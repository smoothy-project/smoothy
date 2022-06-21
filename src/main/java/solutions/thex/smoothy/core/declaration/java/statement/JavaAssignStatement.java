package solutions.thex.smoothy.core.declaration.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.core.declaration.Expression;
import solutions.thex.smoothy.core.declaration.Statement;

import java.util.Set;


public record JavaAssignStatement(String variable,
                                  Expression expression) implements Statement {

    @Builder
    public JavaAssignStatement {
    }

    @Override
    public String render() {
        return this.variable + " = " + this.expression.render() + ";";
    }

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

}
