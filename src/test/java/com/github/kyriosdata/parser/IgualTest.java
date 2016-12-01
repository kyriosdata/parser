package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IgualTest {

    @Test
    public void expressoesNullGeramExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new Igual(null, new Constante(2f)));

        assertThrows(IllegalArgumentException.class,
                () -> new Igual(new Constante(2f), null));
    }

    @Test
    public void verificaIgualdadeTipica() {
        Expressao e1 = new Constante(1f);
        Expressao e2 = new Constante(2f);
        Expressao e3 = new Constante(2f);

        assertEquals(0f, new Igual(e1, e2).valor(), 0.0001d);
        assertEquals(1f, new Igual(e2, e3).valor(), 0.0001d);
    }

    @Test
    public void verificaIgualdadeDependenteDeContexto() {
        Expressao a = new Variavel("a");
        Expressao b = new Variavel("b");

        Map<String, Float> ctx = new HashMap<>(2);
        ctx.put("a", 1.1f);
        ctx.put("b", 1.1f);
        assertEquals(1f, new Igual(a, b).valor(ctx), 0.0001d);
    }
}

