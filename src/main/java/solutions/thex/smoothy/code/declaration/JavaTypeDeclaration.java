package solutions.thex.smoothy.code.declaration;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import solutions.thex.smoothy.code.Annotatable;
import solutions.thex.smoothy.code.Annotation;
import solutions.thex.smoothy.code.JavaType;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A declaration of a type written in Java.
 */
@Builder
@Data
public class JavaTypeDeclaration implements Annotatable {

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

}
