package solutions.thex.smoothy.code.java;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.JavaPlainValueExpression;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MethodInvokeTests {

    private MethodInvoke methodInvoke;

    @Test
    void methodInvoke_should_render_correct_expression() {
        // Given
        methodInvoke = MethodInvoke.builder()//
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
        methodInvoke = MethodInvoke.builder()//
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
        methodInvoke = MethodInvoke.builder()//
                .method("method")//
                .arguments(List.of(//
                        JavaPlainValueExpression.builder()//
                                .value("value")//
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
        methodInvoke = MethodInvoke.builder()//
                .method("method")//
                .arguments(List.of(//
                        JavaPlainValueExpression.builder()//
                                .value("value1")//
                                .build(),//
                        JavaPlainValueExpression.builder()//
                                .value("value2")//
                                .build()))//
                .build();
        // When
        String expression = methodInvoke.render();

        // Then
        assertEquals(".method(value1, value2)", expression);
    }

}
