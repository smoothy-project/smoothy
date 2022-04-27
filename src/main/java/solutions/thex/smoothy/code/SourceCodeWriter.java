package solutions.thex.smoothy.code;

import java.io.IOException;
import java.io.OutputStream;

public interface SourceCodeWriter {

    void writeSourceTo(SourceCode sourceCode, OutputStream output) throws IOException;

}
