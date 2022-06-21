package solutions.thex.smoothy.core.declaration.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.core.declaration.Expression;
import solutions.thex.smoothy.core.declaration.Statement;
import solutions.thex.smoothy.core.declaration.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.core.declaration.java.util.JavaModifier;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public record JavaDeclarationStatement(JavaModifier modifiers, String name, String type,//
                                       boolean initialized,
                                       Expression expression, List<String> genericTypes) implements Statement {

    @Builder
    public JavaDeclarationStatement {
    }

    @Override
    public String render() {
        StringBuilder str = new StringBuilder();
        if (modifiers != null) str.append(this.modifiers.render());
        str.append(JavaSourceCodeWriter.getUnqualifiedName(this.type));
        if (this.genericTypes != null) {
            str.append("<").append(this.genericTypes.stream()//
                            .map(JavaSourceCodeWriter::getUnqualifiedName)//
                            .collect(Collectors.joining(", ")))//
                    .append("> ");
        }
        str.append(" ").append(this.name);
        if (this.initialized) str.append(" = ").append(this.expression.render());
        str.append(";");
        return str.toString();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.type)) imports.add(this.type);
        if (this.initialized) imports.addAll(this.expression.imports());
        return new LinkedHashSet<>(imports);
    }

}
