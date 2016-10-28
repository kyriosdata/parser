/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão definida por uma constante.
 */
class Constante implements Expressao {
    private final float constante;

    /**
     * Cria uma expressão definida por constante.
     *
     * @param valor O valor da constante.
     */
    public Constante(float valor) {
        constante = valor;
    }

    @Override
    public float valor(Map<String, Float> contexto) {
        return constante;
    }

    @Override
    public float valor() {
        return constante;
    }
}
