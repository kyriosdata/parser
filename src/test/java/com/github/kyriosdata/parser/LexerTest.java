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
    }

    @Test
    public void umUnicoIdentificador() {
        verificaUmUnicoId("a");
        verificaUmUnicoId("atencao");
        verificaUmUnicoId("saude123");
        verificaUmUnicoId("x1");
    }

    private void verificaUmUnicoId(String identificador) {
        List<Token> tokens = new Lexer(identificador).tokenize();
        assertEquals(1, tokens.size());
        assertEquals(Lexer.ID, tokens.get(0).getTipo());
        assertEquals(identificador, tokens.get(0).getElemento());
    }
}

