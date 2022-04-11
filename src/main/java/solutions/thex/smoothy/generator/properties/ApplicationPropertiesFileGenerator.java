package solutions.thex.smoothy.generator.properties;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import solutions.thex.smoothy.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public class ApplicationPropertiesFileGenerator implements ISoyConfiguration {

    private String name;
    private String port;
    private Path rootDirectory;

    @Override
    public String getName() {
        return "website.smoothy.generator.properties";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("application.properties", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(//
                Objects.requireNonNull(//
                        getClass().getClassLoader().getResourceAsStream("templates/application.properties.soy")), tempFile);

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(//
                "name", name,//
                "port", port);
    }

    @Override
    public Path getPath() throws IOException {
        return rootDirectory.resolve("src/main/resources/application.properties");
    }

}
