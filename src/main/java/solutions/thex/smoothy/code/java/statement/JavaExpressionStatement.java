package solutions.thex.smoothy.code.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.Statement;

/**
 * A statement that contains a single expression.
 */
public record JavaExpressionStatement(Expression expression) implements Statement {

    @Builder
    public JavaExpressionStatement {
    }

    @Override
    public String render() {
        return expression.render() + ";";
    }

}
