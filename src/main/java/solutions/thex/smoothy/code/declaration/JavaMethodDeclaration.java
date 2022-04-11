package solutions.thex.smoothy.code.declaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import solutions.thex.smoothy.code.Annotatable;
import solutions.thex.smoothy.code.Annotation;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.Parameter;

/**
 * Declaration of a method written in Java.
 */
@Builder
@Data
public final class JavaMethodDeclaration implements Annotatable {

	private final List<Annotation> annotations = new ArrayList<>();

	private final String name;

	private final String returnType;

	private final int modifiers;

	@Default
	private final List<Parameter> parameters = new LinkedList<>();

	@Default
	private final List<JavaStatement> statements = new LinkedList<>();

	@Override
	public void annotate(Annotation annotation) {
		this.annotations.add(annotation);
	}

	@Override
	public List<Annotation> getAnnotations() {
		return Collections.unmodifiableList(this.annotations);
	}

}
