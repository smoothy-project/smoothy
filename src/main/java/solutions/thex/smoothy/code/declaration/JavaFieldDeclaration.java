package solutions.thex.smoothy.code.declaration;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;
import solutions.thex.smoothy.code.Annotatable;
import solutions.thex.smoothy.code.Annotation;
import solutions.thex.smoothy.code.JavaDeclaration;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.formatting.IndentingWriter;

/**
 * Declaration of a field written in Java.
 */
@Builder
@Data
public final class JavaFieldDeclaration implements Annotatable, JavaDeclaration {

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

	@Override
	public void render(IndentingWriter writer) {
		JavaSourceCodeWriter.writeAnnotations(writer, this);
		JavaSourceCodeWriter.writeModifiers(writer, JavaSourceCodeWriter.FIELD_MODIFIERS, getModifiers());
		writer.print(JavaSourceCodeWriter.getUnqualifiedName(getType()));
		writer.print(" ");
		writer.print(getName());
		if (isInitialized()) {
			writer.print(" = ");
			writer.print(String.valueOf(getValue()));
		}
		writer.println(";");
		writer.println();
	}

}
