package solutions.thex.smoothy.code.java.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.java.JavaExpression;
import solutions.thex.smoothy.code.java.JavaStatement;

/**
 * A return statement.
 */
@Builder
@Getter
public class JavaReturnStatement implements JavaStatement {

	private final JavaExpression expression;
}
