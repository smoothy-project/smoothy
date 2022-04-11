package solutions.thex.smoothy.code;

import lombok.Builder;
import lombok.Data;

/**
 * A parameter, typically of a method or function.
 */

@Builder
@Data
public class Parameter {

	private final String type;
	private final String name;

}
