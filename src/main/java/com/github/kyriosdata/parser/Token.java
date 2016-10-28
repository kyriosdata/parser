/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

/**
 * Cada componente de uma sentença (expressão).
 */
public class Token {

    private int tipo;
    private String elemento;

    /**
     * Cria token de determinado tipo.
     *
     * @param tipoToken O tipo do token.
     *
     * @param valorToken Valor do token (texto
     *                   correspondente).
     */
    public Token(final int tipoToken, final String valorToken) {
        tipo = tipoToken;
        elemento = valorToken;
    }

    /**
     * Recupera o tipo do token.
     * @return O identificador do tipo.
     */
    public final int getTipo() {
        return tipo;
    }

    /**
     * O valor (texto) do token.
     *
     * @return Texto do token.
     */
    public final String getElemento() {
        return elemento;
    }
}
