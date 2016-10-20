package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void expressoesUmaConstante() {
        assertEquals(1.2d, exprPara("1.2").valor(), 0.001d);
        assertEquals(0d, exprPara("0").valor(), 0.001d);
        assertEquals(10987.654d, exprPara("10987.654").valor(), 0.001d);
    }

    @Test
    public void expressoesUmaVariavel() {
        Map<String,Float> valores = new HashMap<>();
        valores.put("a", 9.876f);
        valores.put("b123", 1234f);

        assertEquals(9.876d, exprPara("a").valor(valores), 0.001d);
        assertEquals(1234d, exprPara("b123").valor(valores), 0.001d);
    }

    @Test
    public void valorZeroParaVariavelNaoDefinida() {
        assertEquals(0d, exprPara("a").valor(new HashMap<>()), 0.001d);
    }

    @Test
    public void expressaoComDoisComponentes() {
        assertEquals(2d, exprPara("1 + 1").valor(new HashMap<>()), 0.001d);
        assertEquals(0.2d, exprPara("0.11 + 0.09").valor(new HashMap<>()), 0.001d);
        assertEquals(11d, exprPara("1.1 * 10").valor(new HashMap<>()), 0.001d);
        assertEquals(314d, exprPara("3.14 * 100").valor(new HashMap<>()), 0.001d);
        assertEquals(1d, exprPara("1.1 - 0.1").valor(new HashMap<>()), 0.001d);
        assertEquals(-0.88d, exprPara("1.12 - 2").valor(new HashMap<>()), 0.001d);
        assertEquals(1.1d, exprPara("1.1 / 1").valor(new HashMap<>()), 0.001d);
        assertEquals(38.46153d, exprPara("345 / 8.97").valor(new HashMap<>()), 0.001d);
    }

    private Expressao exprPara(String expressao) {
        List<Token> tokens = new Lexer(expressao).tokenize();
        Parser parser = new Parser(tokens);
        return parser.expressao();
    }
}

