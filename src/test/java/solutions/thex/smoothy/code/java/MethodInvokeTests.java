package solutions.thex.smoothy.code.java;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.JavaVariableExpression;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MethodInvokeTests {

    private JavaMethodInvoke methodInvoke;

    @Test
    void methodInvoke_should_render_correct_expression() {
        // Given
        methodInvoke = JavaMethodInvoke.builder()//
                .method("method")//
                .build();
        // When
        String expression = methodInvoke.render();

        // Then
        assertEquals(".method()", expression);
    }

    @Test
    void methodInvoke_with_breakLine_should_render_correct_expression() {
        // Given
        methodInvoke = JavaMethodInvoke.builder()//
                .method("method")//
                .breakLine(true)//
                .build();
        // When
        String expression = methodInvoke.render();

        // Then
        assertEquals(".method()//\n        ", expression);//TODO: must read indent
    }

    @Test
    void methodInvoke_with_one_argument_should_render_correct_expression() {
        // Given
        methodInvoke = JavaMethodInvoke.builder()//
                .method("method")//
                .arguments(List.of(//
                        JavaVariableExpression.builder()//
                                .variable("value")//
                                .build()))//
                .build();
        // When
        String expression = methodInvoke.render();

        // Then
        assertEquals(".method(value)", expression);
    }

    @Test
    void methodInvoke_with_multiple_arguments_should_render_correct_expression() {
        // Given
        methodInvoke = JavaMethodInvoke.builder()//
                .method("method")//
                .arguments(List.of(//
                        JavaVariableExpression.builder()//
                                .variable("value1")//
                                .build(),//
                        JavaVariableExpression.builder()//
                                .variable("value2")//
                                .build()))//
                .build();
        // When
        String expression = methodInvoke.render();

        // Then
        assertEquals(".method(value1, value2)", expression);
    }

}
