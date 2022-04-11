package solutions.thex.smoothy.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;

/**
 * A Java-compilation unit that represents an individual source file.
 */
@Builder
@Getter
public class JavaCompilationUnit {

	private final String packageName;

	private final String name;

	@Default
	private final List<JavaTypeDeclaration> typeDeclarations = new ArrayList<>();

	public JavaTypeDeclaration createTypeDeclaration(String name) {
		JavaTypeDeclaration typeDeclaration = doCreateTypeDeclaration(name);
		this.typeDeclarations.add(typeDeclaration);
		return typeDeclaration;
	}

	public List<JavaTypeDeclaration> getTypeDeclarations() {
		return Collections.unmodifiableList(this.typeDeclarations);
	}

	protected JavaTypeDeclaration doCreateTypeDeclaration(String name) {
		return JavaTypeDeclaration.builder().name(name).build();
	}

}
