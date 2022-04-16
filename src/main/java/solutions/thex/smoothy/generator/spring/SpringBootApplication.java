package solutions.thex.smoothy.generator.spring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solutions.thex.smoothy.generator.Application;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpringBootApplication implements Application {

    @Builder.Default
    private String javaVersion = "18";
    @Builder.Default
    private String springVersion = "2.6.6";
    @Builder.Default
    private String port = "8080";

}
