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
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.generator.SpringBootApplicationGenerator;
import solutions.thex.smoothy.zip.Zipper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        SpringBootApplicationGenerator.builder()//
                .application(new JSONObject(payload))//
                .sourceWriter(sourceWriter)//
                .generationRootDirectory(generationRootDirectory)//
                .build()//
                .generate();

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

}
