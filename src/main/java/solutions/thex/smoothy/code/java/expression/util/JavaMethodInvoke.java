package solutions.thex.smoothy.code.java.expression.util;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import solutions.thex.smoothy.code.Expression;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@Getter
@ToString
public final class JavaMethodInvoke {

    @Builder.Default
    private final List<Expression> arguments = new LinkedList<>();
    private final String method;

    public String render() {
        return "." + getUnqualifiedName(method)//
                + "("
                + arguments.stream().map(Expression::render).collect(Collectors.joining(", "))//
                + ")";
                //+ printTabIfBreakLine();
    }

    public Set<String> imports() {
        return this.arguments.stream().map(Expression::imports).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String getUnqualifiedName(String name) {
        if (!name.contains(".") || name.split("\\.").length <= 2) {
            return name;
        }
        return name.split("\\.")[name.split("\\.").length - 2] + "." + name.split("\\.")[name.split("\\.").length - 1];
    }

//    private String printTabIfBreakLine() {
//        return (breakLine) ? "//\n" + "    " + "    " : ""; //TODO: give indentation strategy
//    }

}
