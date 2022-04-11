package solutions.thex.smoothy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import solutions.thex.smoothy.code.java.formatting.IndentingWriterFactory;
import solutions.thex.smoothy.code.java.formatting.SimpleIndentStrategy;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;

@Configuration
public class JavaSourceCodeWriterConfig {

    @Autowired
    SimpleIndentStrategy indentStrategy;

    @Bean
    public JavaSourceCodeWriter createJavaSourceCodeWriter() {
        return new JavaSourceCodeWriter(//
                IndentingWriterFactory.builder().//
                        defaultIndentingStrategy(indentStrategy)//
                        .build());
    }

}
