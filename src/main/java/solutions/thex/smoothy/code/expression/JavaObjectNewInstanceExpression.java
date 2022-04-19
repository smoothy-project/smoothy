package solutions.thex.smoothy.code.expression;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

import java.util.LinkedList;
import java.util.List;

@Builder
@Getter
public class JavaObjectNewInstanceExpression implements JavaExpression {

    private final String name;
    @Builder.Default
    private final List<String> arguments = new LinkedList<>();

    @Override
    public void render(IndentingWriter writer) {
        writer.print("new " + name + "(" + String.join(", ", arguments) + ")");
    }

}
