package solutions.thex.smoothy.core.declaration.java.declaration;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.core.declaration.Declaration;
import solutions.thex.smoothy.core.declaration.formatting.IndentingWriter;
import solutions.thex.smoothy.core.declaration.formatting.SimpleIndentStrategy;
import solutions.thex.smoothy.core.declaration.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.core.declaration.java.util.JavaAnnotation;
import solutions.thex.smoothy.core.declaration.java.util.JavaModifier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Declaration of a field written in Java.
 */
@Builder
@Getter
public final class JavaFieldDeclaration implements Declaration {

    @Default
    private final List<JavaAnnotation> annotations = new ArrayList<>();
    private final JavaModifier modifiers;
    private final String name;
    private final String type;
    private final Object value;
    @Default
    List<String> genericTypes = new ArrayList<>();

    @Override
    public String render() {
        IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy("    "));
        writer.print(renderAnnotations());
        writer.print(this.modifiers.render());
        writer.print(JavaSourceCodeWriter.getUnqualifiedName(this.type));
        if (!this.genericTypes.isEmpty()) {
            writer.print(renderGenericType());
        }
        writer.print(" " + this.name);
        if (this.value != null) {
            writer.print(initValue());
        }
        writer.println(";");
        return writer.render();
    }

    private String renderGenericType() {
        StringBuilder str = new StringBuilder();
        str.append("<").append(this.genericTypes.stream()//
                        .map(JavaSourceCodeWriter::getUnqualifiedName)//
                        .collect(Collectors.joining(", ")))//
                .append("> ");
        return str.toString();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.type)) imports.add(this.type);
        if (this.value instanceof Class && JavaSourceCodeWriter.requiresImport(((Class<?>) this.value).getName()))
            imports.add(((Class<?>) this.value).getName());
        imports.addAll(this.annotations.stream().map(JavaAnnotation::imports).flatMap(Collection::stream).collect(Collectors.toList()));
        return new LinkedHashSet<>(imports);
    }

    private String initValue() {
        if (this.value instanceof Class)
            return " = new " + ((Class<?>) this.value).getSimpleName() + "()";
        else {
            String value = " = " + this.value;
            if (this.value instanceof Long)
                value += "L";
            else if (this.value instanceof Float)
                value += "F";
            else if (this.value instanceof Double)
                value += "D";

            return value;
        }
    }

    private String renderAnnotations() {
        return this.annotations.stream().map(JavaAnnotation::render).collect(Collectors.joining());
    }

}
