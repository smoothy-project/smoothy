package solutions.thex.smoothy.core.declaration.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.core.declaration.java.expression.util.JavaMethodInvoke;
import solutions.thex.smoothy.core.declaration.java.util.JavaOperand;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaMethodInvocationExpressionTests {

    private JavaMethodInvocationExpression javaMethodInvocationExpression;

    @Test
    void javaMethodInvocationExpression_with_one_methodInvoke_should_render_correct_expression() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("test")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("method")//
                                .build()))//
                .build();

        // When
        String expression = javaMethodInvocationExpression.render();

        // Then
        assertEquals("test.method()", expression);
    }

    @Test
    void javaMethodInvocationExpression_with_one_methodInvoke_and_operand_should_render_correct_expression() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("test")//
                .operand(JavaOperand.PLUS)
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("method")//
                                .build()))//
                .build();

        // When
        String expression = javaMethodInvocationExpression.render();

        // Then
        assertEquals("test.method() + ", expression);
    }

    @Test
    void javaMethodInvocationExpression_with_one_methodInvoke_and_argument_should_render_correct_expression() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("test")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("method")//
                                .arguments(List.of(
                                        JavaVariableExpression.builder()//
                                                .variable("arg1")//
                                                .build()))//
                                .build()))//
                .build();

        // When
        String expression = javaMethodInvocationExpression.render();

        // Then
        assertEquals("test.method(arg1)", expression);
    }

    @Test
    void javaMethodInvocationExpression_with_one_methodInvoke_multiple_arguments_should_render_correct_expression() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("test")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("method")//
                                .arguments(List.of(
                                        JavaVariableExpression.builder()//
                                                .variable("arg1")//
                                                .build(),//
                                        JavaVariableExpression.builder()//
                                                .variable("arg2")//
                                                .build()))//
                                .build()))//
                .build();

        // When
        String expression = javaMethodInvocationExpression.render();

        // Then
        assertEquals("test.method(arg1, arg2)", expression);
    }

    @Test
    void javaMethodInvocationExpression_with_nested_methodInvokes_should_render_correct_expression() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("test1")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("method1")//
                                .arguments(List.of(//
                                        JavaMethodInvocationExpression.builder()//
                                                .target("test2")//
                                                .invokes(List.of(//
                                                        JavaMethodInvoke.builder()//
                                                                .method("method2")//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();

        // When
        String expression = javaMethodInvocationExpression.render();

        // Then
        assertEquals("test1.method1(test2.method2())", expression);
    }

    @Test
    void javaMethodInvocationExpression_with_multiple_methodInvokes_should_render_correct_expression() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("test")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("method1")//
                                .build(),//
                        JavaMethodInvoke.builder()//
                                .method("method2")//
                                .build()))//
                .build();

        // When
        String expression = javaMethodInvocationExpression.render();

        // Then
        assertEquals("test.method1().method2()", expression);
    }

    @Test
    void javaMethodInvocationExpression_should_return_correct_imports() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("org.springframework.boot.SpringApplication")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("run")//
                                .build()))//
                .build();

        // When
        Set<String> imports = javaMethodInvocationExpression.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", imports.iterator().next());
    }

    @Test
    void javaMethodInvocationExpression_with_argument_should_eliminate_same_imports_and_return_correct_imports() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("org.springframework.boot.SpringApplication")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("run")//
                                .arguments(List.of(//
                                        JavaValueExpression.builder()//
                                                .value("org.springframework.boot.SpringApplication")//
                                                .type(Class.class)//
                                                .build()))//
                                .build()))//
                .build();

        // When
        Set<String> imports = javaMethodInvocationExpression.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", imports.iterator().next());
    }

    @Test
    void javaMethodInvocationExpression_with_argument_should_return_correct_imports() {
        // Given
        javaMethodInvocationExpression = JavaMethodInvocationExpression.builder()//
                .target("org.springframework.boot.SpringApplication")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("run")//
                                .arguments(List.of(//
                                        JavaValueExpression.builder()//
                                                .value("org.springframework.boot.autoconfigure.SpringBootApplication")//
                                                .type(Class.class)//
                                                .build()))//
                                .build()))//
                .build();

        // When
        Set<String> imports = javaMethodInvocationExpression.imports();
        Iterator<String> iterator = imports.iterator();

        // Then
        assertEquals(2, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", iterator.next());
        assertEquals("org.springframework.boot.autoconfigure.SpringBootApplication", iterator.next());
    }

}
