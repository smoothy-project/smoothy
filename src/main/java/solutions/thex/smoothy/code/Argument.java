package solutions.thex.smoothy.code;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import solutions.thex.smoothy.code.expression.JavaMethodInvocationExpression;

import java.util.LinkedList;
import java.util.List;

@Builder
@Data
@Getter
public class Argument {

    @Builder.Default
    private final List<JavaMethodInvocationExpression.MethodInvoke> invokes = new LinkedList<>();
    @Builder.Default
    private boolean lambda = false;
    @Builder.Default
    private boolean invoked = false;
    @Builder.Default
    private boolean newed = false;
    private final String name;

    public String render() {
        if (isInvoked() && isLambda()) {
            return JavaSourceCodeWriter.getUnqualifiedName(name) + "::" + invokes.get(0).render();
        } else if (isInvoked() && !isLambda() && !isNewed()) {
            return JavaSourceCodeWriter.getUnqualifiedName(name) + "." + invokes.stream().map(JavaMethodInvocationExpression.MethodInvoke::render).reduce((a, b) -> a + "." + b).get();
        } else if (isNewed()) {
            System.out.println("newed");
            return "new " + JavaSourceCodeWriter.getUnqualifiedName(name) + "(" + invokes.stream().map(JavaMethodInvocationExpression.MethodInvoke::render).reduce((a, b) -> a + "." + b).get() + ")";
        } else {
            if ((name.startsWith("\"")))
                return name;
            else if (name.endsWith("class"))
                return name.split("\\.")[name.split("\\.").length - 2] + ".class";
            else if (name.contains("::"))
                return name.split("::")[0].split("\\.")[name.split("::")[0].split("\\.").length - 1] + "::" + name.split("::")[1];
            else if (name.contains(".") && JavaSourceCodeWriter.isUpperCase(name.split("\\.")[name.split("\\.").length - 1]))
                return name.split("\\.")[name.split("\\.").length - 2] + "." + name.split("\\.")[name.split("\\.").length - 1];

            return JavaSourceCodeWriter.getUnqualifiedName(name);
        }
    }

}
