package solutions.thex.smoothy.generator.spring.main.test;

import solutions.thex.smoothy.code.java.util.JavaAnnotation;
import solutions.thex.smoothy.code.java.source.JavaCompilationUnit;
import solutions.thex.smoothy.code.java.util.JavaModifier;
import solutions.thex.smoothy.code.java.util.JavaType;
import solutions.thex.smoothy.code.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.java.source.JavaTypeDeclaration;
import solutions.thex.smoothy.util.StringFormatter;

import java.lang.reflect.Modifier;
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
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC)//
                                        .build())
                                .annotations(List.of(//
                                        JavaAnnotation.builder()//
                                                .name("org.springframework.boot.test.context.SpringBootTest")//
                                                .build()))//
                                .methodDeclarations(List.of(//
                                        JavaMethodDeclaration.builder()//
                                                .name("contextLoads")//
                                                .returnType("void")//
                                                .annotations(List.of(//
                                                        JavaAnnotation.builder()//
                                                                .name("org.junit.jupiter.api.Test")//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();
    }

}
