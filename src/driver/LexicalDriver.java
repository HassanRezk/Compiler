package driver;

import lexical_analysis.Token;
import lexical_analysis.TokenError;
import lexical_analysis.Tokenizer;

import java.io.IOException;
import java.util.List;

/**
 * Created by Hassan on 12/11/2016.
 */
public final class LexicalDriver {

    private static final String filePath = "lexical_analysis_regex.mashm";

    public static void main(final String[] args) throws IOException {
        StringBuilder sb = new StringBuilder("");
        for(char c = 'a' ; c <= 'z' ; ++c) {
            sb.append("'" + c + "'" + " | ");
        }
        for(char c = 'A' ; c <= 'Z' ; ++c) {
            sb.append("'" + c + "'" + " |");
        }
        sb.append(" '_'");
        System.out.println(sb.toString());
        if(true) return;
        Tokenizer tokenizer = Tokenizer.getInstance(filePath);
        List<List<Token>> tokens = tokenizer.getTokens();
        List<TokenError> tokenErrors = tokenizer.getTokenErrors();
        System.out.println("Tokens:\n");
        for(int i = 0 ; i < tokens.size() ; ++i) {
            System.out.print("line# " + (i+1) + ":\n");
            for(Token token : tokens.get(i)) {
                System.out.println(token.toString());
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Tokens erros:\n");
        for(TokenError tokenError : tokenErrors) {
            System.out.println(tokenError.toString());
        }
    }

}
