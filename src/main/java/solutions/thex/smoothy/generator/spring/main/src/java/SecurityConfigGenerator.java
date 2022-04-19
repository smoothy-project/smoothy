package solutions.thex.smoothy.generator.spring.main.src.java;

import solutions.thex.smoothy.code.Annotation;
import solutions.thex.smoothy.code.JavaCompilationUnit;
import solutions.thex.smoothy.code.JavaType;
import solutions.thex.smoothy.code.Parameter;
import solutions.thex.smoothy.code.declaration.JavaFieldDeclaration;
import solutions.thex.smoothy.code.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;
import solutions.thex.smoothy.code.expression.JavaMethodInvocationExpression;
import solutions.thex.smoothy.code.expression.JavaObjectNewInstanceExpression;
import solutions.thex.smoothy.code.expression.JavaVariableAssigningExpression;
import solutions.thex.smoothy.code.statement.JavaAssignStatement;
import solutions.thex.smoothy.code.statement.JavaExpressionStatement;
import solutions.thex.smoothy.code.statement.JavaReturnStatement;

import java.lang.reflect.Modifier;
import java.util.List;

public class SecurityConfigGenerator {

    public static List<JavaCompilationUnit> generate(String name) {
        return List.of(//
                JavaCompilationUnit.builder()//
                        .packageName("website.smoothy.".concat(name.toLowerCase()).concat(".security"))//
                        .name("SecurityConfig")//
                        .typeDeclarations(
                                List.of(//
                                        JavaTypeDeclaration.builder()//
                                                .type(JavaType.CLASS)//
                                                .name("SecurityConfig")//
                                                .modifiers(Modifier.PUBLIC)//
                                                .extendedClassName("org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter")//
                                                .annotations(List.of(//
                                                        Annotation.builder()//
                                                                .name("org.springframework.context.annotation.Configuration")//
                                                                .build(),//
                                                        Annotation.builder()//
                                                                .name("org.springframework.security.config.annotation.web.configuration.EnableWebSecurity")//
                                                                .build(),//
                                                        Annotation.builder()//
                                                                .name("org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity")//
                                                                .attributes(List.of(//
                                                                        Annotation.Attribute.builder()//
                                                                                .name("prePostEnabled")//
                                                                                .type(boolean.class)//
                                                                                .values(List.of("true"))//
                                                                                .build()))//
                                                                .build()))//
                                                .fieldDeclarations(List.of(//
                                                        JavaFieldDeclaration.builder()//
                                                                .name("userDetailsService")//
                                                                .type("org.springframework.security.core.userdetails.UserDetailsService")//
                                                                .modifiers(Modifier.PRIVATE)//
                                                                .initialized(false)//
                                                                .annotations(List.of(//
                                                                        Annotation.builder()//
                                                                                .name("javax.annotation.Resource")//
                                                                                .attributes(List.of(//
                                                                                        Annotation.Attribute.builder()//
                                                                                                .name("name")//
                                                                                                .type(String.class)//
                                                                                                .values(List.of("userDetailsService"))//
                                                                                                .build()))//
                                                                                .build()))//
                                                                .build(),//
                                                        JavaFieldDeclaration.builder()//
                                                                .name("unauthorizedEntryPoint")//
                                                                .type("UnauthorizedEntryPoint")//
                                                                .modifiers(Modifier.PRIVATE | Modifier.FINAL)//
                                                                .initialized(false)//
                                                                .build(),//
                                                        JavaFieldDeclaration.builder()//
                                                                .name("jwtAuthenticationFilter")//
                                                                .type("website.smoothy.".concat(name.toLowerCase()).concat("security.jwt.JwtAuthenticationFilter"))//
                                                                .modifiers(Modifier.PRIVATE | Modifier.FINAL)//
                                                                .initialized(false)//
                                                                .build()))//
                                                .methodDeclarations(List.of(//
                                                        JavaMethodDeclaration.builder()//
                                                                .name("SecurityConfig")//
                                                                .returnType("")//
                                                                .modifiers(Modifier.PUBLIC)//
                                                                .annotations(List.of(//
                                                                        Annotation.builder()//
                                                                                .name("org.springframework.beans.factory.annotation.Autowired")//
                                                                                .build()))//
                                                                .parameters(List.of(//
                                                                        Parameter.builder()//
                                                                                .name("unauthorizedEntryPoint")//
                                                                                .type("UnauthorizedEntryPoint")//
                                                                                .build(),//
                                                                        Parameter.builder()//
                                                                                .name("jwtAuthenticationFilter")//
                                                                                .type("website.smoothy.".concat(name.toLowerCase()).concat("security.jwt.JwtAuthenticationFilter"))//
                                                                                .build()))//
                                                                .statements(List.of(//
                                                                        JavaAssignStatement.builder()//
                                                                                .variable("this.unauthorizedEntryPoint")//
                                                                                .expression(JavaVariableAssigningExpression.builder()//
                                                                                        .variable("unauthorizedEntryPoint")//
                                                                                        .build())//
                                                                                .build(),//
                                                                        JavaAssignStatement.builder()//
                                                                                .variable("this.jwtAuthenticationFilter")//
                                                                                .expression(JavaVariableAssigningExpression.builder()//
                                                                                        .variable("jwtAuthenticationFilter")//
                                                                                        .build())//
                                                                                .build()))//
                                                                .build(),//
                                                        JavaMethodDeclaration.builder()//
                                                                .name("configure")//
                                                                .returnType("void")//
                                                                .modifiers(Modifier.PUBLIC)//
                                                                .isThrows(true)//
                                                                .exceptions(List.of(//
                                                                        "Exception"))//
                                                                .annotations(List.of(//
                                                                        Annotation.builder()//
                                                                                .name("Override")//
                                                                                .build()))//
                                                                .parameters(List.of(//
                                                                        Parameter.builder()//
                                                                                .name("auth")//
                                                                                .type("org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder")//
                                                                                .build()))//
                                                                .statements(List.of(//
                                                                        JavaExpressionStatement.builder()
                                                                                .expression(JavaMethodInvocationExpression.builder()//
                                                                                        .target("auth")//
                                                                                        .invokes(List.of(//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("userDetailsService")//
                                                                                                        .arguments(List.of(//
                                                                                                                "userDetailsService"))//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("passwordEncoder")//
                                                                                                        .arguments(List.of(//
                                                                                                                "encoder()"))//
                                                                                                        .build()))//
                                                                                        .build())//
                                                                                .build()))//
                                                                .build(),//
                                                        JavaMethodDeclaration.builder()//
                                                                .name("configure")//
                                                                .returnType("void")//
                                                                .modifiers(Modifier.PROTECTED)//
                                                                .isThrows(true)//
                                                                .exceptions(List.of(//
                                                                        "Exception"))//
                                                                .annotations(List.of(//
                                                                        Annotation.builder()//
                                                                                .name("Override")//
                                                                                .build()))//
                                                                .parameters(List.of(//
                                                                        Parameter.builder()//
                                                                                .name("http")//
                                                                                .type("org.springframework.security.config.annotation.web.builders.HttpSecurity")//
                                                                                .build()))//
                                                                .statements(List.of(//
                                                                        JavaExpressionStatement.builder()
                                                                                .expression(JavaMethodInvocationExpression.builder()//
                                                                                        .target("http")//
                                                                                        .invokes(List.of(//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("csrf")//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("disable")//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("authorizeRequests")//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("antMatchers")//
                                                                                                        .arguments(List.of(//
                                                                                                                "\"/favicon.ico\"", "\"/error\"", "\"/authenticate\"", "\"/signup\"", "\"/api/**\""))//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("permitAll")//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("antMatchers")//
                                                                                                        .arguments(List.of(//
                                                                                                                "\"/admin/**\""))//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("hasAnyAuthority")//
                                                                                                        .arguments(List.of(//
                                                                                                                "\"ADMIN\""))//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("antMatchers")//
                                                                                                        .arguments(List.of(//
                                                                                                                "\"/user/**\""))//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("hasAnyAuthority")//
                                                                                                        .arguments(List.of(//
                                                                                                                "\"USER\""))//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("anyRequest")//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("authenticated")//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("and")//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("sessionManagement")//
                                                                                                        .build(),//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("sessionCreationPolicy")//
                                                                                                        .arguments(List.of(//
                                                                                                                "org.springframework.security.config.http.SessionCreationPolicy.STATELESS"))//
                                                                                                        .build()))//
                                                                                        .build())//
                                                                                .build(),//
                                                                        JavaExpressionStatement.builder()//
                                                                                .expression(JavaMethodInvocationExpression.builder()//
                                                                                        .target("http")//
                                                                                        .invokes(List.of(//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("addFilterBefore")//
                                                                                                        .arguments(List.of(//
                                                                                                                "jwtAuthenticationFilter", "org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class"))//
                                                                                                        .build()))//
                                                                                        .build())//
                                                                                .build()))//
                                                                .build(),//
                                                        JavaMethodDeclaration.builder()
                                                                .name("encoder")//
                                                                .returnType("org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder")//
                                                                .modifiers(Modifier.PUBLIC)//
                                                                .annotations(List.of(//
                                                                        Annotation.builder()//
                                                                                .name("org.springframework.context.annotation.Bean")//
                                                                                .build()))//
                                                                .statements(List.of(//
                                                                        JavaReturnStatement.builder()//
                                                                                .expression(JavaObjectNewInstanceExpression.builder()//
                                                                                        .name("org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder")//
                                                                                        .build())//
                                                                                .build()))//
                                                                .build(),//
                                                        JavaMethodDeclaration.builder()
                                                                .name("authenticationManagerBean")//
                                                                .returnType("org.springframework.security.authentication.AuthenticationManager")//
                                                                .modifiers(Modifier.PUBLIC)//
                                                                .isThrows(true)//
                                                                .exceptions(List.of(//
                                                                        "Exception"))//
                                                                .annotations(List.of(//
                                                                        Annotation.builder()//
                                                                                .name("Override")//
                                                                                .build(),//
                                                                        Annotation.builder()//
                                                                                .name("org.springframework.context.annotation.Bean")//
                                                                                .build()))//
                                                                .statements(List.of(//
                                                                        JavaReturnStatement.builder()//
                                                                                .expression(JavaMethodInvocationExpression.builder()//
                                                                                        .target("super")//
                                                                                        .invokes(List.of(//
                                                                                                JavaMethodInvocationExpression.MethodInvoke.builder()//
                                                                                                        .method("authenticationManagerBean")//
                                                                                                        .build()))//
                                                                                        .build())//
                                                                                .build()))//
                                                                .build()))//
                                                .build()))//
                        .build()
        );
    }

}
