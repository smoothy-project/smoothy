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
        return ((bracelet) ? "(" : "") + expressions.stream().map(Expression::render).collect(Collectors.joining()) + ((bracelet) ? ")" : "");
    }

    @Override
    public Set<String> imports() {
        return this.expressions.stream().map(Expression::imports).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
