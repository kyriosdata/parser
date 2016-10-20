package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DivisaoTest {

    @Test
    public void argumentosInvalidos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Divisao(null, null));

        assertThrows(IllegalArgumentException.class,
                () -> new Divisao(null, new Constante(2)));

        assertThrows(IllegalArgumentException.class,
                () -> new Divisao(new Constante(1), null));
    }

    @Test
    public void multiplicacoesDeConstantesSemContexto() {
        assertEquals(0.5f, constantes(1f, 2f).valor(), 0.1d);
        assertEquals(-0.05f, constantes(1f, -20f).valor(), 0.1d);
    }

    @Test
    public void divisaoDeVariaveisComSemContexto() {
        Map<String, Float> ctx = new HashMap<>();
        ctx.put("a", 1f);
        ctx.put("b", 2f);

        assertEquals(0.5f, variaveis("a", "b").valor(ctx), 0.1d);
    }

    @Test
    public void denominadorZeroDivisaoZero() {
        assertEquals(0f, constantes(10, 0).valor(), 0.1d);
    }

    private Divisao constantes(float p1, float p2) {
        return new Divisao(new Constante(p1), new Constante(p2));
    }

    private Divisao variaveis(String p1, String p2) {
        return new Divisao(new Variavel(p1), new Variavel(p2));
    }
}

