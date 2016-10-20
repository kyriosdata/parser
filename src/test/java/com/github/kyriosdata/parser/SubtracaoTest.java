package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubtracaoTest {

    @Test
    public void argumentosInvalidos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Subtracao(null, null));

        assertThrows(IllegalArgumentException.class,
                () -> new Subtracao(null, new Constante(2)));

        assertThrows(IllegalArgumentException.class,
                () -> new Subtracao(new Constante(1), null));
    }

    @Test
    public void subtracoesDeConstantesSemContexto() {
        assertEquals(-1f, constantes(1f, 2f).valor(), 0.1d);
        assertEquals(21f, constantes(1f, -20f).valor(), 0.1d);
    }

    @Test
    public void subtracaoDeVariaveisComSemContexto() {
        Map<String, Float> ctx = new HashMap<>();
        ctx.put("a", 1f);
        ctx.put("b", 2f);

        assertEquals(-1f, variaveis("a", "b").valor(ctx), 0.1d);
        assertEquals(0f, variaveis("a", "b").valor(), 0.1d);
    }

    private Subtracao constantes(float p1, float p2) {
        return new Subtracao(new Constante(p1), new Constante(p2));
    }

    private Subtracao variaveis(String p1, String p2) {
        return new Subtracao(new Variavel(p1), new Variavel(p2));
    }
}

