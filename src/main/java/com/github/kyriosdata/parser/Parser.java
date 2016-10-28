/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

import java.util.List;

/**
 * Analisador sintático descendente recursivo para expressões
 * matemáticas.
 *
 */
public class Parser {

    private int corrente = 0;
    private int ultimoToken;
    private List<Token> tokens;

    /**
     * Cria analisador sintático para a
     * sequência de tokens fornecida como
     * entrada.
     *
     * @param simbolos Sequência de tokens sobre a
     *                 qual a análise será realizada.
     */
    public Parser(List<Token> simbolos) {
        tokens = simbolos;
        ultimoToken = tokens.size() - 1;
    }

    /**
     * expr [operador expr]
     *
     * @return A expressão correspondente à sentença a ser
     * analisada.
     */
    public Expressao expressao() {
        Expressao analisada = expr();

        if (corrente == ultimoToken) {
            String msg = "simbolo inesperado " + simbolo();
            throw new IllegalArgumentException(msg);
        }

        if (corrente < ultimoToken) {
            Expressao comComplemento = complemento(analisada);
            if (corrente <= ultimoToken) {
                String msg = "token nao esperado " + simbolo();
                throw new IllegalArgumentException(msg);
            }

            return comComplemento;
        }

        return analisada;
    }

    /**
     * expr ::= constante | identificador | (expr Op expr)
     * @return
     */
    private Expressao expr() {
        if (corrente > ultimoToken) {
            throw new IllegalArgumentException("expressao esperada");
        }

        if (isConstante()) {
            return new Constante(constante());
        }

        if (isIdentificador()) {
            return new Variavel(simbolo());
        }

        if (isAbre()) {
            return exprEntreParenteses();
        }

        throw new IllegalArgumentException("Nao esperado: "
                + tokens.get(corrente).getElemento());
    }

    private Token proximoToken() {
        if (corrente > ultimoToken) {
            String msg = "fim inesperado no token " + (corrente - 1);
            throw new IllegalArgumentException(msg);
        }

        return tokens.get(corrente++);
    }

    /**
     * Verifica se o token corrente é um operador.
     *
     * @return {@code true} se o token corrente é um
     * operador e {@code false}, caso contrário.
     */
    private boolean isOperador() {
        return tokens.get(corrente).getTipo() == Lexer.OPERADOR;
    }

    /**
     * Verifica se token corrente é abre parênteses.
     *
     * @return {@code true} se o token corrente é um abre
     * parênteses, e {@code false}, caso contrário.
     */
    private boolean isAbre() {
        return tokens.get(corrente).getTipo() == Lexer.ABRE;
    }

    /**
     * Verifica se o token corrente é uma constante.
     *
     * @return {@code true} se o token corrente é uma constante.
     */
    private boolean isConstante() {
        return tokens.get(corrente).getTipo() == Lexer.CONSTANTE;
    }

    /**
     * Verifica se o token corrente é um identificador.
     *
     * @return {@code true} se o token corrente é um identificador.
     */
    private boolean isIdentificador() {
        return tokens.get(corrente).getTipo() == Lexer.ID;
    }

    /**
     * exprEntreParenteses ::= ( expr complemento )
     *
     * @return Expressão entre parênteses.
     */
    private Expressao exprEntreParenteses() {
        // So se chega nesse ponto se verificado
        // que token é abre parênteses.

        // Consome '('
        proximoToken();

        Expressao exp1 = expr();

        Expressao complementoExpr = complemento(exp1);

        Token token = proximoToken();
        if (token.getTipo() != Lexer.FECHA) {
            throw new IllegalArgumentException("esperado )");
        }

        return complementoExpr;
    }

    /**
     * complemento ::= operador expr
     *
     * @param expr1 Primeiro operando.
     *
     * @return Expressão formada pelo primeiro operando
     * concatenada com o Operador Expr.
     */
    private Expressao complemento(Expressao expr1) {
        if (!isOperador()) {
            String tk = tokens.get(corrente).getElemento();
            String msg = "esperado operador recebido " + tk;
            throw new IllegalArgumentException(msg);
        }

        String operador = simbolo();

        Expressao expr2 = expr();

        return montaExpressao(expr1, operador, expr2);
    }

    private Expressao montaExpressao(Expressao e1, String operador, Expressao e2) {
        if (Lexer.SOMA.equals(operador) || Lexer.OU.equals(operador)) {
            return new Soma(e1, e2);
        } else if (Lexer.SUBTRACAO.equals(operador)) {
            return new Subtracao(e1, e2);
        } else if (Lexer.PRODUTO.equals(operador) || Lexer.E.equals(operador)) {
            return new Produto(e1, e2);
        } else if (Lexer.DIVISAO.equals(operador)) {
            return new Divisao(e1, e2);
        }

        // Se não é um dos casos verificados acima,
        // então necessariamente é igual.
        // Método chamado apenas com operador válido.
        return new Igual(e1, e2);
    }

    /**
     * Obtém o valor da constante (token corrente).
     *
     * @return Valor {@code float} correspondente à constante
     * (token corrente).
     */
    private float constante() {
        return (float)Double.parseDouble(simbolo());
    }

    /**
     * Recupera símbolo do token corrente.
     *
     * @return Símbolo do token corrente.
     */
    private String simbolo() {
        return proximoToken().getElemento();
    }
}
