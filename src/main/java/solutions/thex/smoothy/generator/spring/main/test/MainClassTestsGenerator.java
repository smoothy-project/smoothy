package solutions.thex.smoothy.generator.spring.main.test;

import solutions.thex.smoothy.code.Annotation;
import solutions.thex.smoothy.code.JavaCompilationUnit;
import solutions.thex.smoothy.code.JavaType;
import solutions.thex.smoothy.code.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;
import solutions.thex.smoothy.util.StringFormatter;

import java.util.List;

public class MainClassTestsGenerator {

    public static JavaCompilationUnit generate(String name) {
        return JavaCompilationUnit.builder()//
                .packageName("website.smoothy.".concat(name.toLowerCase()))
                .name(StringFormatter.toPascalCase(name.concat("ApplicationTests")))//
                .typeDeclarations(List.of(//
                        JavaTypeDeclaration.builder()//
                                .type(JavaType.CLASS)//
                                .name(StringFormatter.toPascalCase(name.concat("ApplicationTests")))//
                                .annotations(List.of(//
                                        Annotation.builder()//
                                                .name("org.springframework.boot.test.context.SpringBootTest")//
                                                .build()))//
                                .methodDeclarations(List.of(//
                                        JavaMethodDeclaration.builder()//
                                                .name("contextLoads")//
                                                .returnType("void")//
                                                .annotations(List.of(//
                                                        Annotation.builder()//
                                                                .name("org.junit.jupiter.api.Test")//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();
    }

}
