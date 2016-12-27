package grammar;

/**
 * Created by Hassan on 12/25/2016.
 */
public class GrammarNode {

    private final String value;
    private final boolean isTerminal;

    public GrammarNode(final String value, final boolean isTerminal) {
        this.value = value;
        this.isTerminal = isTerminal;
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

}
