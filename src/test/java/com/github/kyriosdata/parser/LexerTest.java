package com.github.kyriosdata.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerTest {

    @Test
    public void expressaoNullOuVaziaGeraExcecao() {
        assertThrows(IllegalArgumentException.class, () -> new Lexer(null));
        assertThrows(IllegalArgumentException.class, () -> new Lexer(""));
        assertThrows(IllegalArgumentException.class, () -> new Lexer(" "));
        assertThrows(IllegalArgumentException.class, () -> new Lexer("\t"));
    }

    @Test
    public void replaceAllFazEsperado() {
        assertEquals(-1, " a ".replaceAll("\\s", "").indexOf(" "));
        assertEquals(-1, " a + b - x / 8 ".replaceAll("\\s", "").indexOf(" "));
        assertEquals(-1, "       8 ".replaceAll("\\s", "").indexOf(" "));
        assertEquals("a 1", "a 1".replaceAll("\\s\\s", " "));
        assertEquals("a 1", "a  1".replaceAll("\\s\\s", " "));
        assertEquals("a 1", "a  \t 1".replaceAll("\\s\\s*", " "));
        assertEquals(" a 1 ", "\t\t \ta  \t 1\t".replaceAll("\\s\\s*", " "));
        assertEquals("a", " a ".trim());
    }

    @Test
    public void quatroTokens() {
        List<Token> tokens = new Lexer("\ta\t 1\t\t  \tb  3").tokenize();
        assertEquals(4, tokens.size());

        assertEquals("a", tokens.get(0).getElemento());
        assertEquals(Lexer.ID, tokens.get(0).getTipo());

        assertEquals("1", tokens.get(1).getElemento());
        assertEquals(Lexer.CONSTANTE, tokens.get(1).getTipo());

        assertEquals("b", tokens.get(2).getElemento());
        assertEquals(Lexer.ID, tokens.get(2).getTipo());

        assertEquals("3", tokens.get(3).getElemento());
        assertEquals(Lexer.CONSTANTE, tokens.get(3).getTipo());
    }

    @Test
    public void umUnicoIdentificador() {
        verificaUmUnicoId("a");
        verificaUmUnicoId("atencao");
        verificaUmUnicoId("saude123");
        verificaUmUnicoId("x1");
    }

    @Test
    public void umaUnicaConstante() {
        verificaUmaUnicaConstante("0");
        verificaUmaUnicaConstante("10");
        verificaUmaUnicaConstante("1.23");
    }

    private void verificaUmUnicoId(String identificador) {
        List<Token> tokens = new Lexer(identificador).tokenize();
        assertEquals(1, tokens.size());
        assertEquals(Lexer.ID, tokens.get(0).getTipo());
        assertEquals(identificador, tokens.get(0).getElemento());
    }

    private void verificaUmaUnicaConstante(String constante) {
        List<Token> tokens = new Lexer(constante).tokenize();
        assertEquals(1, tokens.size());
        assertEquals(Lexer.CONSTANTE, tokens.get(0).getTipo());
        assertEquals(constante, tokens.get(0).getElemento());
    }
}

