package solutions.thex.smoothy.code;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Java-compilation unit that represents an individual source file.
 */
@Builder
@Getter
public class JavaCompilationUnit {

    @Default
    private final List<JavaTypeDeclaration> typeDeclarations = new ArrayList<>();
    private final String packageName;
    private final String name;

    public List<JavaTypeDeclaration> getTypeDeclarations() {
        return Collections.unmodifiableList(this.typeDeclarations);
    }

}
