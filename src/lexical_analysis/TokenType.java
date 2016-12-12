package lexical_analysis;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hassan on 12/10/2016.
 */
public enum TokenType {
    KEYWORD(Arrays.asList("int", "double", "boolean", "char", "string", "if", "else", "start", "end", "for", "while",
            "do", "switch", "case", "true", "false")),
    IDENTIFIER(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "_")),
    NUMBER(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")),
    ASSIGNMENT_OPERATOR(Arrays.asList("=", "-=", "+=", "*=", "/=", "%=", "~=", "&=", "|=", "<<=", ">>=")),
    ARITHMETIC_OPERATOR(Arrays.asList("+", "-", "*", "/", "%", "&", "|", "^", "<<", ">>")),
    INCREMENT_DECREMENT(Arrays.asList("++", "--")), //TODO ++, -- ?
    BITWISE_OPERATOR(Arrays.asList("&", "|", "^")),
    BITWISE_NOT(Arrays.asList("~")), // TODO Does it belong to BITWISE_OPERATOR ?
    LOGICAL_OPERATOR(Arrays.asList("&&", "||")),
    LOGICAL_NOT(Arrays.asList("!")), // TODO Does it belong to LOGICAL_OPERATOR?
    COMPARISON_OPERATOR(Arrays.asList(">", "<", "<=", ">=", "==", "!=")),
    SINGLE_QUOTE(Collections.singletonList("'")),
    DOUBLE_QUOTE(Collections.singletonList("\"")),
    POINT(Collections.singletonList(".")),
    COMMA(Collections.singletonList(",")),
    COLON(Collections.singletonList(":")),
    QUESTION_MARK(Collections.singletonList("?")),
    SEMICOLON(Collections.singletonList(";")),
    LEFT_BRACKET(Collections.singletonList("(")),
    RIGHT_BRACKET(Collections.singletonList(")")),
    LEFT_CURLY_BRACKET(Collections.singletonList("{")),
    RIGHT_CURLY_BRACKET(Collections.singletonList("}")),
    LEFT_SQUARE_BRACKET(Collections.singletonList("[")),
    RIGHT_SQUARE_BRACKET(Collections.singletonList("]"));

    private List<String> values;

    TokenType(final List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }
}
