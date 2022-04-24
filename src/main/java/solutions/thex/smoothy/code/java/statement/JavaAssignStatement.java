package solutions.thex.smoothy.code.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.Statement;


public record JavaAssignStatement(String variable,
                                  Expression expression) implements Statement {

    @Builder
    public JavaAssignStatement {
    }

    @Override
    public String render() {
        return this.variable + " = " + this.expression.render() + ";";
    }

}
