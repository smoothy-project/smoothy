package solutions.thex.smoothy.generate;

import java.io.OutputStream;

public interface Application {

    String getName();

    void generate(OutputStream out);

}
