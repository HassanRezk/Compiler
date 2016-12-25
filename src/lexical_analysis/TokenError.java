package lexical_analysis;

/**
 * Created by Hassan on 12/12/2016.
 */
public final class TokenError {

    private int lineNumber;
    private final Character illegalCharacter;

    public TokenError(final Character illegalCharacter) {
        this.illegalCharacter = illegalCharacter;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "Line Number# " + lineNumber + " Illegal Character = " + illegalCharacter;
    }
}
