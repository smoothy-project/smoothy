package solutions.thex.smoothy.generator.spring.code;

import lombok.Builder;
import solutions.thex.smoothy.code.java.source.JavaCompilationUnit;
import solutions.thex.smoothy.description.java.JavaTypeDescription;
import solutions.thex.smoothy.generator.spring.SpringBootApplicationDescription;
import solutions.thex.smoothy.generator.spring.code.type.SpringDAOGenerator;
import solutions.thex.smoothy.generator.spring.code.type.SpringTypeGenerator;
import solutions.thex.smoothy.util.StringFormatter;

import java.util.ArrayList;
import java.util.List;

@Builder
public class SpringBootSourceCodeGenerator {

    private SpringBootApplicationDescription springBootApplication;

    public List<JavaCompilationUnit> generate() {
        List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(MainClassGenerator.generate(springBootApplication.getName()));
        compilationUnits.addAll(generateObjects());
        return compilationUnits;
    }

    private List<JavaCompilationUnit> generateObjects() {
        List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        springBootApplication.getTypes().forEach(type -> {
            SpringTypeGenerator generator = createGenerator(type);
            assert generator != null;
            compilationUnits.add(//
                    JavaCompilationUnit.builder()
                            .packageName(generatePackageName(type))//
                            .name(StringFormatter.toPascalCase(type.getName()))//
                            .typeDeclarations(List.of(generator.generateTypeDeclaration()))//
                            .build());
        });
        return compilationUnits;
    }

    private SpringTypeGenerator createGenerator(JavaTypeDescription type) {
        switch (type.getType()) {
            case DAO:
                return SpringDAOGenerator.builder()//
                        .projectName(springBootApplication.getName())//
                        .type(type).build();
            default:
                return null;
        }
    }

    private String generatePackageName(JavaTypeDescription type) {
        return "website.smoothy."//
                .concat(springBootApplication.getName().toLowerCase())//
                .concat(".")//
                .concat(type.getType().toString().toLowerCase());
    }

}
