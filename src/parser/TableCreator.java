package parser;

import grammar.GrammarNode;

import java.util.*;


/**
 * Created by Hammam on 12/25/2016.
 */
public class TableCreator {

    private Map<String, List<List<GrammarNode>>> rules;
    private Map<String, Set<String>> firstTable = new HashMap<>();
    private Map<String, Set<String>>  followTable = new HashMap<>();
    private Map<String, Map<String, List<GrammarNode>>> parsingTable = new HashMap<>();
    List<String> nonTerminals;

    public TableCreator(Map<String, List<List<GrammarNode>>> rules,  List<String> nonTerminals) {
        this.rules = rules;
        this.nonTerminals = nonTerminals;
        initTables(nonTerminals);

        for (int i = 0; i < nonTerminals.size(); ++i) {
            buildFirstTable(nonTerminals.get(i));
        }
        for (int i = 0; i < nonTerminals.size(); ++i){
            buildFollowTable(nonTerminals.get(i));
        }
       buildParsingTable();
    }

    private void initTables(List<String> nonTerminlas) {
        for(int i = 0; i < nonTerminlas.size(); ++i ){
            firstTable.put(nonTerminlas.get(i), new HashSet<>());
            if(nonTerminlas.get(i).equals("program")) followTable.put(nonTerminlas.get(i), new HashSet<String>(){{add("$");}});
            else followTable.put(nonTerminlas.get(i), new HashSet<>());
        }

    }

    private void buildFirstTable(String currentNonTerminal) {
        List<List<GrammarNode>> currentNonTerminalRules = rules.get(currentNonTerminal);
        for (int ruleIndex = 0; ruleIndex < currentNonTerminalRules.size(); ++ruleIndex)
        {
            List<GrammarNode> currentRule = currentNonTerminalRules.get(ruleIndex);
            firstTable.get(currentNonTerminal).addAll(getMyFirstTerminals(currentRule.get(0), new HashSet<>(), currentRule, 0));
        }
    }

    private Set<String> getMyFirstTerminals(GrammarNode firstElement, Set<String> carrierSet, List<GrammarNode> parentRule, int ElementIndex) {
        if(firstElement.isTerminal()){
            carrierSet.add(firstElement.getValue());
        } else {
            List<List<GrammarNode>> currentNonTerminalRules = rules.get(firstElement.getValue());
            for (int ruleIndex = 0; ruleIndex < currentNonTerminalRules.size(); ++ruleIndex)
            {
                List<GrammarNode> currentRule = currentNonTerminalRules.get(ruleIndex);
                //System.out.println(firstElement.getValue() + " : " + firstTable.get(firstElement.getValue()));
                firstTable.get(firstElement.getValue()).addAll(getMyFirstTerminals(currentRule.get(0), new HashSet<>(), currentRule, 0));

            }
            carrierSet.addAll(firstTable.get(firstElement.getValue()));
            if(carrierSet.contains("EPSILON")){
                if(ElementIndex + 1 == parentRule.size()) return carrierSet;
                getMyFirstTerminals(parentRule.get(ElementIndex + 1), carrierSet, parentRule, ElementIndex + 1);
            }
        }
        return carrierSet;
    }

    private void buildFollowTable(String currentNonTerminal) {
        for (int i = 0; i < nonTerminals.size(); ++ i) {
            List<List<GrammarNode>> currentNonTerminalRules = rules.get(nonTerminals.get(i));
            for (int j = 0; j < currentNonTerminalRules.size(); ++j) {
                List<GrammarNode> currentRule = currentNonTerminalRules.get(j);
                for (int y = 0; y < currentRule.size(); ++ y ){
                    GrammarNode currentGrammarNode = currentRule.get(y);
                    //System.out.println(currentNonTerminal + " : currentNonTerminal : " + nonTerminals.get(i) + " : currentSearchNode : " + currentGrammarNode.getValue());
                    if(currentGrammarNode.getValue().equals(currentNonTerminal)){
                        if(y + 1 == currentRule.size()) {
                            followTable.get(currentNonTerminal).addAll(followTable.get(nonTerminals.get(i)));
                        }else {
                            followTable.get(currentNonTerminal).addAll(getMyFollowTerminals(nonTerminals.get(i),currentRule, y + 1, new HashSet<>(), currentNonTerminal));
                        }
                    }
                }
            }
        }
    }

    private Set<String> getMyFollowTerminals(String key, List<GrammarNode> rule, int currentIndex, Set<String> carrierSet, String currentTarget){
        if(currentIndex + 1 == rule.size()){
            GrammarNode followElement = rule.get(currentIndex);
            if(followElement.isTerminal()){
                carrierSet.add(followElement.getValue());
            } else {
                Set<String> followElementTerminals = firstTable.get(followElement.getValue());
                if(followElementTerminals.contains("EPSILON")){
                    carrierSet.addAll(followElementTerminals);
                    carrierSet.remove("EPSILON");
                    carrierSet.addAll(followTable.get(key));
                } else {
                    carrierSet.addAll(firstTable.get(followElement.getValue()));
                }
            }
        } else {
            GrammarNode followElement = rule.get(currentIndex);
            if(followElement.isTerminal()) {
                carrierSet.add(followElement.getValue());
            } else {
                Set<String> followElementTerminals = firstTable.get(followElement.getValue());
                if(followElementTerminals.contains("EPSILON")){
                    carrierSet.addAll(followElementTerminals);
                    carrierSet.remove("EPSILON");
                    getMyFollowTerminals(key, rule, currentIndex + 1, carrierSet, currentTarget);
                } else {
                    carrierSet.addAll(followElementTerminals);
                }
            }
        }
        return carrierSet;
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
