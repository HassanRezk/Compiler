package lexical_analysis;

import javafx.scene.Parent;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hassan on 12/10/2016.
 */
public class Token {

    public String sourceCode;

    private List<tokens> tokens = new ArrayList<tokens>();
    private final Pattern intNumbers = Pattern.compile("[0-9]+");
    private final Pattern floatNumbers = Pattern.compile("[0-9]+\\.[0-9]+");
    private final Pattern characters = Pattern.compile("'.?'|\".?\"");
    private final Pattern strings = Pattern.compile("'.*'|\".*\"");
    private final Pattern booleans = Pattern.compile("true|false");
    private final Pattern identifiers = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
    private final Pattern operators = Pattern.compile("(\\|\\|)|(&&)|(\\*=)|(/=)|(\\+=)|(-=)|(>=)|(<=)|(==)|(!=)|(\\+)|(-)|(\\*)|(/)|(%)|(<)|(>)|(=)|(&)|(\\|)");
    private final Pattern semicolon = Pattern.compile(";");
    private final Pattern comma = Pattern.compile("\\,");
    private final Pattern keywords = Pattern.compile("start|int|long|float|String|char|double|boolean|else if|else|if|end");


    public Token(String sourceCode){
        this.sourceCode = sourceCode;
    }

    public List<tokens> getTokens () {
        while (sourceCode.length() != 0){
            boolean goodToken = filltokens(keywords, TokenType.KEYWORD) || filltokens(operators, TokenType.OPERATOR)
                                || filltokens(identifiers, TokenType.IDENTIFIER) || filltokens(booleans, TokenType.BOOLEAN)
                                || filltokens(strings, TokenType.IDENTIFIER) || filltokens(characters, TokenType.IDENTIFIER)
                                || filltokens(floatNumbers, TokenType.FLOAT) || filltokens(intNumbers, TokenType.INT)
                                || filltokens(semicolon, TokenType.SEMICOLON) || filltokens(comma, TokenType.COMMA);
            if(!goodToken){
                System.out.println("error" + sourceCode.substring(0,(sourceCode.length()-1))); break;
            }
        }
        return tokens;
    }

    private boolean filltokens(Pattern currentPatternUsage, TokenType tokenType){
        Matcher m = currentPatternUsage.matcher(sourceCode);
        if(m.lookingAt()){
            tokens.add(new tokens(m.group(), tokenType));
            sourceCode = (sourceCode.substring(m.group().length(), sourceCode.length())).trim();
            return true;
        }
        return false;
    }

    public class tokens{
        public final String text;
        public final TokenType tokenTYpe;

        public tokens(String text, TokenType tokenType) {
            this.text = text;
            this.tokenTYpe = tokenType;
        }
    }
}
