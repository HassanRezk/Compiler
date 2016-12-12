package tokenizer;

/**
 * Created by Hammam on 12/12/2016.
 */
public class Token {
    public static enum TokenType{
        PARENTHESES,
        CURLY_BRACKETS,
        SQUARE_BRACKETS,
        KEYWORD,
        OPERATOR,
        IDENTIFIER,
        BOOLEAN,
        SEMICOLON,
        COMMA,
        INT,
        FLOAT,
        NOTVALIDTOKEN,
        QAUT,
        DOUBLEQAUT,
        CHAR,
        STRING,
        DOT
    }

    private String token;
    private TokenType tokenType;

    public Token(String token, TokenType tokenType){
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getTokenText(){
        return token;
    }

    public TokenType getTokenType(){
        return tokenType;
    }
}
