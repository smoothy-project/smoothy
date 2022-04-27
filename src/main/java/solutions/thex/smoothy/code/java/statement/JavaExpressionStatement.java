package solutions.thex.smoothy.code.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.Statement;

import java.util.Set;

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

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

}
