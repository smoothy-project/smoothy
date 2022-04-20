package solutions.thex.smoothy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import solutions.thex.smoothy.code.formatting.SimpleIndentStrategy;

@Configuration
public class SimpleIndentStrategyConfig {

    @Bean
    public SimpleIndentStrategy simpleIndentStrategy() {
        return new SimpleIndentStrategy("    ");
    }

}
