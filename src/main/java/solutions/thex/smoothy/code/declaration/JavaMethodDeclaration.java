package solutions.thex.smoothy.code.declaration;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import solutions.thex.smoothy.code.*;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Declaration of a method written in Java.
 */
@Builder
@Data
public final class JavaMethodDeclaration implements Annotatable, JavaDeclaration {

    @Default
    private final List<Annotation> annotations = new ArrayList<>();
    @Default
    private final List<Parameter> parameters = new LinkedList<>();
    @Default
    private final List<JavaStatement> statements = new LinkedList<>();
    @Default
    private final List<String> exceptions = new LinkedList<>();
    @Default
    private final boolean isThrows = false;
    private final String name;
    private final String returnType;
    private final int modifiers;

    @Override
    public void annotate(Annotation annotation) {
        this.annotations.add(annotation);
    }

    @Override
    public List<Annotation> getAnnotations() {
        return Collections.unmodifiableList(this.annotations);
    }


    @Override
    public void render(IndentingWriter writer) {
        JavaSourceCodeWriter.writeAnnotations(writer, this);
        JavaSourceCodeWriter.writeModifiers(writer, JavaSourceCodeWriter.METHOD_MODIFIERS, getModifiers());
        writer.print(JavaSourceCodeWriter.getUnqualifiedName(getReturnType()) + (getReturnType().equals("") ? "" : " ") + getName() + "(");
        List<Parameter> parameters = getParameters();
        if (!parameters.isEmpty()) {
            writer.print(parameters.stream()
                    .map((parameter) -> JavaSourceCodeWriter.getUnqualifiedName(parameter.getType()) + " " + parameter.getName())
                    .collect(Collectors.joining(", ")));
        }
        if (isThrows()) {
            writer.print(") throws ");
            writer.print(String.join(", ", getExceptions()));
            writer.println(" {");
        } else {
            writer.println(") {");
        }
        writer.indented(() -> {
            List<JavaStatement> statements = getStatements();
            for (JavaStatement statement : statements) {
                statement.render(writer);
            }
        });
        writer.println("}");
        writer.println();
    }

}
