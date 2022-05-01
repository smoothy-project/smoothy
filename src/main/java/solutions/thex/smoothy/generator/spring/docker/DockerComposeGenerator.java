package solutions.thex.smoothy.generator.spring.docker;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import solutions.thex.smoothy.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public class DockerComposeGenerator implements ISoyConfiguration {

    private String name;
    private String port;


    @Override
    public String getName() {
        return "website.smoothy.generator.dockerCompose";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("docker-compose.yml", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(//
                Objects.requireNonNull(//
                        getClass().getClassLoader().getResourceAsStream("templates/docker-compose.yml.soy")), tempFile);

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(//
                "name", this.name, //
                "port", this.port);
    }

    @Override
    public Path getPath() throws IOException {
        return Path.of("docker-compose.yml");
    }

}
