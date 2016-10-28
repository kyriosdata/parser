/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão definida por uma variável.
 */
class Variavel implements Expressao {
    private final String variavel;

    /**
     * Cria uma expressão formada por uma variável,
     * por exemplo, "a", "salada" e "xpto123".
     *
     * @param variavel O identificador da variável.
     */
    public Variavel(String variavel) {
        this.variavel = variavel;
    }

    @Override
    public float valor() {
        return 0;
    }

    @Override
    public float valor(Map<String, Float> contexto) {
        if (contexto.containsKey(variavel)) {
            return contexto.get(variavel);
        }

        return 0;
    }
}
