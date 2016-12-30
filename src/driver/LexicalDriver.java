package driver;

import grammar.GrammarNode;
import grammar.GrammarReader;
import lexical_analysis.Token;
import lexical_analysis.TokenError;
import lexical_analysis.Tokenizer;
import parser.ParseTree;
import parser.TableCreator;
import parser.ParseTreeNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hassan on 12/11/2016.
 */
public final class LexicalDriver {

    private static final String filePath = "test.mashm";

    public static void main(final String[] args) throws IOException {
        Tokenizer tokenizer = Tokenizer.getInstance(filePath);
        List<List<Token>> tokens = tokenizer.getTokens();
        List<TokenError> tokenErrors = tokenizer.getTokenErrors();
        /*for (int i = 0; i < tokens.size(); ++i){
            for (int j = 0; j < tokens.get(i).size(); ++ j) {
                System.out.println(tokens.get(i).get(j).getTokenValue());
            }
        }*/
        /*System.out.println("Tokens:\n");
        for(int i = 0 ; i < tokens.size() ; ++i) {
            System.out.print("line# " + (i+1) + ":\n");
            for(Token token : tokens.get(i)) {
                System.out.println(token.toString());
            }
            System.out.println();
            System.out.println();
        }*//*
        System.out.println("Tokens erros:\n");
        for(TokenError tokenError : tokenErrors) {
            System.out.println(tokenError.toString());
        }*/

        GrammarReader g = GrammarReader.getInstance("D:\\projects\\javaProjects\\Compiler\\Grammar v3.txt");

        List<String> nonTerminals = new ArrayList<String>(){{
            add("program");
            add("statements");
            add("return_statement");
            add("ident");
            add("assignment");
            add("while_loop");
            add("if_condition");
            add("condition");
            add("conditionnn");
            add("conditionn");
            add("expr");
            add("iden");
            add("num");
            add("declaration");
            add("type");
            add("sign");
            add("identifier");
            add("after_character");
            add("character");
            add("digit");
            add("number");
            add("boolean");
            add("has_assignment");
            add("integer");
            add("digits");
            add("after_digit");
        }};


        TableCreator tableCreator = new TableCreator(g.getRules(), nonTerminals);
        System.out.println("Parse tree : ");
        ParseTree parseTree = new ParseTree(tableCreator.getParsingTable(), tokens);
        System.out.println("\nprint Parse tree : ");
        parseTree.printParseTree(parseTree.getTreeRoot());
    }

}
