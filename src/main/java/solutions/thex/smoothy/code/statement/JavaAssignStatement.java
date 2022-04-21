package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;


public record JavaAssignStatement(String variable,
                                  JavaExpression expression) implements JavaStatement {

    @Builder
    public JavaAssignStatement {
    }

    @Override
    public JavaExpression getExpression() {
        return expression;
    }

    @Override
    public void render(IndentingWriter writer) {
        writer.print(variable.concat(" = "));
        expression.render(writer);
        writer.println(";");
    }

}
