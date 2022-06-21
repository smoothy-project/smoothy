package solutions.thex.smoothy.core.declaration.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.core.declaration.Expression;
import solutions.thex.smoothy.core.declaration.Statement;

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
