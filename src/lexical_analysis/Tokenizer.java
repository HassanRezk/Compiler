package lexical_analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hassan on 12/10/2016.
 */
public final class Tokenizer {

    private static List<List<Token>> tokens;
    private static List<List<TokenError>> tokenErrors;

    private static final Tokenizer instance = new Tokenizer();

    private Tokenizer() {}

    public static Tokenizer getInstance(final String filePath) throws IOException {
        tokens = new ArrayList<>();
        tokenErrors = new ArrayList<>();
        DFA dfa = DFA.getInstance();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            tokens.add(dfa.getTokens(line));
            tokenErrors.add(dfa.getTokenErrors());
        }
        return instance;
    }

    public List<List<Token>> getTokens() throws IOException {
        return tokens;
    }

    public List<List<TokenError>> getTokenErrors() {
        return tokenErrors;
    }
}
