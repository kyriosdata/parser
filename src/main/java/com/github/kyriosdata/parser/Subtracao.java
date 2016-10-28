/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão definida por subtração entre dois números.
 */
class Subtracao implements Expressao {

    private final Expressao parcelaUm;
    private final Expressao parcelaDois;

    public Subtracao(Expressao p1, Expressao p2) {
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("p1 ou p2 é null");
        }

        parcelaUm = p1;
        parcelaDois = p2;
    }

    @Override
    public float valor() {
        return parcelaUm.valor() - parcelaDois.valor();
    }

    @Override
    public float valor(Map<String, Float> contexto) {
        return parcelaUm.valor(contexto) - parcelaDois.valor(contexto);
    }
}
