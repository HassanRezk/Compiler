package grammar;

import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created by Hassan on 12/25/2016.
 */
public class GrammarReader {

    private static final GrammarReader instance = new GrammarReader();

    private static String grammarFilePath;

    private static Map<String, List<List<GrammarNode>>> rules;

    private static Set<String> terminals;

    private static StringBuilder printer;

    private GrammarReader() {}

    public static GrammarReader getInstance(final String grammarFilePath) throws IOException {
        GrammarReader.grammarFilePath = grammarFilePath;
        rules = new HashMap<>();
        createRules();
        printer = createPrinter();
        terminals = new HashSet<>();
        return instance;
    }

    public Map<String, List<List<GrammarNode>>> getRules() {
        return rules;
    }

    public List<String> getNonTerminals() {
        ArrayList<String> terminalList = new ArrayList<>();
        terminalList.addAll(terminals);
        return terminalList;
    }

    @Override
    public String toString() {
        return GrammarReader.printer.toString();
    }

    private static StringBuilder createPrinter() {
        StringBuilder sb = new StringBuilder("");
        for(Map.Entry<String, List<List<GrammarNode>>> entry : rules.entrySet()) {
            sb.append(entry.getKey() + " -> ");
            List<List<GrammarNode>> value = entry.getValue();
            for(List<GrammarNode> statement : value) {
                for(GrammarNode grammarNode : statement) {
                    sb.append(grammarNode.toString() + " ");
                }
                sb.append("\n");
            }
            sb.append("\n##################################################################\n");
        }
        return sb;
    }

    private static void createRules() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(grammarFilePath));
        String line;
        String ruleName = "";
        List<List<GrammarNode>> rulesList = new ArrayList<>();
        List<GrammarNode> ruleList = new ArrayList<>();
        while((line = br.readLine()) != null) {
            line = line.trim();
            if(line.isEmpty()) {
                continue;
            }
            String[] ruleSplitter = line.split("::=");
            if(line.charAt(0) == '#') {
                if(!ruleName.isEmpty()) {
                    if(rules.containsKey(ruleName)) {
                        throw new IllegalArgumentException("Invalid rule statement.");
                    }
                    rules.put(ruleName, rulesList);
                    rulesList = new ArrayList<>();
                }
                ruleName = ruleSplitter[0].substring(1).trim();
                ruleName = ruleName.substring(1, ruleName.length() - 1);
            }
            String[] tokens = ruleSplitter[ruleSplitter.length - 1].split(" ");
            for(String token : tokens) {
                if(token.trim().isEmpty()) {
                    continue;
                }
                if(token.charAt(0) == '|' && !ruleList.isEmpty()) {
                    rulesList.add(ruleList);
                    ruleList = new ArrayList<>();
                } else {
                    String tokenValue = token;
                    if(tokenValue.charAt(0) == '\'' || tokenValue.charAt(0) == '<') {
                        tokenValue = token.substring(1, token.length() - 1);
                    }
                    boolean isTerminal = token.charAt(0) == '\'';
                    if(isTerminal) {
                        terminals.add(tokenValue);
                    }
                    ruleList.add(new GrammarNode(tokenValue, isTerminal));
                }
            }
            if(!ruleList.isEmpty()) {
                rulesList.add(ruleList);
                ruleList = new ArrayList<>();
            }
        }
    }

}
