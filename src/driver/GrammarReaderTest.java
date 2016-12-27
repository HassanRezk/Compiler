package driver;

import grammar.GrammarReader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Hassan on 12/27/2016.
 */
public class GrammarReaderTest {

    public static void main(final String[] args) throws IOException {
        GrammarReader grammarReader = GrammarReader.getInstance("Grammar v2.txt");
        PrintWriter pr = new PrintWriter(new File("Grammar Test output.txt"));
        pr.println(grammarReader.toString());
        pr.flush();
    }
}
