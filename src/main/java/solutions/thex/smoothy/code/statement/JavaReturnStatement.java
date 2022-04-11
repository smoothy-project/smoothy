package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaStatement;

/**
 * A return statement.
 */
@Builder
@Getter
public class JavaReturnStatement implements JavaStatement {

	private final JavaExpression expression;
}
