package lexical_analysis;

/**
 * Created by Hassan on 12/12/2016.
 */
public final class TokenError {

    private final int lineNumber;
    private final Character illegalCharacter;

    public TokenError(final int lineNumber, final Character illegalCharacter) {
        this.lineNumber = lineNumber;
        this.illegalCharacter = illegalCharacter;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public Character getIllegalCharacter() {
        return illegalCharacter;
    }
}
