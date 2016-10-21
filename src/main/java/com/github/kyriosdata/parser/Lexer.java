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

    public static final String SOMA = "+";
    public static final String SUBTRACAO = "-";
    public static final String PRODUTO = "*";
    public static final String DIVISAO = "/";
    public static final String E = "&";
    public static final String OU = "|";
    public static final String IGUAL = "=";


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
                proximoCaractere();
                continue;
            }

            if (isFecha()) {
                tokens.add(new Token(FECHA, ")"));
                proximoCaractere();
                continue;
            }

            if (isOperador()) {
                tokens.add(new Token(OPERADOR, operador()));
                continue;
            }

            // Se é espaço, então é um separador de tokens.
            if (caractere == ' ') {
                proximoCaractere();

                continue;
            }

            // Se não é nenhum dos casos acima, então é desconhecido.
            tokens.add(new Token(DESCONHECIDO, Character.toString(caractere)));
            proximoCaractere();
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
        String ops[] = { SOMA, SUBTRACAO, PRODUTO, DIVISAO, E, OU, IGUAL };
        for(String operador : ops) {
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
