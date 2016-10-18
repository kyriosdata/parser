package com.github.kyriosdata.parser;

/**
 * Analisador sintático descendente recursivo para expressões matemáticas.
 */
public class Parser {

    private int corrente = 0;
    private char caractere = ' ';
    private String expr;
    private int ultimaPosicao;

    public Parser(String expressao) {
        if (expressao == null) {
            throw new IllegalArgumentException("expressão null");
        }

        expr = expressao.trim();
        ultimaPosicao = expr.length() - 1;

        if (ultimaPosicao < 0) {
            throw new IllegalArgumentException("apenas espaço(s)");
        }

        caractere = expr.charAt(corrente);
    }

    /**
     * expr [operador expr]
     *
     * @return A expressão correspondente à sentença a ser
     * analisada.
     */
    public Expressao expressao() {
        Expressao analisada = expr();

        if (corrente < ultimaPosicao) {
            return complementoExpr(analisada);
        }

        return analisada;
    }

    /**
     * Expr ::= Constante | Variavel | (Expr Op Expr)
     * @return
     */
    private Expressao expr() {
        proximo();

        if (isConstante()) {
            return new Constante(constante());
        }

        if (isLetra()) {
            return new Variavel(variavel());
        }

        if (isExprEntreParenteses()) {
            return exprEntreParenteses();
        }

        throw new IllegalArgumentException("Nao esperado: " + caractere);
    }

    private void fechaParenteses() {
        eliminaBrancos();

        if (caractere != ')') {
            throw new IllegalArgumentException("Esperado fecha parenteses: " + caractere);
        }

        if (corrente < ultimaPosicao) {
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

    private Expressao exprEntreParenteses() {
        // consome '('
        consome('(');

        Expressao exp1 = expr();

        Expressao comComplemento = complementoExpr(exp1);

        fechaParenteses();

        return comComplemento;
    }

    /**
     * Operador Expr
     *
     * @param expr1 Primeiro operando.
     *
     * @return Expressão formada pelo primeiro operando
     * concatenada com o Operador Expr.
     */
    private Expressao complementoExpr(Expressao expr1) {
        char operador = getOperador();

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
                throw new IllegalArgumentException("Operador invalido:" + caractere);
        }
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
                if (corrente == ultimaPosicao) {
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

    private String variavel() {
        int inicio = corrente;

        while (isLetra()) {
            if (corrente == ultimaPosicao) {
                break;
            }

            caractere = expr.charAt(++corrente);
        }

        int fim = isLetra() ? corrente + 1 : corrente;
        return expr.substring(inicio, fim);
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
        if (corrente < ultimaPosicao) {
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
            if (corrente < ultimaPosicao) {
                caractere = expr.charAt(++corrente);
            } else {
                throw new IllegalArgumentException("fim inesperado");
            }
        }
    }
}
