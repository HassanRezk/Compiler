package test.parser;

import grammar.GrammarNode;
import grammar.GrammarReader;

import java.io.IOException;
import java.util.Map;
import java.util.List;

/**
 * Created by user on 12/25/2016.
 */
public class main {

    public static void main(String[] args) throws IOException {
        GrammarReader g = GrammarReader.getInstance("");
        Parser p = new Parser(g.getRules());

    }
}
