package solutions.thex.smoothy.generator.spring.conf;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import solutions.thex.smoothy.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public class SmoothyDotConfFileGenerator implements ISoyConfiguration {

    private String yml;

    @Override
    public String getName() {
        return "website.smoothy.generator.conf";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("smoothy.conf", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(//
                Objects.requireNonNull(//
                        getClass().getClassLoader().getResourceAsStream("templates/smoothy.conf.soy")), tempFile);

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(//
                "yml", yml);
    }

    @Override
    public Path getPath() throws IOException {
        return Path.of("smoothy.conf");
    }

}
