package solutions.thex.smoothy.code.java.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.expression.util.Operable;

import java.util.*;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
public class JavaBraceletExpression extends Operable implements Expression {

    @Builder.Default
    List<Expression> expressions = new LinkedList<>();
    @Builder.Default
    private boolean bracelet = true;

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + ((bracelet) ? "(" : "") + expressions.stream().map(Expression::render).collect(Collectors.joining()) + ((bracelet) ? ")" : "");
        return ((bracelet) ? "(" : "") + expressions.stream().map(Expression::render).collect(Collectors.joining()) + ((bracelet) ? ")" : "") + super.render();
    }

    @Override
    public Set<String> imports() {
        return this.expressions.stream().map(Expression::imports).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
