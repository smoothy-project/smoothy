package solutions.thex.smoothy.code.java.expression;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;

@Builder
@Getter
public class JavaDotClassExpression implements Expression {

    private final String name;

    @Override
    public String render() {
        return JavaSourceCodeWriter.getUnqualifiedName(this.name) + ".class";
    }

}
