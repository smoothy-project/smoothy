package solutions.thex.smoothy.generator;

import lombok.Builder;
import org.json.JSONObject;
import solutions.thex.smoothy.code.JavaCompilationUnit;
import solutions.thex.smoothy.code.JavaSourceCode;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.SourceStructure;
import solutions.thex.smoothy.generator.java.MainClassGenerator;
import solutions.thex.smoothy.generator.pom.PomFileGenerator;
import solutions.thex.smoothy.generator.properties.ApplicationPropertiesFileGenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Builder
public class SpringBootApplicationGenerator {

    private final List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
    private Path generationRootDirectory;
    private JavaSourceCodeWriter sourceWriter;
    private JSONObject application;

    public boolean generate() throws IOException {
        generateCompilationUnits();
        generateApplicationPropertiesFile();
        generatePomFile();

        writeSourceCode();
        return true;
    }

    private void writeSourceCode() throws IOException {
        sourceWriter.writeTo(//
                new SourceStructure(//
                        generationRootDirectory.resolve(application.getString("name")).resolve("src/main/")),
                JavaSourceCode.builder()//
                        .compilationUnits(compilationUnits)//
                        .build());
    }

    private void generateCompilationUnits() {
        compilationUnits.add(MainClassGenerator.generate(application.getString("name")));
    }

    private void generateApplicationPropertiesFile() throws IOException {
        ApplicationPropertiesFileGenerator.builder()//
                .name(application.getString("name"))//
                .port(application.getString("port"))//
                .rootDirectory(generationRootDirectory.resolve(application.getString("name")))//
                .build().generate();
    }

    private void generatePomFile() throws IOException {
        PomFileGenerator.builder()//
                .javaVersion(application.has("javaVersion") ? application.getString("javaVersion") : "18")//
                .springVersion(application.has("springVersion") ? application.getString("springVersion") : "2.6.6")//
                .name(application.getString("name"))//
                .description(application.getString("description"))//
                .rootDirectory(generationRootDirectory.resolve(application.getString("name")))//
                .build().generate();
    }

}
