package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Created by fabio_000 on 18/10/2016.
 */
class Soma implements Expressao {

    private final Expressao parcelaUm;
    private final Expressao parcelaDois;

    public Soma(Expressao p1, Expressao p2) {
        parcelaUm = p1;
        parcelaDois = p2;
    }

    @Override
    public float valor() {
        return parcelaUm.valor() + parcelaDois.valor();
    }

    @Override
    public float valor(Map<String, Float> contexto) {
        return parcelaUm.valor(contexto) + parcelaDois.valor(contexto);
    }
}
