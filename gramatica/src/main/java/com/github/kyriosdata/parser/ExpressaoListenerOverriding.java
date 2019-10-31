package com.github.kyriosdata.parser;

import com.github.kyrosdata.parser.ExpressaoBaseListener;
import com.github.kyrosdata.parser.ExpressaoParser;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ExpressaoListenerOverriding extends ExpressaoBaseListener {

    @Override
    public void enterVariavel(ExpressaoParser.VariavelContext ctx) {
        TerminalNode node = ctx.VARIAVEL();
        System.out.println(node);
    }
}
