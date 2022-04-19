package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

/**
 * A return statement.
 */
public record JavaReturnStatement(@Getter JavaExpression expression) implements JavaStatement {

    @Builder
    public JavaReturnStatement {
    }

    @Override
    public void render(IndentingWriter writer) {
        writer.print("return ");
        expression.render(writer);
        writer.println(";");
    }

}
