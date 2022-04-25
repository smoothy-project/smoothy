package solutions.thex.smoothy.code.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.Statement;

import java.util.Set;

/**
 * A return statement.
 */
public record JavaReturnStatement(Expression expression) implements Statement {

    @Builder
    public JavaReturnStatement {
    }

    @Override
    public String render() {
        return "return " + expression.render() + ";";
    }

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

}
