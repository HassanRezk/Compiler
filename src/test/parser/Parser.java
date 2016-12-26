package test.parser;

import grammar.GrammarNode;

import java.util.*;


/**
 * Created by user on 12/25/2016.
 */
public class Parser {

    private Map<String, List<List<GrammarNode>>> rules;
    private Map<String, Set<String>> firstTable, followTable;
    private Map<String, Map<String, String>> subsitituationTable = new HashMap<>();
    Stack<GrammarNode> stackList = new Stack<>();

    public Parser(Map<String, List<List<GrammarNode>>> rules) {
        this.rules = rules;
        firstTable = new HashMap<String, Set<String>>(){
            {
                put("S", new HashSet<>());
                put("A", new HashSet<>());
                put("B", new HashSet<>());
                put("C", new HashSet<>());
                //put("T", new HashSet<>());
               // put("F", new HashSet<>());
            }
        };


        List<String> nonTerminals  = new ArrayList<>(rules.keySet());
        for (int i = 0; i < nonTerminals.size(); ++i) {
            buildFirstTable(nonTerminals.get(i), 0, 0);
        }
        for(int i = 0; i < firstTable.size(); ++i){
            System.out.println(nonTerminals.get(i) + "     " + firstTable.get(nonTerminals.get(i)));
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
                        terminals.remove("empty");
                        firstTable.get(currentNonTerminal).addAll(terminals);
                    }

                } else {
                    firstTable.get(currentNonTerminal).addAll(firstTable.get(currentNode.getValue()));
                    currentIndex = 0;
                }
            }
        }
    }



}
