package lexical_analysis_regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 12/12/2016.
 */
public class Tokenizer {

    private String line;
    private List<Token> tokens = new ArrayList<Token>();
    private final Pattern integerNumbers = Pattern.compile("^[0-9]+\\b");
    private final Pattern floatNumbers = Pattern.compile("^[0-9]+\\.[0-9]+\\b");
    private final Pattern characters = Pattern.compile("'.?'");
    private final Pattern Strings =Pattern.compile("\"[^\"]*\"");
    private final Pattern quat = Pattern.compile("'");
    private final Pattern doubleQuat = Pattern.compile("\"");
    private final Pattern booleann = Pattern.compile("^false\\b|^true\\b");
    private final Pattern keywords = Pattern.compile("^start\\b|^int\\b|^long\\b|^float\\b|^String\\b|^char\\b|^double\\b|^boolean\\b|^else\\s+if\\b|^else\\b|^if\\b" +
                                                     "|^for\\b|^foreach\\b|^do\\b|^while\\b|^end");
    private final Pattern identifiers = Pattern.compile("[a-zA-Z0-9_]+");
    private final Pattern operators = Pattern.compile("[\\*|/|\\+|\\-|%|>|<|=|~|!|&|\\|]+");
    private final Pattern parentheses = Pattern.compile("\\(|\\)");
    private final Pattern squareBrackets = Pattern.compile("\\[|\\]");
    private final Pattern curlyBrackets = Pattern.compile("\\{|\\}");
    private final Pattern semicolon = Pattern.compile(";");
    private final Pattern comma = Pattern.compile("\\,");
    private final Pattern comment = Pattern.compile("^//+");
    private final Pattern dot = Pattern.compile("\\.");

    public Tokenizer(final String line) {
        this.line = line;
    }

    public List<Token> getLineComponents() {
        while (line.length() != 0){
            boolean isComment = filltokens(comment, null);
            if(isComment) break;
            boolean ok =filltokens(keywords, Token.TokenType.KEYWORD) || filltokens(booleann, Token.TokenType.BOOLEAN)
                    ||filltokens(operators, Token.TokenType.OPERATOR) ||filltokens(floatNumbers, Token.TokenType.FLOAT)
                    || filltokens(integerNumbers, Token.TokenType.INT) || filltokens(characters, Token.TokenType.CHAR)
                    || filltokens(Strings, Token.TokenType.STRING) || filltokens(quat, Token.TokenType.QAUT)
                    || filltokens(doubleQuat, Token.TokenType.DOUBLEQAUT) ||filltokens(identifiers, Token.TokenType.IDENTIFIER)
                    || filltokens(semicolon, Token.TokenType.SEMICOLON) || filltokens(comma, Token.TokenType.COMMA)
                    || filltokens(curlyBrackets, Token.TokenType.CURLY_BRACKETS) || filltokens( parentheses, Token.TokenType.PARENTHESES)
                    || filltokens(squareBrackets, Token.TokenType.SQUARE_BRACKETS) || filltokens(dot, Token.TokenType.DOT);
           // System.out.println(line);
            if(!ok){
                tokens.add(new Token(line.substring(0, 1), Token.TokenType.NOTVALIDTOKEN));
                line = (line.substring(1, line.length())).trim();
            }
        }
        return tokens;
    }
    private boolean filltokens(Pattern currentPatternUsage, Token.TokenType tokenType) {
        Matcher m = currentPatternUsage.matcher(line);
        if(m.lookingAt()){
            if(currentPatternUsage == comment) {System.out.println("comment");return true;}
            tokens.add(new Token(m.group(), tokenType));
            line = (line.substring(m.group().length(), line.length())).trim();
            return true;
        }
        return false;
    }

}