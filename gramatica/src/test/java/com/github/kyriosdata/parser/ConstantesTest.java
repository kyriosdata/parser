package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantesTest {

    @Test
    void constanteTrivialNegativa() {
        Expressao exp = Expressao.from("-9");
        assertEquals(0, exp.getVariaveis().size());
        assertEquals("-9", exp.getPosfixada().get(0));
        assertEquals("-9", exp.getEntrada());
    }

    @Test
    void trivial() {
        Expressao exp = Expressao.from("1");
        assertEquals(0, exp.getVariaveis().size());
        assertEquals("1", exp.getPosfixada().get(0));
        assertEquals("1", exp.getEntrada());
    }
}
