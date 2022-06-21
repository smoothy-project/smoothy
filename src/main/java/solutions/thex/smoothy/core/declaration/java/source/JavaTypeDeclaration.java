package solutions.thex.smoothy.core.declaration.java.source;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.core.declaration.TypeDeclaration;
import solutions.thex.smoothy.core.declaration.formatting.IndentingWriter;
import solutions.thex.smoothy.core.declaration.formatting.SimpleIndentStrategy;
import solutions.thex.smoothy.core.declaration.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.core.declaration.java.declaration.JavaFieldDeclaration;
import solutions.thex.smoothy.core.declaration.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.core.declaration.java.util.JavaAnnotation;
import solutions.thex.smoothy.core.declaration.java.util.JavaModifier;
import solutions.thex.smoothy.core.declaration.java.util.JavaType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A declaration of a type written in Java.
 */
@Builder
@Getter
public final class JavaTypeDeclaration implements TypeDeclaration {

    @Default
    private List<JavaAnnotation> annotations = new ArrayList<>();
    @Default
    private final List<JavaFieldDeclaration> fieldDeclarations = new ArrayList<>();
    @Default
    private final List<JavaMethodDeclaration> methodDeclarations = new ArrayList<>();
    private JavaModifier modifiers;
    private final JavaType type;
    private final String name;
    private String extendedClassName;
    private String implementedClassName;

    @Override
    public String render() {
        IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy("    "));
        writer.print(renderAnnotations());
        writer.print(this.modifiers.render());
        writer.print(this.type + " " + this.name);
        writer.print(implementedOrExtendedClassName());
        writer.println(" {");
        writer.println();
        writer.indented(() -> {
            this.fieldDeclarations.forEach(fieldDeclaration -> writer.println(fieldDeclaration.render()));
        });
        writer.indented(() -> {
            this.methodDeclarations.forEach(methodDeclaration -> writer.println(methodDeclaration.render()));
        });
        writer.print("}");
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        imports.addAll(this.annotations.stream().map(JavaAnnotation::imports).flatMap(Collection::stream).collect(Collectors.toList()));
        imports.addAll(this.fieldDeclarations.stream().map(JavaFieldDeclaration::imports).flatMap(Collection::stream).collect(Collectors.toList()));
        imports.addAll(this.methodDeclarations.stream().map(JavaMethodDeclaration::imports).flatMap(Collection::stream).collect(Collectors.toList()));
        if (this.extendedClassName != null) imports.add(this.extendedClassName);
        if (this.implementedClassName != null) imports.add(this.implementedClassName);
        return new LinkedHashSet<>(imports);
    }

    private String implementedOrExtendedClassName() {
        String className = "";
        if (getExtendedClassName() != null)
            className += " extends " + JavaSourceCodeWriter.getUnqualifiedName(this.extendedClassName);
        else if (getImplementedClassName() != null)
            className += " implements " + JavaSourceCodeWriter.getUnqualifiedName(this.implementedClassName);
        return className;
    }

    private String renderAnnotations() {
        return this.annotations.stream().map(JavaAnnotation::render).collect(Collectors.joining());
    }

}
