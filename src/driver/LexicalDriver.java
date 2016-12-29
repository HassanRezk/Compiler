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
            add("doublle");
            add("boolean");
            add("has_assignment");
            add("integer");
            add("digits");
            add("after_digit");
            /*add("E");
            add("E'");
            add("T");
            add("T'");
            add("F");*/
        }};
        Map<String, List<List<GrammarNode>>> x = new HashMap<String, List<List<GrammarNode>>>(){
            {
                put("E", new ArrayList<List<GrammarNode>>(){{
                    add(new ArrayList<GrammarNode>(){{
                        add(new GrammarNode("T", false));
                        add(new GrammarNode("E'", false));
                    }});
                }});

                put("E'", new ArrayList<List<GrammarNode>>(){{
                    add(new ArrayList<GrammarNode>(){{
                        add(new GrammarNode("+", true));
                        add(new GrammarNode("T", false));
                        add(new GrammarNode("E'", false));
                    }});

                    add(new ArrayList<GrammarNode>(){{
                        add(new GrammarNode("EPSILON", true));
                    }});
                }});

                put("T", new ArrayList<List<GrammarNode>>(){{
                    add(new ArrayList<GrammarNode>(){{
                        add(new GrammarNode("F", false));
                        add(new GrammarNode("T'", false));
                    }});
                }});

                put("T'", new ArrayList<List<GrammarNode>>(){{
                    add(new ArrayList<GrammarNode>(){{
                        add(new GrammarNode("*", true));
                        add(new GrammarNode("F", false));
                        add(new GrammarNode("T'", false));
                    }});

                    add(new ArrayList<GrammarNode>(){{
                        add(new GrammarNode("EPSILON", true));
                    }});
                }});

                put("F", new ArrayList<List<GrammarNode>>(){{
                    add(new ArrayList<GrammarNode>(){{
                        add(new GrammarNode("id", true));
                    }});

                    add(new ArrayList<GrammarNode>(){{
                        add(new GrammarNode("(", true));
                        add(new GrammarNode("E", false));
                        add(new GrammarNode(")", true));
                    }});
                }});

            }
        };

        TableCreator tableCreator = new TableCreator(g.getRules(), nonTerminals);

        System.out.println("Parse tree : ");
        ParseTree parseTree = new ParseTree(tableCreator.getParsingTable(), tokens);
    }

}
