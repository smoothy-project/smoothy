package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaOperand;
import solutions.thex.smoothy.code.java.JavaMethodInvoke;

import java.util.List;

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

}
