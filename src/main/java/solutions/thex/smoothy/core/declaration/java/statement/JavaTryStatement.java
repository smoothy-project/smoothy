package solutions.thex.smoothy.core.declaration.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.core.declaration.Statement;
import solutions.thex.smoothy.core.declaration.formatting.IndentingWriter;
import solutions.thex.smoothy.core.declaration.formatting.SimpleIndentStrategy;
import solutions.thex.smoothy.core.declaration.java.JavaSourceCodeWriter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record JavaTryStatement(List<Statement> statements, List<Catch> catches) implements Statement {

    @Builder
    public JavaTryStatement {
    }

    @Override
    public String render() {
        IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy("    "));
        writer.println("try {");
        writer.indented(() -> {
            writer.println(statements.stream().map(Statement::render).collect(Collectors.joining(System.lineSeparator())));
        });
        writer.print("} ");
        writer.print(catches.stream().map(Catch::render).collect(Collectors.joining()));
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        imports.addAll(statements.stream().map(Statement::imports).flatMap(Set::stream).collect(Collectors.toList()));
        imports.addAll(catches.stream().map(Catch::imports).flatMap(Set::stream).collect(Collectors.toList()));
        return new LinkedHashSet<>(imports);
    }

    @Builder
    public static class Catch {

        public String exception;
        public String name;
        public List<Statement> statements;

        public String render() {
            IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy("    "));
            writer.println("catch (" + JavaSourceCodeWriter.getUnqualifiedName(exception) + " " + name + ") {");
            writer.indented(() -> {
                writer.println(statements.stream().map(Statement::render).collect(Collectors.joining(System.lineSeparator())));
            });
            writer.print("} ");
            return writer.render();
        }

        public Set<String> imports() {
            List<String> imports = new ArrayList<>();
            if (JavaSourceCodeWriter.requiresImport(exception)) imports.add(exception);
            imports.addAll(statements.stream().map(Statement::imports).flatMap(Set::stream).collect(Collectors.toList()));
            return new LinkedHashSet<>(imports);
        }

    }
}
