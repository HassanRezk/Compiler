package driver;

import lexical_analysis.Token;

import java.util.List;

/**
 * Created by Hassan on 12/10/2016.
 */
public class Tokenizer {

    public static void main(final String[] args) {
        Token t = new Token("start int t; t = 0; char c = 'a'; end");
        List<Token.tokens> tokens = t.getTokens();

        for(int i = 0 ; i < tokens.size(); i++) {
            System.out.println(tokens.get(i).text + "\t," + tokens.get(i).tokenTYpe);
        }
    }
}
