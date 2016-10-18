package com.github.kyriosdata.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Analisador léxico
 */
public class Lexer {

    public static final int ID = 0;

    public static final int CONSTANTE = 1;

    public static final int ABRE = 2;

    public static final int FECHA = 3;

    public static final int OPERADOR = 4;

    public static final int DESCONHECIDO = 5;

    private int corrente = 0;
    private char caractere = ' ';
    private final String expr;
    private final int posicaoUltimoCaractere;

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

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        caractere = expr.charAt(corrente);

        while (corrente <= posicaoUltimoCaractere) {

            if (isLetra()) {
                tokens.add(new Token(ID, identificador()));
                continue;
            }

            if (isDigito()) {
                tokens.add(new Token(CONSTANTE, constante()));
                continue;
            }

            if (isAbre()) {
                tokens.add(new Token(ABRE, "("));
                continue;
            }

            // Se é espaço, então é um separador de tokens.
            if (caractere == ' ') {
                corrente = corrente + 1;
                if (corrente <= posicaoUltimoCaractere) {
                    caractere = expr.charAt(corrente);
                }

                continue;
            }

            // Se não é nenhum dos casos acima, então é desconhecido.
            tokens.add(new Token(DESCONHECIDO, Character.toString(caractere)));
            corrente = corrente + 1;
            if (corrente <= posicaoUltimoCaractere) {
                caractere = expr.charAt(corrente);
            }
        }

        return tokens;
    }

    private boolean isAbre() {
        return caractere == '(';
    }

    private String abre() {
        corrente = corrente + 1;
        if (corrente <= posicaoUltimoCaractere) {
            caractere = expr.charAt(corrente);
        }

        return "(";
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

        // Assegura que inicia por letra
        if (!isLetra()) {
            return null;
        }

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
