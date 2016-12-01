/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Analisador léxico para expressões matemáticas.
 */
public class Lexer {

    /**
     * Tipo identificador.
     */
    public static final int ID = 0;

    /**
     * Tipo constante.
     */
    public static final int CONSTANTE = 1;

    /**
     * Tipo abre parênteses.
     */
    public static final int ABRE = 2;

    /**
     * Tipo fecha parênteses.
     */
    public static final int FECHA = 3;

    /**
     * Tipo operador.
     */
    public static final int OPERADOR = 4;

    /**
     * Tinho desconhecido, nenhum daqueles
     * previstos.
     */
    public static final int DESCONHECIDO = 5;

    /**
     * Texto do token para operador de soma.
     */
    public static final String SOMA = "+";

    /**
     * Texto do token para operador de subtração.
     */
    public static final String SUBTRACAO = "-";

    /**
     * Texto do token para operador de produto.
     */
    public static final String PRODUTO = "*";

    /**
     * Texto do token para operador de divisão.
     */
    public static final String DIVISAO = "/";

    /**
     * Texto do token para operador de E lógico.
     */
    public static final String E = "&";

    /**
     * Texto do token para operador de OU lógico.
     */
    public static final String OU = "|";

    /**
     * Texto do token para operador de igualdade.
     */
    public static final String IGUAL = "=";

    /**
     * Texto do token para operador "maior que".
     */
    public static final String MAIOR = ">";

    /**
     * Texto do token para operador "maior que".
     */
    public static final String MENOR = "<";


    private int corrente = 0;
    private char caractere = ' ';
    private final String expr;
    private final int posicaoUltimoCaractere;

    /**
     * Cria instância de analisador léxico para a
     * sentença.
     *
     * @param sentenca Sentença cuja análise léxica
     *                 produz uma sequência de tokens.
     */
    public Lexer(String sentenca) {
        if (sentenca == null) {
            throw new IllegalArgumentException("expressão null");
        }

        // Elimina espaços e tabs extras e nos extremos.
        // Sentença com espaço usado como separador.
        String espacosExtrasRemovidos = sentenca.trim().replaceAll("\\s\\s*", " ");

        if (espacosExtrasRemovidos.length() < 1) {
            throw new IllegalArgumentException("expressão vazia");
        }

        expr = espacosExtrasRemovidos;
        posicaoUltimoCaractere = expr.length() - 1;
    }

    /**
     * Produz, para a sequência a ser analisada lexicamente,
     * a sequência de tokens correspondente.
     *
     * @return Sequência de tokens correspondente à
     * expressão a ser analisada lexicamente.
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        caractere = expr.charAt(corrente);

        while (corrente <= posicaoUltimoCaractere) {

            if (isLetra()) {
                tokens.add(new Token(ID, identificador()));
            } else if (isDigito()) {
                tokens.add(new Token(CONSTANTE, constante()));
            } else if (isAbre()) {
                tokens.add(new Token(ABRE, "("));
                proximoCaractere();
            } else if (isFecha()) {
                tokens.add(new Token(FECHA, ")"));
                proximoCaractere();
            } else if (isOperador()) {
                tokens.add(new Token(OPERADOR, operador()));
            } else if (caractere == ' ') { // Espaço é um separador de tokens.
                proximoCaractere();
            } else {
                // Se não é nenhum dos casos acima, então é desconhecido.
                tokens.add(new Token(DESCONHECIDO, Character.toString(caractere)));
                proximoCaractere();
            }
        }

        return tokens;
    }

    private void proximoCaractere() {
        corrente = corrente + 1;
        if (corrente <= posicaoUltimoCaractere) {
            caractere = expr.charAt(corrente);
        }
    }

    private boolean isAbre() {
        return caractere == '(';
    }

    private boolean isFecha() {
        return caractere == ')';
    }

    private String operador() {
        String operador = Character.toString(caractere);
        proximoCaractere();

        return operador;
    }

    /**
     * Verifica se o token corrente é um operador.
     *
     * @return {@code true} se o token corrente é um
     * operador e {@code false}, caso contrário.
     */
    private boolean isOperador() {
        String supostoOperador = Character.toString(caractere);
        String ops[] = {SOMA, SUBTRACAO, PRODUTO, DIVISAO, E, OU, IGUAL, MAIOR, MENOR };
        for (String operador : ops) {
            if (operador.equals(supostoOperador)) {
                return true;
            }
        }

        return false;
    }

    private boolean isLetra() {
        return Character.isLetter(caractere);
    }

    private boolean isDigito() {
        return Character.isDigit(caractere);
    }

    /**
     * Pelo menos um dígito, possivelmente seguido de outros,
     * um único ponto separa as cadas decimais.
     *
     * @return Sequência de caracteres correspondente à constante.
     */
    private String constante() {

        boolean semPonto = true;
        int inicio = corrente;

        while (isDigito()) {
            if (corrente == posicaoUltimoCaractere) {
                // posiciona no início do próximo token
                // (que, nesse caso, não existe)
                corrente = corrente + 1;
                break;
            }

            caractere = expr.charAt(++corrente);
            if (caractere == '.' && semPonto) {
                semPonto = false;

                // Vamos para o próximo símbolo da entrada
                caractere = expr.charAt(++corrente);
                continue;
            }
        }

        return expr.substring(inicio, corrente);
    }

    /**
     * Pelo menos uma letra, seguida de letras e ou digitos.
     *
     * @return Identificador obtido a partir da posição corrente.
     */
    private String identificador() {
        int inicio = corrente;

        while (isLetra() || isDigito()) {
            if (corrente == posicaoUltimoCaractere) {
                // indica fim do token corrente
                corrente = corrente + 1;
                break;
            }

            caractere = expr.charAt(++corrente);
        }

        return expr.substring(inicio, corrente);
    }
}
