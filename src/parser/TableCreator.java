package parser;

import grammar.GrammarNode;

import java.util.*;


/**
 * Created by Hammam on 12/25/2016.
 */
public class TableCreator {

    private Map<String, List<List<GrammarNode>>> rules;
    private Map<String,Set<String>> firstTable = new HashMap<>();
    private Map<String, Set<String>>  followTable = new HashMap<>();
    private Map<String, Map<String, List<GrammarNode>>> parsingTable = new HashMap<>();
    List<String> nonTerminals, terminals;

    public TableCreator(Map<String, List<List<GrammarNode>>> rules,  List<String> nonTerminals) {
        this.rules = rules;
        this.nonTerminals = nonTerminals;
        initTables(nonTerminals);

        for (int i = 0; i < nonTerminals.size(); ++i) {
            buildFirstTable(nonTerminals.get(i), 0, 0, false);
        }
        for (int i = 0; i < nonTerminals.size(); ++i){
            buildFollowTable(new GrammarNode(nonTerminals.get(i), false));
        }
        buildParsingTable();
        terminals = new ArrayList<String>(){
            {
                add("start");
            }
        };

        /*for (int i =0; i <nonTerminals.size(); ++i) {
            System.out.println(firstTable.get(nonTerminals.get(i)) + " \t " + nonTerminals.get(i) + " \t " + followTable.get(nonTerminals.get(i)));
        }*/
        /*for(int i = 0; i < nonTerminals.size(); ++i ){
            System.out.println(nonTerminals.get(i) + ":");
            for(int j = 0; j < terminals.size(); ++j){
                List<GrammarNode> currentRule = parsingTable.get(nonTerminals.get(i)).get(terminals.get(j));
                System.out.println("\t" + terminals.get(j) + ": " );
                if(currentRule == null) continue;
                for(int y = 0; y < currentRule.size();++y){
                    System.out.print("\t\t" + parsingTable.get(nonTerminals.get(i)).get(terminals.get(j)).get(y).getValue());
                }
                System.out.println();
            }
        }*/
    }

    private void initTables(List<String> nonTerminlas) {
        for(int i = 0; i < nonTerminlas.size(); ++i ){
            firstTable.put(nonTerminlas.get(i), new HashSet<>());
            if(nonTerminlas.get(i).equals("program")) followTable.put(nonTerminlas.get(i), new HashSet<String>(){{add("$");}});
            else followTable.put(nonTerminlas.get(i), new HashSet<>());
        }

    }

