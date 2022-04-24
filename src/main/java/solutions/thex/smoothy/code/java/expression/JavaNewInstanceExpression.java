package solutions.thex.smoothy.code.java.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.java.MethodInvoke;
import solutions.thex.smoothy.code.java.Operable;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
public class JavaNewInstanceExpression extends Operable implements Expression {

    @Builder.Default
    private final List<Expression> arguments = new LinkedList<>();
    @Builder.Default
    private final List<MethodInvoke> invokes = new LinkedList<>();
    private final String name;

    @Override
    public String render() {
        return "new " + JavaSourceCodeWriter.getUnqualifiedName(this.name)//
                + "("//
                + this.arguments.stream().map(Expression::render).collect(Collectors.joining(", "))//
                + ")"//
                + this.invokes.stream().map(MethodInvoke::render).collect(Collectors.joining())//
                + super.render();
    }

}
