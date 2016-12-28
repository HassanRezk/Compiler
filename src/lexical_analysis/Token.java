package lexical_analysis;

/**
 * Created by Hassan on 12/10/2016.
 */
public final class Token {

    private final TokenType tokenType;
    private final String tokenValue;

    public Token(final TokenType tokenType, final String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getTokenValue() {return tokenValue;}

    @Override
    public String toString() {
        return "Token Type: " + this.tokenType + " Token Value: " + this.tokenValue;
    }

}
