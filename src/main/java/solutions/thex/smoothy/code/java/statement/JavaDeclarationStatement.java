package solutions.thex.smoothy.code.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.Statement;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.java.util.JavaModifier;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public record JavaDeclarationStatement(JavaModifier modifiers, String name, String type,//
                                       boolean initialized,
                                       Expression expression) implements Statement {

    @Builder
    public JavaDeclarationStatement {
    }

    @Override
    public String render() {
        StringBuilder str = new StringBuilder();
        if (modifiers != null) str.append(this.modifiers.render());
        str.append(JavaSourceCodeWriter.getUnqualifiedName(this.type)).append(" ").append(this.name);
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
