package solutions.thex.smoothy.code.java.declaration;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.code.Declaration;
import solutions.thex.smoothy.code.formatting.IndentingWriter;
import solutions.thex.smoothy.code.formatting.SimpleIndentStrategy;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.java.util.JavaAnnotation;
import solutions.thex.smoothy.code.java.util.JavaModifier;

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
    private final boolean initialized;

    @Override
    public String render() {
        IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy("    "));
        writer.print(renderAnnotations());
        writer.print(this.modifiers.render());
        writer.print(JavaSourceCodeWriter.getUnqualifiedName(this.type) + " " + this.name);
        if (isInitialized()) {
            writer.print(initValue());
        }
        writer.println(";");
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.type)) imports.add(this.type);
        imports.addAll(this.annotations.stream().map(JavaAnnotation::imports).flatMap(Collection::stream).collect(Collectors.toList()));
        return new LinkedHashSet<>(imports);
    }

    private String initValue() {
        String value = " = " + this.value;
        if (getValue() instanceof Long)
            value += "L";
        else if (getValue() instanceof Float)
            value += "F";
        else if (getValue() instanceof Double)
            value += "D";
        return value;
    }

    private String renderAnnotations() {
        return this.annotations.stream().map(JavaAnnotation::render).collect(Collectors.joining());
    }

}
