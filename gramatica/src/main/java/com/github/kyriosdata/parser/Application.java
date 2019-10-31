package com.github.kyriosdata.parser;

import com.github.kyrosdata.parser.ExpressaoLexer;
import com.github.kyrosdata.parser.ExpressaoParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Application {
    public static void main(String[] args) {
        String exp = "1 + x";
        ExpressaoLexer lexer = new ExpressaoLexer(CharStreams.fromString(exp));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressaoParser parser = new ExpressaoParser(tokens);
        ParseTree tree = parser.sentenca();

        ParseTreeWalker walker = new ParseTreeWalker();
        ExpressaoListenerOverriding listener =
                new ExpressaoListenerOverriding();

        walker.walk(listener, tree);
    }
}
