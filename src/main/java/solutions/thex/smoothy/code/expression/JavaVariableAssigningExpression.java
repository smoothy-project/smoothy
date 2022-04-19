package solutions.thex.smoothy.code.expression;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

@Builder
@Getter
public class JavaVariableAssigningExpression implements JavaExpression {

    private final String variable;

    @Override
    public void render(IndentingWriter writer) {
        writer.print(variable);
    }

}
