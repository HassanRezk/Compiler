package driver;

import grammar.GrammarReader;
import lexical_analysis.Token;
import lexical_analysis.TokenError;
import lexical_analysis.Tokenizer;
import parser.ParseTree;
import parser.TableCreator;
import parser.ParseTreeNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hassan on 12/11/2016.
 */
public final class LexicalDriver {

    private static final String filePath = "test.mashm";

    public static void main(final String[] args) throws IOException {
        Tokenizer tokenizer = Tokenizer.getInstance(filePath);
        List<List<Token>> tokens = tokenizer.getTokens();
        List<TokenError> tokenErrors = tokenizer.getTokenErrors();
        /*System.out.println("Tokens:\n");
        for(int i = 0 ; i < tokens.size() ; ++i) {
            System.out.print("line# " + (i+1) + ":\n");
            for(Token token : tokens.get(i)) {
                System.out.println(token.toString());
            }
            System.out.println();
            System.out.println();
        }*/
        System.out.println("Tokens erros:\n");
        for(TokenError tokenError : tokenErrors) {
            System.out.println(tokenError.toString());
        }

        GrammarReader g = GrammarReader.getInstance("D:\\projects\\javaProjects\\Compiler\\Grammar v3.txt");

        List<String> nonTerminals = new ArrayList<String>(){{
            add("program");
            add("statements");
            add("declaration");
            add("assignment");
            add("while_loop");
            add("if_condition");
            add("condition");
            add("conditionn");
            add("conditionnn");
            add("booleann");
            add("iden");
            add("num");
            add("number");
            add("expr");
            add("return_statement");
            add("ident");
            add("type");
            add("has_assignment");
            add("sign");
            add("integer");
            add("doublle");
            add("digits");
            add("after_digit");
            add("digit");
            add("identifier");
            add("character");
            add("after_character");
        }};

        TableCreator tableCreator = new TableCreator(g.getRules(), nonTerminals);
        System.out.println("Parse tree : ");
        ParseTree parseTree = new ParseTree(tableCreator.getParsingTable(), tokens);
    }

}
