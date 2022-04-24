package solutions.thex.smoothy.code.java.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.java.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.Operable;

import java.util.LinkedList;
import java.util.List;
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
        return JavaSourceCodeWriter.getUnqualifiedName(this.target)//
                + invokes.stream().map(JavaMethodInvoke::render).collect(Collectors.joining()) + super.render();
    }

}
