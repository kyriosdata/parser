package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void expressoesInvalidas() {
        Class<IllegalArgumentException> iae = IllegalArgumentException.class;

        assertThrows(iae, () -> exprPara("("));
        assertThrows(iae, () -> exprPara(")"));
        assertThrows(iae, () -> exprPara("+"));
        assertThrows(iae, () -> exprPara("="));
        assertThrows(iae, () -> exprPara("x -"));
        assertThrows(iae, () -> exprPara("x - 1 + 1"));
        assertThrows(iae, () -> exprPara("(x - 1 *"));
        assertThrows(iae, () -> exprPara("x ( a"));
    }

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
    public void expressaoComDoisOperandosConstantes() {
        assertEquals(2d, exprPara("1 + 1").valor(new HashMap<>()), 0.001d);
        assertEquals(0.2d, exprPara("0.11 + 0.09").valor(new HashMap<>()), 0.001d);
        assertEquals(11d, exprPara("1.1 * 10").valor(new HashMap<>()), 0.001d);
        assertEquals(314d, exprPara("3.14 * 100").valor(new HashMap<>()), 0.001d);
        assertEquals(1d, exprPara("1.1 - 0.1").valor(new HashMap<>()), 0.001d);
        assertEquals(-0.88d, exprPara("1.12 - 2").valor(new HashMap<>()), 0.001d);
        assertEquals(1.1d, exprPara("1.1 / 1").valor(new HashMap<>()), 0.001d);
        assertEquals(38.46153d, exprPara("345 / 8.97").valor(new HashMap<>()), 0.001d);
    }

    @Test
    public void expressaoComDoisOperandosVariaveis() {
        Map<String,Float> ctx = new HashMap<>();

        ctx.put("a", 1f);
        ctx.put("b", 1f);
        assertEquals(2d, exprPara("a + b").valor(ctx), 0.001d);

        ctx.put("c", 0.11f);
        ctx.put("d", 0.09f);
        assertEquals(0.2d, exprPara("c + d").valor(ctx), 0.001d);

        ctx.put("e", 1.1f);
        ctx.put("f", 10f);
        assertEquals(11d, exprPara("e * f").valor(ctx), 0.001d);

        ctx.put("g", 3.14f);
        ctx.put("h", 100f);
        assertEquals(314d, exprPara("g * h").valor(ctx), 0.001d);

        ctx.put("i", 1.1f);
        ctx.put("j", 0.1f);
        assertEquals(1d, exprPara("i - j").valor(ctx), 0.001d);

        ctx.put("k", 1.12f);
        ctx.put("l", 2f);
        assertEquals(-0.88d, exprPara("k - l").valor(ctx), 0.001d);

        ctx.put("m", 1.1f);
        ctx.put("n", 1f);
        assertEquals(1.1d, exprPara("m / n").valor(ctx), 0.001d);

        ctx.put("o", 345f);
        ctx.put("p", 8.97f);
        assertEquals(38.46153d, exprPara("o / p").valor(ctx), 0.001d);

        ctx.put("f", 0f);
        ctx.put("v", 1f);

        assertEquals(1d, exprPara("f | v").valor(ctx), 0.001d);
        assertEquals(0d, exprPara("f & v").valor(ctx), 0.001d);
        assertEquals(1d, exprPara("v & v").valor(ctx), 0.001d);
        assertEquals(0d, exprPara("f | f").valor(ctx), 0.001d);
        assertEquals(2d, exprPara("v | v").valor(ctx), 0.001d);
        assertEquals(1d, exprPara("v = v").valor(ctx), 0.001d);
        assertEquals(0d, exprPara("v = f").valor(ctx), 0.001d);
        assertEquals(1d, exprPara("f = f").valor(ctx), 0.001d);
    }

    @Test
    public void expressaoComParenteses() {
        assertEquals(-1d, exprPara("(2 - 3)").valor(), 0.001d);
        assertEquals(2d, exprPara("(10 / 5)").valor(), 0.001d);
        assertEquals(0d, exprPara("(2 & 0)").valor(), 0.001d);

        Map<String, Float> ctx = new HashMap<>();
        ctx.put("a", 10f);

        assertEquals(102d, exprPara("(a * 10.2)").valor(ctx), 0.001d);
    }

    @Test
    public void expressaoComParentesesComplexa() {
        assertEquals(2d, exprPara("10 / (50 / 10)").valor(), 0.001d);
        assertEquals(2d, exprPara("(10 / (50 / 10))").valor(), 0.001d);
        assertEquals(-5d, exprPara("5 * (2 - 3)").valor(), 0.001d);
        assertEquals(0d, exprPara("(2 & 0)").valor(), 0.001d);

        Map<String, Float> ctx = new HashMap<>();
        ctx.put("a", 10f);

        assertEquals(10d, exprPara("(a - 9) * (10.2 - 0.2)").valor(ctx), 0.001d);
    }

    private Expressao exprPara(String expressao) {
        List<Token> tokens = new Lexer(expressao).tokenize();
        Parser parser = new Parser(tokens);
        return parser.expressao();
    }
}

