package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Analisador Sintático Descendente Recursivo.
 * <p>
 * Constante ::= '-' + Numero | Numero
 * Variavel ::= Letra (uma ou mais)
 * Expr ::= Constante | Variavel | ( Expr Operador Expr )
 * Main ::= Expr [Operador Expr]
 *
 * <p>Observe que não contempla "Expr op Expr".
 */
public class RecursiveDescetParserTest {

    @Test
    public void expressaoNullInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Parser(null).expressao());
    }

    @Test
    public void expressaoVaziaInvalida() {

        assertThrows(IllegalArgumentException.class, () -> new Parser("").expressao());
    }

    @Test
    public void expressaoComElementoInvalido() {

        assertThrows(IllegalArgumentException.class, () -> new Parser("@").expressao());
    }

    @Test
    public void expressoesConstantes() {
        assertEquals(2.34, new Parser(" 2.34    ").expressao().valor(), 0.00001d);
        assertEquals(-4, new Parser("   -4").expressao().valor(), 0.00001d);
        assertEquals(-4, new Parser("   -4  ").expressao().valor(), 0.00001d);
        assertEquals(1, new Parser("1").expressao().valor(), 0.00001d);
        assertEquals(0, new Parser("0").expressao().valor(), 0.00001d);
    }

    @Test
    public void expressoesVariaveis() {
        Map<String,Float> ctx = new HashMap<>(2);
        ctx.put("x", 9.876f);
        ctx.put("CaSa", 8.765f);

        assertEquals(0, new Parser("x").expressao().valor(), 0.00001d);
        assertEquals(0, new Parser("CaSa").expressao().valor(), 0.00001d);

        assertEquals(9.876d, new Parser("   x").expressao().valor(ctx), 0.00001d);
        assertEquals(9.876d, new Parser("   x ").expressao().valor(ctx), 0.00001d);
        assertEquals(8.765d, new Parser("   CaSa").expressao().valor(ctx), 0.00001d);
        assertEquals(8.765d, new Parser("   CaSa    ").expressao().valor(ctx), 0.00001d);
        assertEquals(18.641d, new Parser("(x + CaSa)").expressao().valor(ctx), 0.00001d);
        assertEquals(18.641d, new Parser(" (x + CaSa) ").expressao().valor(ctx), 0.00001d);
        assertEquals(18.641d, new Parser(" ( x+CaSa )").expressao().valor(ctx), 0.00001d);
        assertEquals(18.641d, new Parser(" ( x+   CaSa )").expressao().valor(ctx), 0.00001d);
        assertEquals(18.641d, new Parser(" ( x+CaSa )  ").expressao().valor(ctx), 0.00001d);
        assertEquals(18.641d, new Parser(" ( x    +CaSa )").expressao().valor(ctx), 0.00001d);
        assertEquals(86.56314d, new Parser("(x*CaSa)").expressao().valor(ctx), 0.00001d);
        assertEquals(1.12675f, new Parser("(x/CaSa)").expressao().valor(ctx), 0.00001d);
        assertEquals(1.111f, new Parser("(x-CaSa)").expressao().valor(ctx), 0.00001d);
    }

    @Test
    public void expressoesLogicas() {
        assertEquals(0d, new Parser("(0&1)").expressao().valor(), 0.00001d);
        assertEquals(0d, new Parser("(0&0)").expressao().valor(), 0.00001d);
        assertEquals(0d, new Parser("( 0 & 1 )").expressao().valor(), 0.00001d);
        assertEquals(0d, new Parser("( 0 & 1 )").expressao().valor(), 0.00001d);
        assertEquals(1d, new Parser("( 1 & 1 )").expressao().valor(), 0.00001d);
        assertEquals(1d, new Parser("( 0 | 1 )").expressao().valor(), 0.00001d);
        assertEquals(2d, new Parser("( 1 | 1 )").expressao().valor(), 0.00001d);
    }

    @Test
    public void expressoesLogicasComplexas() {
        assertEquals(0d, new Parser("( 0 | (1 & 0) )").expressao().valor(), 0.00001d);
        assertEquals(2d, new Parser("(1|(1 & 1))").expressao().valor(), 0.00001d);
    }

    @Test
    public void sentencasInvalidas() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parser("(").expressao());
        assertThrows(IllegalArgumentException.class,
                () -> new Parser("-").expressao());
    }

    @Test
    public void expressaoTodaComBrancos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parser("   ").expressao());
    }

    @Test
    public void expressaoSemParenteses() {
        assertEquals(2d, new Parser("1 + 1").expressao().valor(), 0.0001d);
        assertEquals(2d, new Parser("1 + 1 + 4").expressao().valor(), 0.0001d);
    }
}

