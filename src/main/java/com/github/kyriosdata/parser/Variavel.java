package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Created by fabio_000 on 18/10/2016.
 */
class Variavel implements Expressao {
    private final String variavel;

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
