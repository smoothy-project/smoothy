package solutions.thex.smoothy.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solutions.thex.smoothy.generator.spring.SpringBootApplication;
import solutions.thex.smoothy.generator.spring.SpringBootApplicationGenerator;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class ApplicationGenerator {

    private final SpringBootApplicationGenerator springGenerator;

    @Autowired
    public ApplicationGenerator(SpringBootApplicationGenerator springGenerator) {
        this.springGenerator = springGenerator;
    }

    public void generate(ApplicationDescription application, OutputStream out) throws IOException {
        if (application.getApplication() instanceof SpringBootApplication)
            springGenerator.generate(application, out);

    }

}
