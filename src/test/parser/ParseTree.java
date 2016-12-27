package test.parser;

import grammar.GrammarNode;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by user on 12/27/2016.
 */
public class ParseTree {
    private Map<String, Map<String, List<GrammarNode>>> parsingTable;
    List<String> terminals, nonTerminals;
    Stack<String> input = new Stack<>();
    Stack<GrammarNode> ruleStack = new Stack<>();
    boolean error = false;

    public ParseTree(Map<String, Map<String, List<GrammarNode>>> parsingTable, String[] input) {
        this.parsingTable = parsingTable;
        this.nonTerminals = nonTerminals;
        this.input.push("$");
        this.ruleStack.push(new GrammarNode("$", true));
        this.ruleStack.push(new GrammarNode("E", false));
        this.input.addAll(Arrays.asList(input[0].trim().split("\\s+")));
         buildParseTree();
        System.out.print(error);
    }

    private void buildParseTree(){
        if(input.empty()) return;
        if(error) return;
        List<GrammarNode> rule = parsingTable.get(ruleStack.pop().getValue()).get(input.peek());
        addRuletoStack(rule);
        if(ruleStack.peek().getValue().equals("empty")){
            ruleStack.pop();
        }
        if(ruleStack.peek().isTerminal() && ruleStack.peek().getValue().equals(input.peek())) {
            ruleStack.pop();
            input.pop();
            buildParseTree();
        } else if(ruleStack.peek().isTerminal() && !ruleStack.peek().getValue().equals(input.peek())) {
            error = true;
        }else if(!ruleStack.peek().isTerminal()) {
            error = false;
            buildParseTree();
        }
    }

    private void addRuletoStack(List<GrammarNode> rule) {
        for(int i = rule.size() - 1 ; i >= 0; --i) {
            ruleStack.push(rule.get(i));
        }
    }
}
