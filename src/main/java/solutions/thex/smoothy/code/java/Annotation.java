package solutions.thex.smoothy.code.java;

import java.util.LinkedList;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

/**
 * An annotation.
 */
@Builder
@Getter
public final class Annotation {

	/**
	 * Define an attribute of an annotation.
	 */
	@Builder
	@Getter
	public static final class Attribute {
		private final String name;
		private final Class<?> type;
		@Default
		private final List<String> values = new LinkedList<>();
	}

	@Default
	private final List<Attribute> attributes = new LinkedList<>();
	private final String name;

}
