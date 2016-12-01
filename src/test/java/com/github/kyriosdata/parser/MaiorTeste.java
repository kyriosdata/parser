package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaiorTeste {

    @Test
    public void expressoesNullGeramExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new Maior(null, new Constante(2f)));

        assertThrows(IllegalArgumentException.class,
                () -> new Maior(new Constante(2f), null));
    }

    @Test
    public void verificaIgualdadeTipica() {
        Expressao e1 = new Constante(1f);
        Expressao e2 = new Constante(2f);

        assertEquals(0f, new Maior(e1, e2).valor(), 0.0001d);
        assertEquals(1f, new Maior(e2, e1).valor(), 0.0001d);
    }

    @Test
    public void verificaIgualdadeDependenteDeContexto() {
        Expressao a = new Variavel("a");
        Expressao b = new Variavel("b");

        Map<String, Float> ctx = new HashMap<>(2);
        ctx.put("a", 1.1f);
        ctx.put("b", 1.01f);
        assertEquals(1f, new Maior(a, b).valor(ctx), 0.0001d);
    }
}

