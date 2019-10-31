package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

class ConstantesTest {

    @Test
    void trivial() {
        ExpressaoProcessor exp = ExpressaoProcessor.run("1 + x * (z - 2)");

        System.out.println("ENTRADA: " + exp.getEntrada());
        System.out.println("POS-FIXADA: " + exp.getPostFix());
        System.out.println("VARIAVEIS: " + exp.getVariaveis());
    }
}
