package solutions.thex.smoothy.core.declaration.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.core.declaration.Statement;
import solutions.thex.smoothy.core.declaration.formatting.IndentingWriter;
import solutions.thex.smoothy.core.declaration.formatting.SimpleIndentStrategy;
import solutions.thex.smoothy.core.declaration.java.expression.JavaBraceletExpression;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record JavaControlStatement(List<If> ifs, Else eLse) implements Statement {

    @Builder
    public JavaControlStatement {
    }

    @Override
    public String render() {
        IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy("    "));
        writer.print("if (");
        writer.print(ifs.get(0).condition.render());
        writer.println(") {");
        writer.indented(() -> {
            writer.println(ifs.get(0).render());
        });
        if (ifs.size() > 1) {
            for (int i = 1; i < ifs.size(); i++) {
                writer.print("} else if (");
                writer.print(ifs.get(i).condition.render());
                writer.println(") {");
                int finalI = i;
                writer.indented(() -> {
                    writer.println(ifs.get(finalI).render());
                });
            }
        } else {
            writer.print("}");
        }
        if (eLse != null) {
            writer.println("} else {");
            writer.indented(() -> {
                writer.println(eLse.render());
            });
            writer.print("}");
        }
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = ifs.stream().flatMap(ifStatement -> ifStatement.imports().stream()).collect(Collectors.toList());
        if (eLse != null)
            imports.addAll(eLse.imports());
        return new LinkedHashSet<>(imports);
    }

    @Builder
    public static class If {

        public JavaBraceletExpression condition;
        public List<Statement> statements;

        protected String render() {
            return statements.stream().map(Statement::render).collect(Collectors.joining(System.lineSeparator()));
        }

        public Set<String> imports() {
            List<String> imports = new ArrayList<>();
            imports.addAll(condition.imports());
            imports.addAll(statements.stream().map(Statement::imports).flatMap(Set::stream).collect(Collectors.toList()));
            return new LinkedHashSet<>(imports);
        }

    }

    @Builder
    public static class Else {

        public List<Statement> statements;

        protected String render() {
            return statements.stream().map(Statement::render).collect(Collectors.joining());
        }

        public Set<String> imports() {
            return statements.stream().map(Statement::imports).flatMap(Set::stream).collect(Collectors.toCollection(LinkedHashSet::new));
        }

    }

}