    private void buildFirstTable(String currentNonTerminal, int currentIndex, int iteration, boolean epsilonCase){

        List<List<GrammarNode>> currentRules = rules.get(currentNonTerminal);
        List<String> terminals = new ArrayList<>();
       // System.out.println(currentNonTerminal + " : " + currentRules);
        for (int i = iteration; i < currentRules.size(); ++i) {
            GrammarNode currentNode = currentRules.get(i).get(currentIndex);

            if(epsilonCase){
               //System.out.println(currentNonTerminal);
                firstTable.get(currentNonTerminal).remove("EPSILON");
                epsilonCase = false;
            }
            if(currentNode.isTerminal()) {
                firstTable.get(currentNonTerminal).add(currentNode.getValue());
                currentIndex = 0;
            } else {
                //System.out.println(currentNode.getValue());
                buildFirstTable(currentNode.getValue(), 0, 0, false);

                if(firstTable.get(currentNode.getValue()).contains("EPSILON")){
                    if(currentIndex + 1 >= currentRules.get(i).size()) {
                      //  System.out.println(currentNonTerminal + " : " + currentNode.getValue() + " : " + firstTable.get(currentNode.getValue()));
                        firstTable.get(currentNonTerminal).addAll(firstTable.get(currentNode.getValue()));
                        currentIndex = 0;
                    }
                    else {
                        buildFirstTable(currentNonTerminal, currentIndex + 1, i, true);
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
                        }else if(currentRule.get(j).get(index+1).isTerminal()){

                            followTable.get(currentNonTerminalNode.getValue()).add(currentRule.get(j).get(index+1).getValue());
                        } else {
                            GrammarNode nextGrammarNode = currentRule.get(j).get(index+1);
                            if(firstTable.get(nextGrammarNode.getValue()).contains("EPSILON")){
                                followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(nextGrammarNode.getValue()));
                                followTable.get(currentNonTerminalNode.getValue()).remove("EPSILON");
                                followTableEmptyCase(currentNonTerminalNode, currentRule.get(j), index + 1, nonTerminals.get(i));
                            } else {
                                followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(nextGrammarNode.getValue()));
                            }
                        }
                    }
                }
            }
        }
    }

    private void followTableEmptyCase(GrammarNode currentNonTerminalNode, List<GrammarNode> rule, int currentNodeIndex, String key){
        GrammarNode currentNode = rule.get(currentNodeIndex);
        if (currentNode.isTerminal()) {
            followTable.get(currentNonTerminalNode.getValue()).add(currentNode.getValue());
        } else if(firstTable.get(currentNode.getValue()).contains("EPSILON")){
            followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(currentNode.getValue()));
            followTable.get(currentNonTerminalNode.getValue()).remove("EPSILON");
            if(currentNodeIndex + 1 == rule.size()) {
                followTable.get(currentNonTerminalNode.getValue()).addAll(followTable.get(key));
                return;
            }
            followTableEmptyCase(currentNonTerminalNode, rule, currentNodeIndex + 1, key);
        } else {
            followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(currentNode.getValue()));
        }
    }


    private void buildParsingTable(){
        for (int nonTerminalCounter = 0; nonTerminalCounter < nonTerminals.size(); ++nonTerminalCounter) {
            Map<String, List<GrammarNode>> currentCarrier = new HashMap<>();

            List<List<GrammarNode>> currentNonTerminalRules = rules.get(nonTerminals.get(nonTerminalCounter));
            for(int ruleIndex = 0; ruleIndex < currentNonTerminalRules.size(); ++ruleIndex) {

                List<GrammarNode> currentRule = currentNonTerminalRules.get(ruleIndex);
                for (int grammarNodeIndex = 0; grammarNodeIndex < currentRule.size(); ++grammarNodeIndex){
                    if (currentRule.get(grammarNodeIndex).isTerminal() && !currentRule.get(grammarNodeIndex).getValue().equals("EPSILON")) {
                        GrammarNode currentTerminal = currentRule.get(grammarNodeIndex);
                        currentCarrier.put(currentTerminal.getValue(), currentRule);
                        parsingTable.put(nonTerminals.get(nonTerminalCounter), currentCarrier);
                        break;
                    }
                    if(currentRule.get(grammarNodeIndex).getValue().equals("EPSILON")){
                        for( String terminal : followTable.get(nonTerminals.get(nonTerminalCounter))) {
                            currentCarrier.put(terminal, currentRule);
                        }
                        parsingTable.put(nonTerminals.get(nonTerminalCounter), currentCarrier);
                        break;
                    }
                    Set<String> firstTerminls = firstTable.get(currentRule.get(grammarNodeIndex).getValue());
                    if(firstTerminls.contains("EPSILON")){
                        firstTerminls.remove("EPSILON");
                        for (String terminal : firstTerminls){
                            currentCarrier.put(terminal, currentRule);
                        }
                        if(grammarNodeIndex + 1 == currentRule.size()) {
                            for( String terminal : followTable.get(nonTerminals.get(nonTerminalCounter))) {
                                currentCarrier.put(terminal, currentRule);
                            }
                            parsingTable.put(nonTerminals.get(nonTerminalCounter), currentCarrier);
                            continue;
                        }
                        parsingTable.put(nonTerminals.get(nonTerminalCounter), currentCarrier);
                        paringTableEmptyCase(nonTerminals.get(nonTerminalCounter), currentRule, grammarNodeIndex+1, currentCarrier);
                    } else {
                        for (String terminal : firstTerminls){
                            currentCarrier.put(terminal, currentRule);
                        }
                        parsingTable.put(nonTerminals.get(nonTerminalCounter), currentCarrier);
                        break;
                    }
                }
            }
        }
    }

    private void paringTableEmptyCase(String nonTerminal, List<GrammarNode> currentRule, int index, Map<String, List<GrammarNode>> currentCarrier) {
        //  System.out.println(currentRule.get(0).getValue());
        GrammarNode currentNode = currentRule.get(index);

        if (currentNode.isTerminal()){
            currentCarrier.put(currentNode.getValue(),currentRule);
            parsingTable.put(nonTerminal, currentCarrier);
        } else {
            Set<String> firstTerminls = firstTable.get(currentNode.getValue());
            if(firstTerminls.contains("EPSILON")){
                // firstTerminls.remove("empty");
                for (String terminal : firstTerminls){
                    currentCarrier.put(terminal, currentRule);
                }
                if(index + 1 == currentRule.size()) {
                    for( String terminal : followTable.get(nonTerminal)) {
                        currentCarrier.put(terminal, currentRule);
                    }
                    parsingTable.put(nonTerminal, currentCarrier);
                    return;
                }
                parsingTable.put(nonTerminal, currentCarrier);
                paringTableEmptyCase(nonTerminal, currentRule, index+1, currentCarrier);
            } else {
                for (String terminal : firstTerminls){
                    currentCarrier.put(terminal, currentRule);
                }
                parsingTable.put(nonTerminal, currentCarrier);
            }
        }
    }

    public Map<String, Map<String, List<GrammarNode>>> getParsingTable(){
        return parsingTable;
    }
}
