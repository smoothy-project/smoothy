package solutions.thex.smoothy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;

@Configuration
public class JavaSourceCodeWriterConfig {

    @Bean
    public JavaSourceCodeWriter createJavaSourceCodeWriter() {
        return new JavaSourceCodeWriter();
    }

}
