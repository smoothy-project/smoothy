package solutions.thex.smoothy.generator;

import java.io.OutputStream;

public interface Application {

    String getName();

    void generate(OutputStream out);

}
