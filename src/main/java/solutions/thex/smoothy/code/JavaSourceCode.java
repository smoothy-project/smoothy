package solutions.thex.smoothy.code;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

/**
 * Java SourceCode.
 */
@Builder
@Getter
public class JavaSourceCode {

	@Default
	private List<JavaCompilationUnit> compilationUnits = new ArrayList<>();

}
