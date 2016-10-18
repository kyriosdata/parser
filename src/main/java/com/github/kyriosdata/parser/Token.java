package com.github.kyriosdata.parser;

/**
 * Cada componente de uma sentença (expressão).
 */
public class Token {

    private int tipo;
    private String elemento;

    public Token(final int tipoToken, final String valorToken) {
        tipo = tipoToken;
        elemento = valorToken;
    }

    public final int getTipo() {
        return tipo;
    }

    public final String getElemento() {
        return elemento;
    }
}
