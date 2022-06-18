package solutions.thex.smoothy.generator;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class ApplicationGenerator {

    public void generate(ApplicationDescription application, OutputStream out) throws IOException {
        application.getApplication().generate(out);
    }

}
