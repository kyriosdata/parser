package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultiplicacaoTest {

    @Test
    public void argumentosInvalidos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Multiplicacao(null, null));

        assertThrows(IllegalArgumentException.class,
                () -> new Multiplicacao(null, new Constante(2)));

        assertThrows(IllegalArgumentException.class,
                () -> new Multiplicacao(new Constante(1), null));
    }

    @Test
    public void multiplicacoesDeConstantesSemContexto() {
        assertEquals(2f, constantes(1f, 2f).valor(), 0.1d);
        assertEquals(21f, constantes(1f, -20f).valor(), 0.1d);
    }

    @Test
    public void multiplicacaoDeVariaveisComSemContexto() {
        Map<String, Float> ctx = new HashMap<>();
        ctx.put("a", 1f);
        ctx.put("b", 2f);

        assertEquals(2f, variaveis("a", "b").valor(ctx), 0.1d);
        assertEquals(0f, variaveis("a", "b").valor(), 0.1d);
    }

    private Multiplicacao constantes(float p1, float p2) {
        return new Multiplicacao(new Constante(p1), new Constante(p2));
    }

    private Multiplicacao variaveis(String p1, String p2) {
        return new Multiplicacao(new Variavel(p1), new Variavel(p2));
    }
}

