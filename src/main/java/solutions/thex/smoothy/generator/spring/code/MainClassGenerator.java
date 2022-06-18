package solutions.thex.smoothy.generator.spring.code;

import solutions.thex.smoothy.code.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.java.expression.JavaMethodInvocationExpression;
import solutions.thex.smoothy.code.java.expression.JavaValueExpression;
import solutions.thex.smoothy.code.java.expression.JavaVariableExpression;
import solutions.thex.smoothy.code.java.expression.util.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.source.JavaCompilationUnit;
import solutions.thex.smoothy.code.java.source.JavaTypeDeclaration;
import solutions.thex.smoothy.code.java.statement.JavaExpressionStatement;
import solutions.thex.smoothy.code.java.util.JavaAnnotation;
import solutions.thex.smoothy.code.java.util.JavaModifier;
import solutions.thex.smoothy.code.java.util.JavaType;
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
                                .modifiers(JavaModifier.builder()//
                                        .type(JavaModifier.TYPE_MODIFIERS)//
                                        .modifiers(Modifier.PUBLIC)//
                                        .build())//
                                .annotations(List.of(//
                                        JavaAnnotation.builder()//
                                                .name("org.springframework.boot.autoconfigure.SpringBootApplication")//
                                                .build()))//
                                .methodDeclarations(List.of(//
                                        JavaMethodDeclaration.builder()//
                                                .name("main")//
                                                .modifiers(JavaModifier.builder()//
                                                        .type(JavaModifier.METHOD_MODIFIERS)//
                                                        .modifiers(Modifier.PUBLIC | Modifier.STATIC)//
                                                        .build())//
                                                .returnType("void")//
                                                .parameters(List.of(//
                                                        JavaMethodDeclaration.Parameter.builder()//
                                                                .type("java.lang.String[]")//
                                                                .name("args")//
                                                                .build()))//
                                                .statements(List.of(//
                                                        JavaExpressionStatement.builder()
                                                                .expression(JavaMethodInvocationExpression.builder()//
                                                                        .target("org.springframework.boot.SpringApplication")//
                                                                        .invokes(List.of(//
                                                                                JavaMethodInvoke.builder()//
                                                                                        .method("run")//
                                                                                        .arguments(List.of(//
                                                                                                JavaValueExpression.builder()//
                                                                                                        .value(StringFormatter.toPascalCase(name.concat("Application")))//
                                                                                                        .type(Class.class)
                                                                                                        .build(),//
                                                                                                JavaVariableExpression.builder()//
                                                                                                        .variable("args")//
                                                                                                        .build()))//
                                                                                        .build()))//
                                                                        .build())//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();
    }

}
