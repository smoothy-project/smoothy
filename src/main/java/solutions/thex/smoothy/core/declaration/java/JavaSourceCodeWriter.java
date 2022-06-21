package solutions.thex.smoothy.core.declaration.java;

import solutions.thex.smoothy.core.declaration.SourceCode;
import solutions.thex.smoothy.core.declaration.SourceCodeWriter;
import solutions.thex.smoothy.core.declaration.java.source.JavaCompilationUnit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A SourceCodeWriter that writes SourceCode in Java.
 */
public final class JavaSourceCodeWriter implements SourceCodeWriter {

    private final String sourceFileExtension;
    private final String sourcesDirectory;
    private final String testsDirectory;

    public JavaSourceCodeWriter() {
        this.sourceFileExtension = "java";
        this.sourcesDirectory = "src/main/java";
        this.testsDirectory = "src/test/java";
    }

    @Override
    public void writeSourceTo(SourceCode sourceCode, OutputStream outPut) {
        try (ZipOutputStream zipOutput = new ZipOutputStream(outPut)) {
            sourceCode.getCompilationUnits().stream().map(unit -> (JavaCompilationUnit) unit).forEach(unit -> {
                try {
                    zipOutput.putNextEntry(new ZipEntry(resolveFileName(unit, this.sourcesDirectory)));
                    write(zipOutput, unit.render());
                    zipOutput.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            sourceCode.getTestCompilationUnits().stream().map(unit -> (JavaCompilationUnit) unit).forEach(unit -> {
                try {
                    zipOutput.putNextEntry(new ZipEntry(resolveFileName(unit, this.testsDirectory)));
                    write(zipOutput, unit.render());
                    zipOutput.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            sourceCode.getStaticCompilationUnits().forEach(unit -> {
                try {
                    zipOutput.putNextEntry(new ZipEntry(unit.getPath().toString()));
                    write(zipOutput, unit.render());
                    zipOutput.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(ZipOutputStream outPut, String content) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes());
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bais.read(buffer)) > 0) {
            outPut.write(buffer, 0, len);
        }
    }

    private String resolveFileName(JavaCompilationUnit compilationUnit, String source) {
        String file = compilationUnit.getName() + "." + this.sourceFileExtension;
        return resolve(resolvePackage(source, compilationUnit.getPackageName()), file);
    }

    private String resolvePackage(String directory, String packageName) {
        return resolve(directory, packageName.replace('.', '/'));
    }

    private String resolve(String directory, String path) {
        return directory.concat("/").concat(path);
    }

    public static String getUnqualifiedName(String name) {
        if (!name.contains(".")) {
            return name;
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static boolean requiresImport(String name) {
        if (name == null || !name.contains(".")) {
            return false;
        }
        String packageName = name.substring(0, name.lastIndexOf('.'));
        return !"java.lang".equals(packageName);
    }

}
