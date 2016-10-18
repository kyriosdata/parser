package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão formada por numerador e denominador.
 */
class Divisao implements Expressao {

    private final Expressao numerador;
    private final Expressao denominador;

    public Divisao(Expressao p1, Expressao p2) {
        numerador = p1;
        denominador = p2;
    }

    @Override
    public float valor() {
        return numerador.valor() / denominador.valor();
    }

    @Override
    public float valor(Map<String, Float> contexto) {
        return numerador.valor(contexto) / denominador.valor(contexto);
    }
}
