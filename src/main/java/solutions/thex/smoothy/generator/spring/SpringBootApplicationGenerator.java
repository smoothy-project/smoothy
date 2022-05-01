package solutions.thex.smoothy.generator.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.java.source.JavaCompilationUnit;
import solutions.thex.smoothy.code.java.source.JavaSourceCode;
import solutions.thex.smoothy.generator.ApplicationDescription;
import solutions.thex.smoothy.generator.spring.conf.SmoothyDotConfFileGenerator;
import solutions.thex.smoothy.generator.spring.docker.DockerComposeGenerator;
import solutions.thex.smoothy.generator.spring.docker.DockerfileGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.MainClassGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.control.util.ApiErrorGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.security.SecurityConfigGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.security.UnauthorizedEntryPointGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.security.filter.ExceptionHandlerFilterGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.security.filter.JwtAuthenticationFilterGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.security.jwt.JwtGenerator;
import solutions.thex.smoothy.generator.spring.main.src.resources.ApplicationPropertiesFileGenerator;
import solutions.thex.smoothy.generator.spring.main.test.MainClassTestsGenerator;
import solutions.thex.smoothy.generator.spring.pom.PomFileGenerator;
import solutions.thex.smoothy.soy.ISoyConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpringBootApplicationGenerator {

    public final String PORT = "8080";
    private final JavaSourceCodeWriter sourceWriter;

    @Autowired
    public SpringBootApplicationGenerator(JavaSourceCodeWriter sourceWriter) {
        this.sourceWriter = sourceWriter;
    }

    public void generate(ApplicationDescription application, OutputStream out) throws IOException {
        sourceWriter.writeSourceTo(//
                JavaSourceCode.builder()//
                        .compilationUnits(generateCompilationUnits(application))//
                        .testCompilationUnits(generateTestCompilationUnits(application))//
                        .staticCompilationUnits(generateStaticUnits(application))//
                        .build(), out);
    }

    private List<JavaCompilationUnit> generateCompilationUnits(ApplicationDescription application) {
        List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(MainClassGenerator.generate(application.getName()));
        compilationUnits.add(SecurityConfigGenerator.generate(application.getName()));
        compilationUnits.add(UnauthorizedEntryPointGenerator.generate(application.getName()));
        compilationUnits.add(JwtGenerator.generate(application.getName()));
        compilationUnits.add(JwtAuthenticationFilterGenerator.generate(application.getName()));
        compilationUnits.add(ExceptionHandlerFilterGenerator.generate(application.getName()));
        compilationUnits.add(ApiErrorGenerator.generate(application.getName()));
        return compilationUnits;
    }

    private List<JavaCompilationUnit> generateTestCompilationUnits(ApplicationDescription application) {
        return List.of(//
                MainClassTestsGenerator.generate(application.getName()));
    }

    private List<ISoyConfiguration> generateStaticUnits(ApplicationDescription application) throws IOException {
        return List.of(//
                generateApplicationPropertiesFile(application),//
                generatePomFile(application),//
                generateSmoothyDotConf(application),//
                generateDockerfile(application),//
                generateDockerCompose(application));
    }

    private ISoyConfiguration generateApplicationPropertiesFile(ApplicationDescription application) {
        return ApplicationPropertiesFileGenerator.builder()//
                .name(application.getName())//
//                .port(((SpringBootApplication) application.getApplication()).getPort())//
                .port(PORT)//
                .build();
    }

    private ISoyConfiguration generatePomFile(ApplicationDescription application) {
        return PomFileGenerator.builder()//
                .javaVersion(((SpringBootApplication) application.getApplication()).getJavaVersion())//
                .springVersion(((SpringBootApplication) application.getApplication()).getSpringVersion())//
                .name(application.getName())//
                .description(application.getDescription())//
                .build();
    }

    private ISoyConfiguration generateSmoothyDotConf(ApplicationDescription application) throws IOException {
        return SmoothyDotConfFileGenerator.builder()//
                .yml(new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)).writeValueAsString(application))//
                .build();
    }

    private ISoyConfiguration generateDockerfile(ApplicationDescription application) {
        return DockerfileGenerator.builder()//
                .javaVersion(((SpringBootApplication) application.getApplication()).getJavaVersion())//
                .build();
    }

    private ISoyConfiguration generateDockerCompose(ApplicationDescription application) {
        return DockerComposeGenerator.builder()//
                .name(application.getName())//
                .port(((SpringBootApplication) application.getApplication()).getPort())//
                .build();
    }

}
