/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão formada por dois operandos, multiplicando e
 * operador, cujo resultado é o produto deles.
 */
class Produto implements Expressao {

    private final Expressao multiplicando;
    private final Expressao multiplicador;

    public Produto(Expressao p1, Expressao p2) {
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("p1 ou p2 é null");
        }

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
