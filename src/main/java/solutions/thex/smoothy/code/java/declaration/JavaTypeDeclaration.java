package solutions.thex.smoothy.code.java.declaration;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import solutions.thex.smoothy.code.java.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A declaration of a type written in Java.
 */
@Builder
@Data
public class JavaTypeDeclaration implements Annotatable {

    @Default
    private List<Annotation> annotations = new ArrayList<>();

    private final JavaType type;

    private final String name;

    private String extendedClassName;

    private int modifiers;

    @Default
    private final List<JavaFieldDeclaration> fieldDeclarations = new ArrayList<>();
    @Default
    private final List<JavaMethodDeclaration> methodDeclarations = new ArrayList<>();

    public void addFieldDeclaration(JavaFieldDeclaration fieldDeclaration) {
        this.fieldDeclarations.add(fieldDeclaration);
    }

    public void addMethodDeclaration(JavaMethodDeclaration methodDeclaration) {
        this.methodDeclarations.add(methodDeclaration);
    }

    @Override
    public void annotate(Annotation annotation) {
        this.annotations.add(annotation);
    }

}
