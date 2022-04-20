package solutions.thex.smoothy.generator.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solutions.thex.smoothy.code.JavaCompilationUnit;
import solutions.thex.smoothy.code.JavaSourceCode;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.generator.ApplicationDescription;
import solutions.thex.smoothy.generator.spring.conf.SmoothyDotConfFileGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.MainClassGenerator;
import solutions.thex.smoothy.generator.spring.main.src.java.security.SecurityConfigGenerator;
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

    private final JavaSourceCodeWriter sourceWriter;

    @Autowired
    public SpringBootApplicationGenerator(JavaSourceCodeWriter sourceWriter) {
        this.sourceWriter = sourceWriter;
    }

    public void generate(ApplicationDescription application, OutputStream out) throws IOException {
        sourceWriter.writeTo(//
                JavaSourceCode.builder()//
                        .compilationUnits(generateCompilationUnits(application))//
                        .testCompilationUnits(generateTestCompilationUnits(application))//
                        .staticCompilationUnits(generateStaticUnits(application))//
                        .build(), out);
    }

    private List<JavaCompilationUnit> generateCompilationUnits(ApplicationDescription application) {
        List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(MainClassGenerator.generate(application.getName()));
        compilationUnits.addAll(SecurityConfigGenerator.generate(application.getName()));
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
                generateSmoothyDotConf(application));
    }

    private ISoyConfiguration generateApplicationPropertiesFile(ApplicationDescription application) throws IOException {
        return ApplicationPropertiesFileGenerator.builder()//
                .name(application.getName())//
                .port(((SpringBootApplication) application.getApplication()).getPort())//
                .build();
    }

    private ISoyConfiguration generatePomFile(ApplicationDescription application) throws IOException {
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

}
