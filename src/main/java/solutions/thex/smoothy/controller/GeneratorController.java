package solutions.thex.smoothy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import solutions.thex.smoothy.generator.ApplicationDescription;
import solutions.thex.smoothy.generator.ApplicationGenerator;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/generate")
public class GeneratorController {

    private final ApplicationGenerator generator;

    @Autowired
    public GeneratorController(ApplicationGenerator generator) {
        this.generator = generator;
    }

    @PostMapping(value = "", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> generate(HttpServletRequest request, @RequestBody String payload) {
        return ResponseEntity
                .ok()//
                .header("Content-Disposition", "attachment; filename=\"smoothy.zip\"")//
                .body(out -> generator.generate(buildApplicationDescription(payload), out));
    }

    private ApplicationDescription buildApplicationDescription(String payload) throws JsonProcessingException {
        return new ObjectMapper().readValue(payload, ApplicationDescription.class);
    }

}
