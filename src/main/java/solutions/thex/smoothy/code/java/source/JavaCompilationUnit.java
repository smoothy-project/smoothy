package solutions.thex.smoothy.code.java.source;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.code.CompilationUnit;
import solutions.thex.smoothy.code.formatting.IndentingWriter;
import solutions.thex.smoothy.code.formatting.SimpleIndentStrategy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Java-compilation unit that represents an individual source file.
 */
@Builder
@Getter
public final class JavaCompilationUnit implements CompilationUnit {

    @Default
    private final List<JavaTypeDeclaration> typeDeclarations = new ArrayList<>();
    private final String packageName;
    private final String name;

    @Override
    public String render() {
        IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy("    "));
        writer.println("package " + this.packageName + ";");
        writer.println();
        writeImports(writer);
        getTypeDeclarations().forEach(typeDeclaration -> writer.println(typeDeclaration.render()));
        return writer.render();
    }

    public void writeImports(IndentingWriter writer) {
        Set<String> imports = this.typeDeclarations.stream().map(JavaTypeDeclaration::imports).flatMap(Collection::stream).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        if (!imports.isEmpty()) {
            for (String importedType : imports) {
                writer.println("import " + importedType + ";");
            }
            writer.println();
        }
    }

    public List<JavaTypeDeclaration> getTypeDeclarations() {
        return Collections.unmodifiableList(this.typeDeclarations);
    }

}
