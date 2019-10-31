package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

class ConstantesTest {

    @Test
    void trivial() {
        ExpressaoProcessor exp = ExpressaoProcessor.run();

        System.out.println("ENTRADA: " + exp);
        System.out.println("POS-FIXADA: " + exp.getPostFix());
        System.out.println("VARIAVEIS: " + exp.getVariaveis());
    }
}
