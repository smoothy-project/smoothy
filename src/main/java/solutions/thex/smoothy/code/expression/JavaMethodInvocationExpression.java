package solutions.thex.smoothy.code.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import solutions.thex.smoothy.code.Argument;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An invocation of a method.
 */
@Builder
@Getter
public class JavaMethodInvocationExpression implements JavaExpression {

    @Builder.Default
    private final List<MethodInvoke> invokes = new LinkedList<>();
    private final String target;

    @Builder
    @Getter
    @ToString
    public static final class MethodInvoke {

        @Builder.Default
        private final List<Argument> arguments = new LinkedList<>();
        @Builder.Default
        private final boolean breakLine = false;
        @Builder.Default
        private final boolean lambda = false;
        private String method;

        public String render() {
            return getUnqualifiedName(method) +//
                    ((lambda) ?//
                            ""//
                            ://
                            ("(" +//
                                    arguments.stream()//
                                            .map(Argument::render)//
                                            .collect(Collectors.joining(", "))//
                                    + ")"))//
                    + printTabIfBreakLine();
        }

        private String getUnqualifiedName(String name) {
            if (!name.contains(".") || name.split("\\.").length <= 2) {
                return name;
            }
            return name.split("\\.")[name.split("\\.").length - 2] + "." + name.split("\\.")[name.split("\\.").length - 1];
        }

        private String printTabIfBreakLine() {
            return (breakLine) ? "//\n" + "    " + "    " : ""; //TODO: give indentation strategy
        }

    }

    @Override
    public void render(IndentingWriter writer) {
        writer.print(//
                JavaSourceCodeWriter.getUnqualifiedName(this.target)//
                        + "."//
                        + invokes.stream().map(MethodInvoke::render).reduce((a, b) -> a + "." + b).orElse(""));
    }

}
