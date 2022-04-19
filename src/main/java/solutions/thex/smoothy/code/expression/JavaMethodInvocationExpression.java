package solutions.thex.smoothy.code.expression;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

import java.util.LinkedList;
import java.util.List;

/**
 * An invocation of a method.
 */
@Builder
@Getter
public class JavaMethodInvocationExpression implements JavaExpression {

    private final String target;
    @Builder.Default
    private final List<MethodInvoke> invokes = new LinkedList<>();

    @Builder
    @Getter
    public static final class MethodInvoke {
        @Builder.Default
        private final List<String> arguments = new LinkedList<>();
        private final String method;

        public String render() {
            return method + "(" + String.join(", ", arguments) + ")";
        }
    }

    @Override
    public void render(IndentingWriter writer) {
        writer.print(//
                JavaSourceCodeWriter.getUnqualifiedName(this.target)//
                        + "."//
                        + invokes.stream().map(MethodInvoke::render).reduce((a, b) -> a + "//\n" + writer.getIndent() + writer.getIndent() + "." + b).orElse(""));
    }

}
