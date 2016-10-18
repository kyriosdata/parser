package com.github.kyriosdata.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Analisador léxico
 */
public class Lexer {

    public static final int ID = 0;

    private int corrente = 0;
    private char caractere = ' ';
    private final String expr;
    private final int posicaoUltimoCaractere;

    public Lexer(String sentenca) {
        if (sentenca == null) {
            throw new IllegalArgumentException("expressão null");
        }

        // Elimina espaços, tabs.
        String semEspacos = sentenca.replaceAll("\\s", "");

        if (semEspacos.length() < 1) {
            throw new IllegalArgumentException("expressão vazia");
        }

        expr = semEspacos;
        posicaoUltimoCaractere = expr.length() - 1;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        caractere = expr.charAt(corrente);

        if (isLetra()) {
            Token id = new Token(ID, identificador());
            tokens.add(id);
        }

        return tokens;
    }

    private void fechaParenteses() {
        eliminaBrancos();

        if (caractere != ')') {
            throw new IllegalArgumentException("Esperado fecha parenteses: " + caractere);
        }

        if (corrente < posicaoUltimoCaractere) {
            caractere = expr.charAt(++corrente);
        }
    }

    private void consome(char esperado) {
        if (caractere != esperado) {
            throw new IllegalArgumentException("Esperado " + caractere);
        }
    }

    private char getOperador() {
        eliminaBrancos();
        if (isOperador()) {
            char operador = caractere;

            // consome operador
            caractere = expr.charAt(++corrente);

            return operador;
        }

        throw new IllegalArgumentException(" Operador esperado: " + caractere);
    }

    /**
     * Verifica se o token corrente é um operador.
     *
     * @return {@code true} se o token corrente é um
     * operador e {@code false}, caso contrário.
     */
    private boolean isOperador() {
        return caractere == '+' || caractere == '-' ||
                caractere == '*' || caractere == '/' ||
                caractere == '&' || caractere == '|';
    }

    private boolean isExprEntreParenteses() {
        return caractere == '(';
    }

    /**
     * Pelo menos um dígito, possivelmente precedido
     * pelo sinal de menos identifica uma constante.
     *
     * @return {@code true} se na posição corrente da
     * expressão encontra-se uma constante.
     */
    private boolean isConstante() {
        if (Character.isDigit(caractere) || caractere == '-') {
            return true;
        }

        return false;
    }

    private boolean isLetra() {
        return Character.isLetter(caractere);
    }

    private boolean isDigito() {
        return Character.isDigit(caractere);
    }

    private float constante() {
        int sinal = 1;
        if (caractere == '-') {
            proximo();
            sinal = -1;
        }

        int inicio = corrente;
        if ((Character.isDigit(caractere)) || caractere == '.') {
            while (Character.isDigit(caractere) || caractere == '.') {
                if (corrente == posicaoUltimoCaractere) {
                    break;
                }

                caractere = expr.charAt(++corrente);
            }

            int fim = Character.isDigit(caractere) ? corrente + 1 : corrente;
            String doubleStr = expr.substring(inicio, fim);
            return sinal * (float)Double.parseDouble(doubleStr);
        }

        throw new IllegalArgumentException("constante esperada");
    }

    /**
     * Inicia com letra, seguida de letras e ou digitos.
     *
     * @return Identificador obtido a partir da posição corrente.
     */
    private String identificador() {
        int inicio = corrente;

        if (!isLetra()) {
            return null;
        }

        while (isLetra() || isDigito()) {
            if (corrente == posicaoUltimoCaractere) {
                break;
            }

            caractere = expr.charAt(++corrente);
        }

        return expr.substring(inicio, corrente + 1);
    }

    /**
     * Elimina caracteres brancos (que não fazem parte
     * de token). Método seguinte deve recuperar próximo
     * token via método {@link #proximo()}.
     */
    private void eliminaBrancos() {
        while (isBranco(caractere)) {
            if (isProximoCaractereBranco()) {
                caractere = expr.charAt(++corrente);
            } else {
                return;
            }
        }
    }

    private boolean isProximoCaractereBranco() {
        if (corrente < posicaoUltimoCaractere) {
            return isBranco(expr.charAt(corrente + 1));
        }

        return false;
    }

    private boolean isBranco(char caractere) {
        return caractere == ' ' || caractere == '\t';
    }

    private void proximo() {
        if (isBranco(caractere)) {
            eliminaBrancos();
        } else {
            if (corrente < posicaoUltimoCaractere) {
                caractere = expr.charAt(++corrente);
            } else {
                throw new IllegalArgumentException("fim inesperado");
            }
        }
    }
}
