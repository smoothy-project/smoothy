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
import solutions.thex.smoothy.generator.SpringBootApplicationGenerator;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/generate")
public class GeneratorController {

    private final SpringBootApplicationGenerator generator;

    @Autowired
    public GeneratorController(SpringBootApplicationGenerator generator) {
        this.generator = generator;
    }

    @PostMapping(value = "", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> generate(HttpServletRequest request, @RequestBody String payload) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=\"smoothy.zip\"")
                .body(out -> {
                    generator.generate(new JSONObject(payload), out);
                });
    }

}
