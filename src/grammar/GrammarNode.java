package grammar;

import lexical_analysis.TokenType;

/**
 * Created by Hassan on 12/25/2016.
 */
public class GrammarNode {

    private final String value;
    private final boolean isTerminal;
    private TokenType tokenType;

    public GrammarNode(final String value, final boolean isTerminal) {
        this.value = value;
        this.isTerminal = isTerminal;
        tokenType = null;
    }

    public String getValue() {
        return value;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    @Override
    public String toString() {
        return (isTerminal ? "'" : "") +  value + (isTerminal ? "'" : "");
    }

    public void setTokenType(final TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
