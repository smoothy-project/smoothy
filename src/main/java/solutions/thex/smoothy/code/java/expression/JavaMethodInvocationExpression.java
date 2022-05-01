package solutions.thex.smoothy.code.java.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.java.expression.util.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.expression.util.Operable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An invocation of a method.
 */
@SuperBuilder
@Getter
public class JavaMethodInvocationExpression extends Operable implements Expression {

    @Builder.Default
    private final List<JavaMethodInvoke> invokes = new LinkedList<>();
    private final String target;

    @Override
    public String render() {
        if (this.invokes.isEmpty()) {
            if ("!".equals(super.render()))
                return super.render() + this.target + "()";
            else
                return this.target + "()" + super.render();
        } else {
            if ("!".equals(super.render()))
                return super.render() + JavaSourceCodeWriter.getUnqualifiedName(this.target)//
                        + invokes.stream().map(JavaMethodInvoke::render).collect(Collectors.joining());
            else
                return JavaSourceCodeWriter.getUnqualifiedName(this.target)//
                        + invokes.stream().map(JavaMethodInvoke::render).collect(Collectors.joining()) + super.render();

        }
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.target)) imports.add(this.target);
        imports.addAll(this.invokes.stream().map(JavaMethodInvoke::imports).flatMap(Collection::stream).collect(Collectors.toList()));
        return new LinkedHashSet<>(imports);
    }

}
