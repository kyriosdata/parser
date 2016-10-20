package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Constante é um valor numérico.
 */
class Constante implements Expressao {
    private final float constante;

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
