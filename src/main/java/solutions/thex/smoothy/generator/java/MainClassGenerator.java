package solutions.thex.smoothy.generator.java;

import solutions.thex.smoothy.code.Annotation;
import solutions.thex.smoothy.code.JavaCompilationUnit;
import solutions.thex.smoothy.code.JavaType;
import solutions.thex.smoothy.code.Parameter;
import solutions.thex.smoothy.code.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;
import solutions.thex.smoothy.code.expression.JavaMethodInvocation;
import solutions.thex.smoothy.code.statement.JavaExpressionStatement;
import solutions.thex.smoothy.util.StringFormatter;

import java.lang.reflect.Modifier;
import java.util.List;

public class MainClassGenerator {

    public static JavaCompilationUnit generate(String name) {
        return JavaCompilationUnit.builder()//
                .packageName("website.smoothy.".concat(name))//
                .name(StringFormatter.toPascalCase(name.concat("Application")))//
                .typeDeclarations(List.of(JavaTypeDeclaration.builder()//
                        .type(JavaType.CLASS)//
                        .name(StringFormatter.toPascalCase(name.concat("Application")))//
                        .modifiers(Modifier.PUBLIC)//
                        .annotations(List.of(Annotation.builder()//
                                .name("org.springframework.boot.autoconfigure.SpringBootApplication")//
                                .build()))//
                        .methodDeclarations(List.of(JavaMethodDeclaration.builder()//
                                .name("main")//
                                .modifiers(Modifier.PUBLIC | Modifier.STATIC)//
                                .returnType("void")//
                                .parameters(List.of(Parameter.builder()//
                                        .type("java.lang.String[]")//
                                        .name("args")//
                                        .build()))//
                                .statements(List.of(JavaExpressionStatement.builder()
                                        .expression(JavaMethodInvocation.builder()//
                                                .target("org.springframework.boot.SpringApplication")//
                                                .name("run")//
                                                .arguments(List.of("Application.class", "args"))//
                                                .build())//
                                        .build())//
                                ).build()))//
                        .build()))//
                .build();
    }

}
