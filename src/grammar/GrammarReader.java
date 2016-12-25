package grammar;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;

/**
 * Created by Hassan on 12/25/2016.
 */
public class GrammarReader {

    private static final GrammarReader instance = new GrammarReader();

    private  GrammarReader() {}

    public GrammarReader getInstance(final String filePath) {
        return instance;
    }

    public Map<String, List<List<GrammarNode>>> getRules() {
        throw new NotImplementedException();
    }

}
