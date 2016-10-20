package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SomaTest {

    @Test
    public void argumentosInvalidos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Soma(null, null));

        assertThrows(IllegalArgumentException.class,
                () -> new Soma(null, new Constante(2)));

        assertThrows(IllegalArgumentException.class,
                () -> new Soma(new Constante(1), null));
    }

    @Test
    public void somasDeConstantesSemContexto() {
        assertEquals(3f, constantes(1f, 2f).valor(), 0.1d);
        assertEquals(-19f, constantes(1f, -20f).valor(), 0.1d);
    }

    @Test
    public void somaVariaveisComSemContexto() {
        Map<String, Float> ctx = new HashMap<>();
        ctx.put("a", 1f);
        ctx.put("b", 2f);

        assertEquals(3f, variaveis("a", "b").valor(ctx), 0.1d);
        assertEquals(0f, variaveis("a", "b").valor(), 0.1d);
    }

    private Soma constantes(float p1, float p2) {
        return new Soma(new Constante(p1), new Constante(p2));
    }

    private Soma variaveis(String p1, String p2) {
        return new Soma(new Variavel(p1), new Variavel(p2));
    }
}

