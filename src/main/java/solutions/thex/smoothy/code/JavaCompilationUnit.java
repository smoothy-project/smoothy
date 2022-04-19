package solutions.thex.smoothy.code;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A Java-compilation unit that represents an individual source file.
 */
@Builder
@Getter
public class JavaCompilationUnit implements CompilationUnit{

    @Default
    private final List<JavaTypeDeclaration> typeDeclarations = new ArrayList<>();
    private final String packageName;
    private final String name;

    public List<JavaTypeDeclaration> getTypeDeclarations() {
        return Collections.unmodifiableList(this.typeDeclarations);
    }

    @Override
    public void render(IndentingWriter writer) {
        writer.println("package " + getPackageName() + ";");
        writer.println();
        Set<String> imports = JavaSourceCodeWriter.determineImports(this);
        if (!imports.isEmpty()) {
            for (String importedType : imports) {
                writer.println("import " + importedType + ";");
            }
            writer.println();
        }
        for (JavaTypeDeclaration type : getTypeDeclarations()) {
            type.render(writer);
        }
    }

}
