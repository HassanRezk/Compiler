package test.parser;

import grammar.GrammarNode;

import java.util.*;


/**
 * Created by user on 12/25/2016.
 */
public class Parser {

    private Map<String, List<List<GrammarNode>>> rules;
    private Map<String,Set<String>> firstTable;
    private Map<String, Set<String>>  followTable;
    private Map<String, Map<String, String>> subsitituationTable = new HashMap<>();
    List<String> nonTerminals;
    Stack<GrammarNode> stackList = new Stack<>();

    public Parser(Map<String, List<List<GrammarNode>>> rules) {
        this.rules = rules;
        firstTable = new HashMap<String, Set<String>>(){
            {
                put("S", new HashSet<>());
                put("C", new HashSet<>());
                put("B", new HashSet<>());
                //put("W", new HashSet<>());
                //put("T", new HashSet<>());
                //put("", new HashSet<>());
            }
        };

        followTable = new HashMap<String, Set<String>>(){
            {
                put("S", new HashSet<String>(){{add("$");}});
                put("C", new HashSet<>());
                put("B", new HashSet<>());
                //put("W", new HashSet<>());
                //put("F", new HashSet<>());
                // put("F", new HashSet<>());
            }
        };


        nonTerminals  = new ArrayList<String>(){
            {
                add("S");
                add("C");
                add("B");
                //add("W");
                //add("F");
            }
        };
        for (int i = 0; i < nonTerminals.size(); ++i) {
            buildFirstTable(nonTerminals.get(i), 0, 0);
        }
        for (int i = 0; i < nonTerminals.size(); ++i){
            buildFollowTable(new GrammarNode(nonTerminals.get(i), false));
        }
        for(int i = 0; i < firstTable.size(); ++i){
            System.out.println(nonTerminals.get(i) + "\t" + followTable.get(nonTerminals.get(i)));
        }
    }

    private void buildFirstTable(String currentNonTerminal, int currentIndex, int iteration){

        List<List<GrammarNode>> currentRules = rules.get(currentNonTerminal);
        List<String> terminals = new ArrayList<>();

        for (int i = iteration; i < currentRules.size(); ++i) {
            GrammarNode currentNode = currentRules.get(i).get(currentIndex);

            if(currentNode.isTerminal()) {
                firstTable.get(currentNonTerminal).add(currentNode.getValue());
                currentIndex = 0;
                //System.out.println(currentNonTerminal + " : " + firstTable.get(currentNonTerminal));
            } else {
                buildFirstTable(currentNode.getValue(), 0, 0);

                if(firstTable.get(currentNode.getValue()).contains("empty")){

                    if(currentIndex + 1 >= currentRules.get(i).size()) {
                        firstTable.get(currentNonTerminal).addAll(firstTable.get(currentNode.getValue()));
                        currentIndex = 0;
                    }
                    else {
                        buildFirstTable(currentNonTerminal, currentIndex + 1, i);
                        terminals.addAll(firstTable.get(currentRules.get(i).get(currentIndex).getValue()));
                        firstTable.get(currentNonTerminal).addAll(terminals);
                    }

                } else {
                    firstTable.get(currentNonTerminal).addAll(firstTable.get(currentNode.getValue()));
                    currentIndex = 0;
                }
            }
        }
    }

    private void buildFollowTable(GrammarNode currentNonTerminalNode){
        for(int i = 0; i < nonTerminals.size(); ++i) {
            List<List<GrammarNode>> currentRule = rules.get(nonTerminals.get(i));
            for(int j = 0; j < currentRule.size(); ++j ){
                for(int y = 0; y < currentRule.get(j).size(); ++y){
                    if(currentRule.get(j).get(y).getValue().equals(currentNonTerminalNode.getValue())){
                        int index = y;
                        if(index + 1 == currentRule.get(j).size()){
                            followTable.get(currentNonTerminalNode.getValue()).addAll(followTable.get(nonTerminals.get(i)));
                            break;
                        }else if(currentRule.get(j).get(index+1).isTerminal()){
                            followTable.get(currentNonTerminalNode.getValue()).add(currentRule.get(j).get(index+1).getValue());
                            break;
                        } else {
                            GrammarNode nextGrammarNode = currentRule.get(j).get(index+1);
                            if(firstTable.get(nextGrammarNode.getValue()).contains("empty")){
                                followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(nextGrammarNode.getValue()));
                                followTable.get(currentNonTerminalNode.getValue()).remove("empty");
                                emptyCase(currentNonTerminalNode, currentRule.get(j), index + 1, nonTerminals.get(i));
                            } else {
                                followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(nextGrammarNode.getValue()));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void emptyCase(GrammarNode currentNonTerminalNode, List<GrammarNode> rule, int currentNodeIndex, String key){
        GrammarNode currentNode = rule.get(currentNodeIndex);
        if (currentNode.isTerminal()) {
            followTable.get(currentNonTerminalNode.getValue()).add(currentNode.getValue());
        } else if(firstTable.get(currentNode.getValue()).contains("empty")){
            followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(currentNode.getValue()));
            followTable.get(currentNonTerminalNode.getValue()).remove("empty");
            if(currentNodeIndex + 1 == rule.size()) {
                followTable.get(currentNonTerminalNode.getValue()).addAll(followTable.get(key));
                return;
            }
            emptyCase(currentNonTerminalNode, rule, currentNodeIndex + 1, key);
        } else {
            followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(currentNode.getValue()));
        }
    }

}
