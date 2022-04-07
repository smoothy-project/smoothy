package solutions.thex.smoothy.code.java;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

/**
 * Declaration of a field written in Java.
 */
@Builder
@Data
public final class JavaFieldDeclaration implements Annotatable {

	@Default
	private final List<Annotation> annotations = new ArrayList<>();

	private final int modifiers;

	private final String name;

	private final String type;

	private final Object value;

	private final boolean initialized;

	@Override
	public void annotate(Annotation annotation) {
		this.annotations.add(annotation);
	}
}
