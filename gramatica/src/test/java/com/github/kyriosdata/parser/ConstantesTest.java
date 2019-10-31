package com.github.kyriosdata.parser;

import com.github.kyrosdata.parser.ExpressaoLexer;
import com.github.kyrosdata.parser.ExpressaoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

class ConstantesTest {

    @Test
    void trivial() {
        String exp = "1 + x * (z - 2)";
        ExpressaoLexer lexer = new ExpressaoLexer(CharStreams.fromString(exp));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressaoParser parser = new ExpressaoParser(tokens);
        ParseTree tree = parser.sentenca();

        ParseTreeWalker walker = new ParseTreeWalker();
        ExpressaoProcessor listener =
                new ExpressaoProcessor();

        walker.walk(listener, tree);

        System.out.println("ENTRADA: " + exp);
        System.out.println("POS-FIXADA: " + listener.getPostFix());
        System.out.println("VARIAVEIS: " + listener.getVariaveis());
    }
}
