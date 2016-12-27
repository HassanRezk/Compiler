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
    List<String> nonTerminals;

    public TableCreator(Map<String, List<List<GrammarNode>>> rules,  List<String> nonTerminals) {
        this.rules = rules;
        this.nonTerminals = nonTerminals;
        initTables(nonTerminals);

        for (int i = 0; i < nonTerminals.size(); ++i) {
            buildFirstTable(nonTerminals.get(i), 0, 0);
        }
        for (int i = 0; i < nonTerminals.size(); ++i){
            buildFollowTable(new GrammarNode(nonTerminals.get(i), false));
        }

        buildParsingTable();
    }

    private void initTables(List<String> nonTerminlas) {
        for(int i = 0; i < nonTerminlas.size(); ++i ){
            firstTable.put(nonTerminlas.get(i), new HashSet<>());
            if(i == 0 ) followTable.put(nonTerminlas.get(i), new HashSet<String>(){{add("$");}});
            if(i > 0) followTable.put(nonTerminlas.get(i), new HashSet<>());
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
                        }else if(currentRule.get(j).get(index+1).isTerminal()){
                            followTable.get(currentNonTerminalNode.getValue()).add(currentRule.get(j).get(index+1).getValue());
                        } else {
                            GrammarNode nextGrammarNode = currentRule.get(j).get(index+1);
                            if(firstTable.get(nextGrammarNode.getValue()).contains("empty")){
                                followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(nextGrammarNode.getValue()));
                                followTable.get(currentNonTerminalNode.getValue()).remove("empty");
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
        } else if(firstTable.get(currentNode.getValue()).contains("empty")){
            followTable.get(currentNonTerminalNode.getValue()).addAll(firstTable.get(currentNode.getValue()));
            followTable.get(currentNonTerminalNode.getValue()).remove("empty");
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
                    if (currentRule.get(grammarNodeIndex).isTerminal() && !currentRule.get(grammarNodeIndex).getValue().equals("empty")) {
                        GrammarNode currentTerminal = currentRule.get(grammarNodeIndex);
                        currentCarrier.put(currentTerminal.getValue(), currentRule);
                        parsingTable.put(nonTerminals.get(nonTerminalCounter), currentCarrier);
                        break;
                    }
                    if(currentRule.get(grammarNodeIndex).getValue().equals("empty")){
                        for( String terminal : followTable.get(nonTerminals.get(nonTerminalCounter))) {
                            currentCarrier.put(terminal, currentRule);
                        }
                        parsingTable.put(nonTerminals.get(nonTerminalCounter), currentCarrier);
                        break;
                    }
                    Set<String> firstTerminls = firstTable.get(currentRule.get(grammarNodeIndex).getValue());
                    if(firstTerminls.contains("empty")){
                        firstTerminls.remove("empty");
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
            System.out.println(parsingTable.get(nonTerminal).get(currentNode.getValue()));
        } else {
            Set<String> firstTerminls = firstTable.get(currentNode.getValue());
            if(firstTerminls.contains("empty")){
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
