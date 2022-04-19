package solutions.thex.smoothy.generator.spring.main.src.java;

import solutions.thex.smoothy.code.Annotation;
import solutions.thex.smoothy.code.JavaCompilationUnit;
import solutions.thex.smoothy.code.JavaType;
import solutions.thex.smoothy.code.Parameter;
import solutions.thex.smoothy.code.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;
import solutions.thex.smoothy.code.expression.JavaMethodInvocationExpression;
import solutions.thex.smoothy.code.statement.JavaExpressionStatement;
import solutions.thex.smoothy.util.StringFormatter;

import java.lang.reflect.Modifier;
import java.util.List;

public class MainClassGenerator {

    public static JavaCompilationUnit generate(String name) {
        return JavaCompilationUnit.builder()//
                .packageName("website.smoothy.".concat(name.toLowerCase()))//
                .name(StringFormatter.toPascalCase(name.concat("Application")))//
                .typeDeclarations(List.of(//
                        JavaTypeDeclaration.builder()//
                                .type(JavaType.CLASS)//
                                .name(StringFormatter.toPascalCase(name.concat("Application")))//
                                .modifiers(Modifier.PUBLIC)//
                                .annotations(List.of(//
                                        Annotation.builder()//
                                                .name("org.springframework.boot.autoconfigure.SpringBootApplication")//
                                                .build()))//
                                .methodDeclarations(List.of(//
                                        JavaMethodDeclaration.builder()//
                                                .name("main")//
                                                .modifiers(Modifier.PUBLIC | Modifier.STATIC)//
                                                .returnType("void")//
                                                .parameters(List.of(//
                                                        Parameter.builder()//
                                                                .type("java.lang.String[]")//
                                                                .name("args")//
                                                                .build()))//
                                                .statements(List.of(//
                                                        JavaExpressionStatement.builder()
                                                                .expression(JavaMethodInvocationExpression.builder()//
                                                                        .target("org.springframework.boot.SpringApplication")//
                                                                        .invokes(List.of(//
                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                        .method("run")//
                                                                                        .arguments(List.of(//
                                                                                                StringFormatter.toPascalCase(name.concat("Application")).concat(".class"), "args"))//
                                                                                        .build()))//
                                                                        .build())//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();
    }

}
