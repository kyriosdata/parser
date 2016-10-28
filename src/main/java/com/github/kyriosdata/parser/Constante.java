/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão definida por uma valorFixo.
 */
class Constante implements Expressao {

    private final float valorFixo;

    /**
     * Cria uma expressão definida por valorFixo.
     *
     * @param valor O valor da valorFixo.
     */
    public Constante(float valor) {
        valorFixo = valor;
    }

    @Override
    public float valor(Map<String, Float> contexto) {
        return valorFixo;
    }

    @Override
    public float valor() {
        return valorFixo;
    }
}
