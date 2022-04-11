package solutions.thex.smoothy.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import solutions.thex.smoothy.code.*;
import solutions.thex.smoothy.code.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;
import solutions.thex.smoothy.code.expression.JavaMethodInvocation;
import solutions.thex.smoothy.code.statement.JavaExpressionStatement;
import solutions.thex.smoothy.generator.pom.PomFileGenerator;
import solutions.thex.smoothy.generator.properties.ApplicationPropertiesFileGenerator;
import solutions.thex.smoothy.zip.Zipper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/generate")
public class GeneratorController {

    private final Path generationRootDirectory;
    private final JavaSourceCodeWriter sourceWriter;

    @Autowired
    public GeneratorController(JavaSourceCodeWriter sourceWriter) throws URISyntaxException {
        this.sourceWriter = sourceWriter;
        this.generationRootDirectory = Paths.get("factory");
    }

    @PostMapping(value = "", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> generate(HttpServletRequest request, @RequestBody String payload) throws IOException {
        javaSourceCodeContribute(new JSONObject(payload));
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=\"smoothy.zip\"")
                .body(out -> {
                    Zipper.builder()//
                            .source(generationRootDirectory.resolve("soroush"))//
                            .build()//
                            .zip(out);
                });
    }

    private void javaSourceCodeContribute(JSONObject application) throws IOException {
        generateApplication(application,//
                JavaSourceCode.builder()//
                        .compilationUnits(List.of(mainApplication(application.getString("name"))))//
                        .build());
    }

    private void generateApplication(JSONObject application, JavaSourceCode sourceCode) throws IOException {
        this.sourceWriter.writeTo(//
                new SourceStructure(//
                        generationRootDirectory.resolve(application.getString("name")).resolve("src/main/")),
                sourceCode);
        generatePomFile(application);
        generateApplicationPropertiesFile(application);
    }

    private void generateApplicationPropertiesFile(JSONObject application) throws IOException {
        ApplicationPropertiesFileGenerator.builder()//
                .name(application.getString("name"))//
                .port(application.getString("port"))//
                .rootDirectory(generationRootDirectory.resolve(application.getString("name")))//
                .build().generate();
    }

    private void generatePomFile(JSONObject application) throws IOException {
        PomFileGenerator.builder()//
                .javaVersion(application.has("javaVersion") ? application.getString("javaVersion") : "18")//
                .springVersion(application.has("springVersion") ? application.getString("springVersion") : "2.6.6")//
                .name(application.getString("name"))//
                .description(application.getString("description"))//
                .rootDirectory(generationRootDirectory.resolve(application.getString("name")))//
                .build().generate();
    }

    private JavaCompilationUnit mainApplication(String name) {
        return JavaCompilationUnit.builder()//
                .packageName("website.smoothy.".concat(name))//
                .name("Application")//
                .typeDeclarations(List.of(JavaTypeDeclaration.builder()//
                        .type(JavaType.CLASS)//
                        .name("Application")//
                        .modifiers(Modifier.PUBLIC)//
                        .annotations(List.of(Annotation.builder()//
                                .name("org.springframework.boot.autoconfigure.SpringBootApplication")//
                                .build()))//
                        .methodDeclarations(List.of(JavaMethodDeclaration.builder()//
                                .name("main")//
                                .modifiers(Modifier.PUBLIC | Modifier.STATIC)//
                                .returnType("void")//
                                .parameters(List.of(Parameter.builder()//
                                        .type("java.lang.String[]")//
                                        .name("args")//
                                        .build()))//
                                .statements(List.of(JavaExpressionStatement.builder()
                                        .expression(JavaMethodInvocation.builder()//
                                                .target("org.springframework.boot.SpringApplication")//
                                                .name("run")//
                                                .arguments(List.of("Application.class", "args"))//
                                                .build())//
                                        .build())//
                                ).build()))//
                        .build()))//
                .build();
    }

}
