package solutions.thex.smoothy.core.declaration.java.expression;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.core.declaration.Expression;
import solutions.thex.smoothy.core.declaration.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.core.declaration.java.expression.util.Operable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
public class JavaLambdaMethodInvocationExpression extends Operable implements Expression {

    private final String target;
    private final String invoke;

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + JavaSourceCodeWriter.getUnqualifiedName(this.target) + "::" + this.invoke;
        return JavaSourceCodeWriter.getUnqualifiedName(this.target) + "::" + this.invoke + super.render();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.target)) imports.add(this.target);
        return new LinkedHashSet<>(imports);
    }

}
