package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão formada por dois operandos, multiplicando e
 * operador, cujo resultado é o produto deles.
 */
class Multiplicacao implements Expressao {

    private final Expressao multiplicando;
    private final Expressao multiplicador;

    public Multiplicacao(Expressao p1, Expressao p2) {
        multiplicando = p1;
        multiplicador = p2;
    }

    @Override
    public float valor() {
        return multiplicando.valor() * multiplicador.valor();
    }

    @Override
    public float valor(Map<String, Float> contexto) {
        return multiplicando.valor(contexto) * multiplicador.valor(contexto);
    }
}
