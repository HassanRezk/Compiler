package lexical_analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hassan on 12/10/2016.
 */
public enum TokenType {
    KEYWORD(Arrays.asList("start", "main", "end", "void", "double", "int", "boolean",
                          "return", "continue", "break", "while", "if", "endwhile", "endif")),
    IDENTIFIER(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "_")),
    NUMBER(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")),
    ASSIGNMENT_OPERATOR(Arrays.asList("=", "-=", "+=")),
    ARITHMETIC_OPERATOR(Arrays.asList("+", "-", "*", "/", "//", "%")),
    BITWISE_OPERATOR(Arrays.asList("&", "|", "^")),
    LOGICAL_OPERATOR(Arrays.asList("&&", "||")),
    COMPARISON_OPERATOR(Arrays.asList(">", "<", "<=", ">=", "==", "!=")),
    POINT(Collections.singletonList(".")),
    SEMICOLON(Collections.singletonList(";")),
    LEFT_BRACKET(Collections.singletonList("(")),
    RIGHT_BRACKET(Collections.singletonList(")")),
    LEFT_CURLY_BRACKET(Collections.singletonList("{")),
    RIGHT_CURLY_BRACKET(Collections.singletonList("}")),
    BOOLEAN(Arrays.asList("true", "false"));

    private List<String> values;

    TokenType(final List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }
}
