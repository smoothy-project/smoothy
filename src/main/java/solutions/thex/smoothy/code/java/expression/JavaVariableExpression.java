package solutions.thex.smoothy.code.java.expression;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.Operable;

import java.util.LinkedHashSet;
import java.util.Set;

@SuperBuilder
@Getter
public class JavaVariableExpression extends Operable implements Expression {

    private final String variable;

    @Override
    public String render() {
        return variable + super.render();
    }

    @Override
    public Set<String> imports() {
        return new LinkedHashSet<>();
    }

}
