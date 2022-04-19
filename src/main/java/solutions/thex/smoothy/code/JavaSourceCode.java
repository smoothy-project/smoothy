package solutions.thex.smoothy.code;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.soy.ISoyConfiguration;

/**
 * Java SourceCode.
 */
@Builder
@Getter
public class JavaSourceCode {

	@Default
	private List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
	@Default
	private List<JavaCompilationUnit> testCompilationUnits = new ArrayList<>();
	@Default
	private List<ISoyConfiguration> staticCompilationUnits = new ArrayList<>();

}
