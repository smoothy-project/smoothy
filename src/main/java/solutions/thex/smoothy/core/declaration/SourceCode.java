package solutions.thex.smoothy.core.declaration;

import solutions.thex.smoothy.soy.ISoyConfiguration;

import java.util.List;

public interface SourceCode {

    List<CompilationUnit> getCompilationUnits();

    List<CompilationUnit> getTestCompilationUnits();

    List<ISoyConfiguration> getStaticCompilationUnits();

}
