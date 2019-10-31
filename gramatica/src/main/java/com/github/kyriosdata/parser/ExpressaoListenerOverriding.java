package com.github.kyriosdata.parser;

import com.github.kyrosdata.parser.ExpressaoBaseListener;
import com.github.kyrosdata.parser.ExpressaoLexer;
import com.github.kyrosdata.parser.ExpressaoParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class ExpressaoListenerOverriding extends ExpressaoBaseListener {

    private List<String> postFix = new ArrayList<>();
    private Stack<Integer> contadores = new Stack<>();
    private Stack<String> operadores = new Stack<>();
    private Set<String> variaveis = new HashSet<>();

    public List<String> getPostFix() {
        return postFix;
    }

    public Set<String> getVariaveis() {
        return variaveis;
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
        switch (node.getSymbol().getType()) {
            case ExpressaoLexer.ADICAO:
                operadores.push("+");
                break;
            case ExpressaoLexer.SUBTRACAO:
                operadores.push("-");
                break;
            case ExpressaoLexer.MULTIPLICACAO:
                operadores.push("*");
                break;
            case ExpressaoLexer.DIVISAO:
                operadores.push("/");
                break;
        }
    }

    @Override
    public void enterExpr(ExpressaoParser.ExprContext ctx) {
        super.enterExpr(ctx);
        contadores.push(0);
    }

    @Override
    public void exitExpr(ExpressaoParser.ExprContext ctx) {
        super.exitExpr(ctx);
        int contador = contadores.pop() + 1;
        if (contador == 2) {
            postFix.add(operadores.pop());
        } else {
            contadores.push(contador);
        }
    }

    @Override
    public void enterVariavel(ExpressaoParser.VariavelContext ctx) {
        super.enterVariavel(ctx);
        TerminalNode node = ctx.VARIAVEL();
        postFix.add(node.toString());
        variaveis.add(node.toString());
    }

    @Override
    public void enterConstante(ExpressaoParser.ConstanteContext ctx) {
        super.enterConstante(ctx);
        TerminalNode node = ctx.NUMERO();
        postFix.add(node.toString());
    }
}
