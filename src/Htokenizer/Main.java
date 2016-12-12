package Htokenizer;

import java.util.List;

/**
 * Created by Hammam on 12/12/2016.
 */
public class Main {
    public static void main(String[] args){
        Tokenizer tokenizer = new Tokenizer("foreach_x(int a = 0 ){{{(})[}( >=== ++ -- *= \"Aadasasfa$%#$dasdas\" 'a';");
        List<Token> tokens = tokenizer.getLineComponents();
        for (int i = 0; i < tokens.size(); i++){
            System.out.println(tokens.get(i).getTokenText() + "\t, " + tokens.get(i).getTokenType());
        }

    }

}
