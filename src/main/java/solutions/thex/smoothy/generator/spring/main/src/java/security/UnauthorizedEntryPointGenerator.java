package solutions.thex.smoothy.generator.spring.main.src.java.security;

import solutions.thex.smoothy.code.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.java.expression.JavaBraceletExpression;
import solutions.thex.smoothy.code.java.expression.JavaMethodInvocationExpression;
import solutions.thex.smoothy.code.java.expression.JavaValueExpression;
import solutions.thex.smoothy.code.java.expression.util.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.source.JavaCompilationUnit;
import solutions.thex.smoothy.code.java.source.JavaTypeDeclaration;
import solutions.thex.smoothy.code.java.statement.JavaExpressionStatement;
import solutions.thex.smoothy.code.java.util.JavaAnnotation;
import solutions.thex.smoothy.code.java.util.JavaModifier;
import solutions.thex.smoothy.code.java.util.JavaOperand;
import solutions.thex.smoothy.code.java.util.JavaType;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;
import java.util.List;

public class UnauthorizedEntryPointGenerator {

    public static JavaCompilationUnit generate(String name) {
        return JavaCompilationUnit.builder()//
                .packageName("website.smoothy.".concat(name.toLowerCase()).concat(".security"))//
                .name("UnauthorizedEntryPoint")//
                .typeDeclarations(
                        List.of(//
                                JavaTypeDeclaration.builder()//
                                        .type(JavaType.CLASS)//
                                        .name("UnauthorizedEntryPoint")//
                                        .modifiers(JavaModifier.builder()//
                                                .type(JavaModifier.TYPE_MODIFIERS)//
                                                .modifiers(Modifier.PUBLIC | Modifier.FINAL)//
                                                .build())//
                                        .implementedClassName("org.springframework.security.web.AuthenticationEntryPoint")//
                                        .annotations(List.of(//
                                                JavaAnnotation.builder()//
                                                        .name("org.springframework.stereotype.Component")//
                                                        .build(),//
                                                JavaAnnotation.builder()//
                                                        .name("lombok.extern.slf4j.Slf4j")//
                                                        .build()))//
                                        .methodDeclarations(List.of(//
                                                JavaMethodDeclaration.builder()//
                                                        .name("commence")//
                                                        .returnType("void")//
                                                        .modifiers(JavaModifier.builder()//
                                                                .type(JavaModifier.METHOD_MODIFIERS)//
                                                                .modifiers(Modifier.PUBLIC)//
                                                                .build())//
                                                        .isThrows(true)//
                                                        .exceptions(List.of(//
                                                                "java.io.IOException"))//
                                                        .annotations(List.of(//
                                                                JavaAnnotation.builder()//
                                                                        .name("org.springframework.context.annotation.Bean")//
                                                                        .build()))//
                                                        .parameters(List.of(//
                                                                JavaMethodDeclaration.Parameter.builder()//
                                                                        .name("request")//
                                                                        .type("javax.servlet.http.HttpServletRequest")//
                                                                        .build(),//
                                                                JavaMethodDeclaration.Parameter.builder()//
                                                                        .name("response")//
                                                                        .type("javax.servlet.http.HttpServletResponse")//
                                                                        .build(),//
                                                                JavaMethodDeclaration.Parameter.builder()//
                                                                        .name("authException")//
                                                                        .type("org.springframework.security.core.AuthenticationException")//
                                                                        .build()))//
                                                        .statements(List.of(//
                                                                JavaExpressionStatement.builder()//
                                                                        .expression(JavaMethodInvocationExpression.builder()//
                                                                                .target("log")//
                                                                                .invokes(List.of(//
                                                                                        JavaMethodInvoke.builder()//
                                                                                                .method("warn")//
                                                                                                .arguments(List.of(//
                                                                                                        JavaBraceletExpression.builder()
                                                                                                                .bracelet(false)//
                                                                                                                .expressions(List.of(//
                                                                                                                        JavaValueExpression.builder()//
                                                                                                                                .value("UnauthorizedEntryPoint.commence: path= ")//
                                                                                                                                .type(String.class)//
                                                                                                                                .operand(JavaOperand.PLUS)//
                                                                                                                                .build(),//
                                                                                                                        JavaMethodInvocationExpression.builder()//
                                                                                                                                .target("request")//
                                                                                                                                .invokes(List.of(//
                                                                                                                                        JavaMethodInvoke.builder()//
                                                                                                                                                .method("getRequestURI")//
                                                                                                                                                .build()))//
                                                                                                                                .operand(JavaOperand.PLUS)//
                                                                                                                                .build(),//
                                                                                                                        JavaValueExpression.builder()//
                                                                                                                                .value(", ip= ")//
                                                                                                                                .type(String.class)//
                                                                                                                                .operand(JavaOperand.PLUS)//
                                                                                                                                .build(),//
                                                                                                                        JavaMethodInvocationExpression.builder()//
                                                                                                                                .target("request")//
                                                                                                                                .invokes(List.of(//
                                                                                                                                        JavaMethodInvoke.builder()//
                                                                                                                                                .method("getRemoteAddr")//
                                                                                                                                                .build()))//
                                                                                                                                .operand(JavaOperand.PLUS)//
                                                                                                                                .build(),//
                                                                                                                        JavaValueExpression.builder()//
                                                                                                                                .value(", user agent= ")//
                                                                                                                                .type(String.class)//
                                                                                                                                .operand(JavaOperand.PLUS)//
                                                                                                                                .build(),//
                                                                                                                        JavaMethodInvocationExpression.builder()//
                                                                                                                                .target("request")//
                                                                                                                                .invokes(List.of(//
                                                                                                                                        JavaMethodInvoke.builder()//
                                                                                                                                                .method("getHeader")//
                                                                                                                                                .arguments(List.of(//
                                                                                                                                                        JavaValueExpression.builder()//
                                                                                                                                                                .value("User-Agent")//
                                                                                                                                                                .type(String.class)//
                                                                                                                                                                .build()))//
                                                                                                                                                .build()))//
                                                                                                                                .build()))//
                                                                                                                .build()))//
                                                                                                .build()))//
                                                                                .build())//
                                                                        .build(),//
                                                                JavaExpressionStatement.builder()//
                                                                        .expression(JavaMethodInvocationExpression.builder()//
                                                                                .target("response")//
                                                                                .invokes(List.of(//
                                                                                        JavaMethodInvoke.builder()//
                                                                                                .method("sendError")//
                                                                                                .arguments(List.of(//
                                                                                                        JavaValueExpression.builder()//
                                                                                                                .value("javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED")//
                                                                                                                .type(HttpServletResponse.class)//
                                                                                                                .build()))//
                                                                                                .build()))//
                                                                                .build())//
                                                                        .build()))//
                                                        .build()))//
                                        .build()))//
                .build();
    }
}
