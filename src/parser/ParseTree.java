package parser;

import grammar.GrammarNode;
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
    ParseTreeNode root;

    public ParseTree(Map<String, Map<String, List<GrammarNode>>> parsingTable, String[] input) {
        this.parsingTable = parsingTable;
        this.nonTerminals = nonTerminals;
        this.input.push("$");
        this.ruleStack.push(new GrammarNode("$", true));
        this.ruleStack.push(new GrammarNode("E", false));
        this.input.addAll(Arrays.asList(input[0].trim().split("\\s+")));

        root = new ParseTreeNode(new GrammarNode("E", false));
        buildParseTree(root);
    }

    private void buildParseTree(ParseTreeNode currentNode){
        if(input.empty()) return;
        if(error) return;

        if(ruleStack.peek().getValue().equals("empty")){
            GrammarNode node = ruleStack.pop();
            currentNode.addChild(new ParseTreeNode(node));
            return;
        }else if(ruleStack.peek().isTerminal() && ruleStack.peek().getValue().equals(input.peek())) {
            GrammarNode node = ruleStack.pop();
            currentNode.addChild(new ParseTreeNode(node));
            input.pop();
            return;
        } else if(ruleStack.peek().isTerminal() && !ruleStack.peek().getValue().equals(input.peek())) {
            error = true;
        }else if(!ruleStack.peek().isTerminal()){

            GrammarNode grammarNode = ruleStack.pop();
            List<GrammarNode> rule = parsingTable.get(grammarNode.getValue()).get(input.peek());
            addRuletoStack(rule);
            currentNode.addChild(new ParseTreeNode(ruleStack.peek()));
            if(!grammarNode.getValue().equals("E")) {
                buildParseTree(new ParseTreeNode(ruleStack.peek()));
            }else{
                buildParseTree(currentNode);
            }
        }
    }

    private void addRuletoStack(List<GrammarNode> rule) {
        for(int i = rule.size() - 1 ; i >= 0; --i) {
            ruleStack.push(rule.get(i));
        }
    }


}
