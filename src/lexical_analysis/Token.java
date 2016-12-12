package lexical_analysis;

import java.util.regex.Pattern;

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

    @Override
    public String toString() {
        return "Token Type: " + this.tokenType + " Token Value: " + this.tokenValue;
    }

}
