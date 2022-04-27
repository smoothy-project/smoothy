package solutions.thex.smoothy.code.java.source;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.code.CompilationUnit;
import solutions.thex.smoothy.code.SourceCode;
import solutions.thex.smoothy.soy.ISoyConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Java SourceCode.
 */
@Builder
@Getter
public final class JavaSourceCode implements SourceCode {

    @Default
    private List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
    @Default
    private List<JavaCompilationUnit> testCompilationUnits = new ArrayList<>();
    @Default
    private List<ISoyConfiguration> staticCompilationUnits = new ArrayList<>();

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        return this.compilationUnits.stream().map(CompilationUnit.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<CompilationUnit> getTestCompilationUnits() {
        return this.testCompilationUnits.stream().map(CompilationUnit.class::cast).collect(Collectors.toList());
    }

}
