package solutions.thex.smoothy.code.java.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.java.JavaExpression;
import solutions.thex.smoothy.code.java.JavaStatement;

/**
 * A statement that contains a single expression.
 */
@Builder
@Getter
public class JavaExpressionStatement implements JavaStatement {

    private final JavaExpression expression;

}
