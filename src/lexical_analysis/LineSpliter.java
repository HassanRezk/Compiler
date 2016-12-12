package lexical_analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 12/12/2016.
 */
public class LineSpliter {

    private String line;
    private List<String> tokens = new ArrayList<String>();
    private final Pattern identifiers = Pattern.compile("[a-zA-Z0-9_]+");
    private final Pattern operators = Pattern.compile("[\\*|/|\\+|\\-|%|>|<|=|~|!|&|\\|]+");
    private final Pattern brackets = Pattern.compile("[\\{|\\}|\\)|\\(|\\[|\\]]?");
    private final Pattern semicolon = Pattern.compile(";");
    private final Pattern comma = Pattern.compile("\\,");

    public LineSpliter(final String line) {
        this.line = line;
    }

    public List<String> getLineComponents() {
        while (line.length() != 0){
            boolean ok =filltokens(operators) || filltokens(identifiers) || filltokens(semicolon) ||
                    filltokens(comma) || filltokens(brackets);
        }
        return tokens;
    }

    private boolean filltokens(Pattern currentPatternUsage) {
        Matcher m = currentPatternUsage.matcher(line);
        if(m.lookingAt()){
            tokens.add(m.group());
            line = (line.substring(m.group().length(), line.length())).trim();
            return true;
        }
        return false;
    }

}
