package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaStatement;

/**
 * A statement that contains a single expression.
 */
@Builder
@Getter
public class JavaExpressionStatement implements JavaStatement {

    private final JavaExpression expression;

}
