package solutions.thex.smoothy.code.java.declaration;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import solutions.thex.smoothy.code.Declaration;
import solutions.thex.smoothy.code.Statement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;
import solutions.thex.smoothy.code.formatting.SimpleIndentStrategy;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.java.util.JavaAnnotation;
import solutions.thex.smoothy.code.java.util.JavaModifier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Declaration of a method written in Java.
 */
@Builder
@Getter
public final class JavaMethodDeclaration implements Declaration {

    @Default
    private final List<JavaAnnotation> annotations = new ArrayList<>();
    @Default
    private final List<Parameter> parameters = new LinkedList<>();
    @Default
    private final List<Statement> statements = new LinkedList<>();
    @Default
    private final List<String> exceptions = new LinkedList<>();
    @Default
    private final boolean isThrows = false;
    private final String name;
    private final String returnType;
    private final JavaModifier modifiers;

    @Builder
    @Data
    public static class Parameter {

        @Builder.Default
        private final List<String> genericTypes = new LinkedList<>();
        private final String type;
        private final String name;
        private final JavaModifier modifiers;

    }

    @Override
    public String render() {
        IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy("    "));
        writer.print(renderAnnotations());
        if (this.modifiers != null) writer.print(this.modifiers.render());
        writer.print(renderReturnType());
        writer.print(this.parameters.stream()
                .map((parameter) -> {
                    if (parameter.getGenericTypes().isEmpty())
                        return ((parameter.modifiers != null) ? parameter.modifiers.render() + " " : "") + renderSimpleParameter(parameter);
                    else
                        return ((parameter.modifiers != null) ? parameter.modifiers.render() + " "  : "") + renderGenericParameter(parameter);
                })
                .collect(Collectors.joining(", ")));
        if (isThrows()) {
            writer.print(") throws ");
            writer.print(renderExceptions());
            writer.println(" {");
        } else {
            writer.println(") {");
        }
        writer.indented(() -> {
            getStatements().forEach(statement -> writer.println(statement.render()));
        });
        writer.println("}");
        writer.println();
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.returnType)) imports.add(this.returnType);
        imports.addAll(this.annotations.stream().map(JavaAnnotation::imports).flatMap(Collection::stream).collect(Collectors.toList()));
        // TODO: add imports for parameters generic types
        imports.addAll(this.parameters.stream().map(Parameter::getType).filter(JavaSourceCodeWriter::requiresImport).collect(Collectors.toList()));
        imports.addAll(this.statements.stream().map(Statement::imports).flatMap(Collection::stream).collect(Collectors.toList()));
        imports.addAll(exceptions.stream().filter(JavaSourceCodeWriter::requiresImport).collect(Collectors.toList()));
        return new LinkedHashSet<>(imports);
    }

    private String renderExceptions() {
        return this.exceptions.stream()//
                .map(JavaSourceCodeWriter::getUnqualifiedName)//
                .collect(Collectors.joining(", "));
    }

    private String renderGenericParameter(Parameter parameter) {
        return JavaSourceCodeWriter.getUnqualifiedName(parameter.getType())//
                + "<" +//
                parameter.getGenericTypes().stream()//
                        .map(JavaSourceCodeWriter::getUnqualifiedName)//
                        .collect(Collectors.joining(", "))//
                + "> " + parameter.getName();
    }

    private String renderSimpleParameter(Parameter parameter) {
        return JavaSourceCodeWriter.getUnqualifiedName(parameter.getType()) + " " + parameter.getName();
    }

    private String renderReturnType() {
        return JavaSourceCodeWriter.getUnqualifiedName(this.returnType) + ifReturnValueWasEmptyDontAddWhiteSpace() + this.name + "(";
    }

    /**
     * For constructor methods, the return type is empty.
     *
     * @return If the return type is empty, return an empty string. Otherwise, return a whitespace.
     */
    private String ifReturnValueWasEmptyDontAddWhiteSpace() {
        return getReturnType().equals("") ? "" : " ";
    }

    private String renderAnnotations() {
        return this.annotations.stream().map(JavaAnnotation::render).collect(Collectors.joining());
    }

}
