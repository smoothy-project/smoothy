package solutions.thex.smoothy.code.declaration;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import solutions.thex.smoothy.code.*;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * A declaration of a type written in Java.
 */
@Builder
@Data
public class JavaTypeDeclaration implements Annotatable, JavaDeclaration {

    @Default
    private List<Annotation> annotations = new ArrayList<>();
    @Default
    private final List<JavaFieldDeclaration> fieldDeclarations = new ArrayList<>();
    @Default
    private final List<JavaMethodDeclaration> methodDeclarations = new ArrayList<>();
    private final JavaType type;
    private final String name;
    private String extendedClassName;
    private int modifiers;

    @Override
    public void annotate(Annotation annotation) {
        this.annotations.add(annotation);
    }

    @Override
    public void render(IndentingWriter writer) {
        JavaSourceCodeWriter.writeAnnotations(writer, this);
        JavaSourceCodeWriter.writeModifiers(writer, JavaSourceCodeWriter.TYPE_MODIFIERS, getModifiers());
        writer.print(getType() + " " + getName());
        if (getExtendedClassName() != null) {
            writer.print(" extends " + JavaSourceCodeWriter.getUnqualifiedName(getExtendedClassName()));
        }
        writer.println(" {");
        writer.println();
        List<JavaFieldDeclaration> fieldDeclarations = getFieldDeclarations();
        if (!fieldDeclarations.isEmpty()) {
            writer.indented(() -> {
                for (JavaFieldDeclaration fieldDeclaration : fieldDeclarations) {
                    fieldDeclaration.render(writer);
                }
            });
        }
        List<JavaMethodDeclaration> methodDeclarations = getMethodDeclarations();
        if (!methodDeclarations.isEmpty()) {
            writer.indented(() -> {
                for (JavaMethodDeclaration methodDeclaration : methodDeclarations) {
                    methodDeclaration.render(writer);
                }
            });
        }
        writer.println("}");
    }

}
