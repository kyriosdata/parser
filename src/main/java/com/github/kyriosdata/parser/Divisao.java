package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão formada pela razão entre numerador e denominador.
 */
class Divisao implements Expressao {

    private final Expressao numerador;
    private final Expressao denominador;

    /**
     * Cria uma divisão (razão) entre numerador e denominador.
     *
     * @param p1 Numerador.
     * @param p2 Denominador.
     */
    public Divisao(Expressao p1, Expressao p2) {
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("p1 ou p2 é null");
        }

        numerador = p1;
        denominador = p2;
    }

    @Override
    public float valor() {
        return divide(numerador.valor(), denominador.valor());
    }

    private float divide(float dividendo, float divisor) {
        if (Math.signum(divisor) == 0) {
            return 0f;
        }

        return dividendo / divisor;
    }

    @Override
    public float valor(Map<String, Float> contexto) {
        return divide(numerador.valor(contexto), denominador.valor(contexto));
    }
}
