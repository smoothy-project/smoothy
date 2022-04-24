package solutions.thex.smoothy.code.java.expression;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.Operable;

@SuperBuilder
@Getter
public class JavaPlainValueExpression extends Operable implements Expression {

    private final String value;

    @Override
    public String render() {
        return value + super.render();
    }

}
