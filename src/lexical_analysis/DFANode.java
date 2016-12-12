package lexical_analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Hassan on 12/12/2016.
 */
public final class DFANode {

    private final Character value;
    private final HashMap<Character, DFANode> children;
    private final Set<Character> allowedCharacters;
    private TokenType tokenType;

    public DFANode(final Set<Character> allowedCharacters) {
        children = new HashMap<Character, DFANode>();
        this.allowedCharacters = allowedCharacters;
        value = null;
        tokenType = null;
    }

    public DFANode(final char value, final TokenType tokenType, final Set<Character> acceptedCharacters) {
        this.value = value;
        this.tokenType = tokenType;
        this.allowedCharacters = acceptedCharacters;
        children = new HashMap<Character, DFANode>();
    }

    public char getValue() {
        return value;
    }

    public boolean isChild(final Character c) {
        return children.containsKey(c);
    }

    public DFANode getChild(final Character c) {
        if(!isChild(c)) {
            throw new IllegalArgumentException(c + " is not one of the children.");
        } else {
            return children.get(c);
        }
    }

    public void setTokenType(final TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public void addChild(final Character key, final DFANode value) {
        if(isChild(key)) {
            throw new IllegalArgumentException(key + " already exists.");
        }
        children.put(key, value);
    }

    public Set<Character> getAllowedCharacters() {
        return allowedCharacters;
    }

    public void emptyAllowedCharacters() {
        allowedCharacters.clear();
    }

    public void addCharacterToAllowedCharacters(final Character c) {
        allowedCharacters.add(c);
    }

    public boolean isAllowedCharacter(final Character c) {
        return allowedCharacters.contains(c);
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
