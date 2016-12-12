package lexical_analysis;

import java.util.*;

/**
 * Created by Hassan on 12/12/2016.
 */
public final class DFA {

    private final DFANode dfaRoot;

    private static final Character[] allowedCharacters = {
            '~', '!', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '[', ']', '{', '}', ';', '\'', '"', '?', ':',
            '|', '<', '>', '.', '/', 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H',
            'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r', 'R', 's',
            'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9'
    };

    private static final DFA DFAInstance = new DFA();

    private final List<TokenError> tokenErrors;

    private DFA() {
        dfaRoot = new DFANode(new HashSet<Character>(Arrays.asList(allowedCharacters)));
        for(TokenType tokenType : TokenType.values()) {
            List<String> tokenValues = tokenType.getValues();
            for(String tokenValue : tokenValues) {
                add(tokenValue, tokenType);
            }
        }
        tokenErrors = new ArrayList<>();
    }

    public static DFA getInstance() {
        return DFAInstance;
    }

    public List<TokenError> getTokenErrors() {
        return tokenErrors;
    }

    public List<Token> getTokens(final String line) {
        tokenErrors.clear();
        List<Token> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        DFANode dfaRoot = this.dfaRoot;
        for(int i = 0 ; i < line.length() ; ++i) {
            char c = line.charAt(i);
            if(isWhiteSpace(c)) {
                continue;
            }
            if(dfaRoot == this.dfaRoot && !dfaRoot.isAllowedCharacter(c)) {
                tokenErrors.add(new TokenError(i + 1, c));
            }
            if(dfaRoot.isChild(c)) {
                token.append(c);
                dfaRoot = dfaRoot.getChild(c);
            } else {
                if(dfaRoot.isAllowedCharacter(c)) {
                    token.append(c);
                } else {
                    tokens.add(new Token(dfaRoot.getTokenType(), token.toString()));
                    token = new StringBuilder();
                    dfaRoot = this.dfaRoot;
                    --i;
                }
            }
        }
        if(dfaRoot != this.dfaRoot) {
            tokens.add(new Token(dfaRoot.getTokenType(), token.toString()));
        }
        return tokens;
    }

    private boolean isWhiteSpace(final char c) {
        return c == ' ' || c == '\n' || c == '\t' || c == '\0';
    }

    private void add(final String value, final TokenType tokenType) {
        DFANode dfaRoot = this.dfaRoot;
        for(int i = 0 ; i < value.length() ; ++i) {
            char c = value.charAt(i);
            if(!dfaRoot.isChild(c)) {
                Set<Character> allowedCharacters;
                if(tokenType == TokenType.IDENTIFIER || tokenType == TokenType.NUMBER) {
                    allowedCharacters =
                            getAllowedCharactersForIdentifierOrNumber(dfaRoot.getAllowedCharacters(), tokenType);
                } else {
                    if(i + 1 < value.length()) {
                        char nextC = value.charAt(i+1);
                        allowedCharacters = new HashSet<>();
                        allowedCharacters.add(nextC);
                    } else {
                        allowedCharacters = new HashSet<>();
                    }
                }
                DFANode dfaNode = new DFANode(c, null, allowedCharacters);
                dfaRoot.addChild(c, dfaNode);
            }
            dfaRoot = dfaRoot.getChild(c);
            if(i == value.length() - 1) {
                dfaRoot.setTokenType(tokenType);
                if(tokenType != TokenType.IDENTIFIER && tokenType != TokenType.NUMBER) {
                    dfaRoot.emptyAllowedCharacters();
                }
            }
        }
    }

    private Set<Character> getAllowedCharactersForIdentifierOrNumber(final Set<Character> parentAllowedCharacters,
                                                                      final TokenType tokenType) {
        Set<Character> allowedCharacters = new HashSet<>();
        switch (tokenType) {
            case IDENTIFIER:
                for(Character c : parentAllowedCharacters) {
                    if(Character.isDigit(c) || Character.isAlphabetic(c)) {
                        allowedCharacters.add(c);
                    }
                }
                break;
            case NUMBER:
                for(Character c : parentAllowedCharacters) {
                    if(Character.isDigit(c)) {
                        allowedCharacters.add(c);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException(tokenType.toString()
                        + " is not supported by this method.");
        }
        return allowedCharacters;
    }

}
