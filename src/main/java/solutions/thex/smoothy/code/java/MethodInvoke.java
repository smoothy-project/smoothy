package solutions.thex.smoothy.code.java;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import solutions.thex.smoothy.code.Expression;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@ToString
public final class MethodInvoke {

    @Builder.Default
    private final List<Expression> arguments = new LinkedList<>();
    @Builder.Default
    private final boolean breakLine = false;
    private final String method;

    public String render() {
        return "." + getUnqualifiedName(method)//
                + "("
                + arguments.stream().map(Expression::render).collect(Collectors.joining(", "))//
                + ")"//
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
