package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;


@Getter
public record JavaAssignStatement(String variable,
                                  JavaExpression expression) implements JavaStatement {

    @Builder
    public JavaAssignStatement {
    }

    @Override
    public void render(IndentingWriter writer) {
        writer.print(variable.concat(" = "));
        expression.render(writer);
        writer.println(";");
    }

}
