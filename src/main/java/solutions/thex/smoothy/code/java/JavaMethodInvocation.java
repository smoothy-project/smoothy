package solutions.thex.smoothy.code.java;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * An invocation of a method.
 */
@Builder
@Getter
public class JavaMethodInvocation implements JavaExpression {

	private final String target;

	private final String name;

	private final List<String> arguments;

}
