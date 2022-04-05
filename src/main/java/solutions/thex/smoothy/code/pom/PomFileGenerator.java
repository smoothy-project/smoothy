package solutions.thex.smoothy.code.pom;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import solutions.thex.smoothy.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public class PomFileGenerator implements ISoyConfiguration {

    private String javaVersion;
    private String springVersion;
    private String name;
    private String description;
    private Path rootDirectory;

    @Override
    public String getName() {
        return "website.smoothy.generator.pom";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("pom.xml", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(//
                Objects.requireNonNull(//
                        getClass().getClassLoader().getResourceAsStream("templates/pom.xml.soy")), tempFile);

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(//
                "java", javaVersion,//
                "spring", springVersion,//
                "name", name,//
                "description", description);
    }

    @Override
    public Path getPath() throws IOException {
        return rootDirectory.resolve("pom.xml");
    }

}
