package grammar;

import lexical_analysis.TokenType;

/**
 * Created by Hassan on 12/25/2016.
 */
public class GrammarNode {

    private final String value;
    private final boolean isTerminal;
    private final TokenType tokenType;

    public GrammarNode(final String value, final boolean isTerminal, final TokenType tokenType) {
        this.value = value;
        this.isTerminal = isTerminal;
        this.tokenType = tokenType;
    }

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

    public TokenType getTokenType() {
        return tokenType;
    }

}
