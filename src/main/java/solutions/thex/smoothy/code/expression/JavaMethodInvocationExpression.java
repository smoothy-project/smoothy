package solutions.thex.smoothy.code.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
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
        private final List<String> arguments = new LinkedList<>();
        @Builder.Default
        private final boolean breakLine = false;
        private String method;

        public String render() {
            return method + "(" +//
                    arguments.stream().map(argument -> {
                        if ((argument.startsWith("\"")))
                            return argument;
                        else if (argument.endsWith("class"))
                            return argument.split("\\.")[argument.split("\\.").length - 2] + ".class";
                        else if (argument.contains(".") && JavaSourceCodeWriter.isUpperCase(argument.split("\\.")[argument.split("\\.").length - 1]))
                            return argument.split("\\.")[argument.split("\\.").length - 2] + "." + argument.split("\\.")[argument.split("\\.").length - 1];

                        return JavaSourceCodeWriter.getUnqualifiedName(argument);
                    }).collect(Collectors.joining(", "))//
                    + ")" + printTabIfBreakLine();
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
