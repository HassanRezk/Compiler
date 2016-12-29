package parser;

import grammar.GrammarNode;
import lexical_analysis.Token;
import lexical_analysis.TokenType;

import java.util.*;

/**
 * Created by user on 12/27/2016.
 */
public class ParseTree {
    private Map<String, Map<String, List<GrammarNode>>> parsingTable;
    Stack<String> input = new Stack<>();
    Stack<GrammarNode> ruleStack = new Stack<>();
    boolean error = false;
    ParseTreeNode root;

    public ParseTree(Map<String, Map<String, List<GrammarNode>>> parsingTable, List<List<Token>> input) {
        this.parsingTable = parsingTable;
        this.input.push("$");
        this.ruleStack.push(new GrammarNode("$", true));
        this.ruleStack.push(new GrammarNode("program", false));
        //this.input.addAll(Arrays.asList(input.split("\\s+")));
        buildInputStack(input);
        root = new ParseTreeNode(new GrammarNode("E", false));
        buildParseTree(root);
    }

    private void buildInputStack(List<List<Token>> input) {
        for (int i = input.size() - 1; i >= 0; --i ) {
            for (int j = input.get(i).size() - 1; j>= 0; --j){
                if(input.get(i).get(j).getTokenType() == TokenType.IDENTIFIER || input.get(i).get(j).getTokenType() == TokenType.NUMBER){
                    for(int y = input.get(i).get(j).getTokenValue().length() - 1; y >= 0; --y){
                        this.input.push(String.valueOf(input.get(i).get(j).getTokenValue().charAt(y)));
                    }
                }else {
                    this.input.push(input.get(i).get(j).getTokenValue());
                }
            }
        }
    }

    private void buildParseTree(ParseTreeNode currentNode){
        if(input.empty()) {
            System.out.println("input is empty");
            return;
        }
        System.out.println(ruleStack.peek() + " :: " + input.peek());

        if(error) {
            System.out.println("error");
            return;
        }
        if (ruleStack.peek().getValue().equals("EPSILON")) {
            GrammarNode node = ruleStack.pop();
            currentNode.addChild(new ParseTreeNode(node));
            buildParseTree(new ParseTreeNode(node));
        } else if (ruleStack.peek().isTerminal() && ruleStack.peek().getValue().equals(input.peek())) {
            GrammarNode node = ruleStack.pop();
            currentNode.addChild(new ParseTreeNode(node));
            input.pop();
            buildParseTree( new ParseTreeNode(node));
        } else if (ruleStack.peek().isTerminal() && !ruleStack.peek().getValue().equals(input.peek())) {
            error = true;
            System.out.println("error");
        } else if (!ruleStack.peek().isTerminal()) {
            GrammarNode grammarNode = ruleStack.pop();
            if(!parsingTable.get(grammarNode.getValue()).containsKey(input.peek())){
                error = true;
                return;
            }
            List<GrammarNode> rule = parsingTable.get(grammarNode.getValue()).get(input.peek());
            addRuletoStack(rule);
            buildParseTree(currentNode);
        }
    }

    private void addRuletoStack(List<GrammarNode> rule) {
        for(int i = rule.size() - 1 ; i >= 0; --i) {
            ruleStack.push(rule.get(i));
        }
    }

    public ParseTreeNode getTreeRoot () {return root;}

}
