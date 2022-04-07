package solutions.thex.smoothy.code.java;

import lombok.Builder;
import lombok.Getter;

/**
 * A statement that contains a single expression.
 */
@Builder
@Getter
public class JavaExpressionStatement implements JavaStatement {

	private final JavaExpression expression;

}
