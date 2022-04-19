package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

/**
 * A statement that contains a single expression.
 */
@Getter
public record JavaExpressionStatement(JavaExpression expression) implements JavaStatement {

    @Builder
    public JavaExpressionStatement {
    }

    @Override
    public void render(IndentingWriter writer) {
        expression.render(writer);
        writer.println(";");
    }

}
