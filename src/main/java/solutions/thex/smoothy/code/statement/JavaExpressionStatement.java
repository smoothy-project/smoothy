package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

/**
 * A statement that contains a single expression.
 */
public record JavaExpressionStatement(JavaExpression expression) implements JavaStatement {

    @Builder
    public JavaExpressionStatement {
    }

    @Override
    public JavaExpression getExpression() {
        return expression;
    }

    @Override
    public void render(IndentingWriter writer) {
        expression.render(writer);
        writer.println(";");
    }

}
