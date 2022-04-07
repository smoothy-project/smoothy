package solutions.thex.smoothy.code.java;

import lombok.Builder;
import lombok.Getter;

/**
 * A return statement.
 */
@Builder
@Getter
public class JavaReturnStatement implements JavaStatement {

	private final JavaExpression expression;
}
