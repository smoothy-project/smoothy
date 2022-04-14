package solutions.thex.smoothy.generator;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solutions.thex.smoothy.code.JavaCompilationUnit;
import solutions.thex.smoothy.code.JavaSourceCode;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.generator.main.src.java.MainClassGenerator;
import solutions.thex.smoothy.generator.main.src.java.SecurityConfigGenerator;
import solutions.thex.smoothy.generator.main.src.resources.properties.ApplicationPropertiesFileGenerator;
import solutions.thex.smoothy.generator.main.test.MainClassTestsGenerator;
import solutions.thex.smoothy.generator.pom.PomFileGenerator;
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

    public void generate(JSONObject application, OutputStream out) throws IOException {
        sourceWriter.writeTo(//
                JavaSourceCode.builder()//
                        .compilationUnits(generateCompilationUnits(application))//
                        .testCompilationUnits(generateTestCompilationUnits(application))//
                        .staticCompilationUnits(generateStaticUnits(application))//
                        .build(), out);
    }

    private List<JavaCompilationUnit> generateCompilationUnits(JSONObject application) {
        List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(MainClassGenerator.generate(application.getString("name")));
        compilationUnits.addAll(SecurityConfigGenerator.generate(application.getString("name")));
        return compilationUnits;
    }

    private List<JavaCompilationUnit> generateTestCompilationUnits(JSONObject application) {
        return List.of(//
                MainClassTestsGenerator.generate(application.getString("name")));
    }

    private List<ISoyConfiguration> generateStaticUnits(JSONObject application) throws IOException {
        return List.of(//
                generateApplicationPropertiesFile(application),//
                generatePomFile(application));
    }

    private ISoyConfiguration generateApplicationPropertiesFile(JSONObject application) throws IOException {
        return ApplicationPropertiesFileGenerator.builder()//
                .name(application.getString("name"))//
                .port(application.getString("port"))//
                .build();
    }

    private ISoyConfiguration generatePomFile(JSONObject application) throws IOException {
        return PomFileGenerator.builder()//
                .javaVersion(application.has("javaVersion") ? application.getString("javaVersion") : "18")//
                .springVersion(application.has("springVersion") ? application.getString("springVersion") : "2.6.6")//
                .name(application.getString("name"))//
                .description(application.getString("description"))//
                .build();
    }

}
