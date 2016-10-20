package com.github.kyriosdata.parser;

import java.util.List;

/**
 * Analisador sintático descendente recursivo para expressões matemáticas.
 */
public class Parser {

    private int corrente = 0;
    private int ultimoToken;
    private List<Token> tokens;
    private char caractere;

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

        if (corrente < ultimoToken) {
            return complemento(analisada);
        }

        return analisada;
    }

    /**
     * expr ::= constante | identificador | (expr Op expr)
     * @return
     */
    private Expressao expr() {

        if (isConstante()) {
            return new Constante(constante());
        }

        if (isIdentificador()) {
            return new Variavel(variavel());
        }

        if (isAbre()) {
            return exprEntreParenteses();
        }

        throw new IllegalArgumentException("Nao esperado: "
                + tokens.get(corrente).getElemento());
    }


    private void consome(char esperado) {
        if (caractere != esperado) {
            throw new IllegalArgumentException("Esperado " + caractere);
        }
    }

    private char operador() {
        return proximoToken().getElemento().charAt(0);
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

    private boolean isIdentificador() {
        return tokens.get(corrente).getTipo() == Lexer.ID;
    }

    /**
     * exprEntreParenteses ::= ( expr complemento )
     * @return
     */
    private Expressao exprEntreParenteses() {
        if (!isAbre()) {
            throw new IllegalArgumentException("esperado (");
        }

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

        char operador = operador();

        Expressao exp2 = expr();

        switch (operador) {
            case '+':
                return new Soma(expr1, exp2);
            case '-':
                return new Subtracao(expr1, exp2);
            case '*':
                return new Multiplicacao(expr1, exp2);
            case '/':
                return new Divisao(expr1, exp2);
            case '&':
                return new Multiplicacao(expr1, exp2);
            case '|':
                return new Soma(expr1, exp2);
            default:
                throw new IllegalArgumentException("Operador invalido:" + operador);
        }
    }

    private float constante() {
        return (float)Double.parseDouble(proximoToken().getElemento());
    }

    private String variavel() {
        return proximoToken().getElemento();
    }

}
