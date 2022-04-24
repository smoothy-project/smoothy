package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaOperand;
import solutions.thex.smoothy.code.java.MethodInvoke;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaNewInstanceExpressionTests {

    private JavaNewInstanceExpression javaNewInstanceExpression;

    @Test
    void javaNewInstanceExpression_with_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("Test")//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Test()", expression);
    }

    @Test
    void javaNewInstanceExpression_with_one_argument_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("Test")//
                .arguments(List.of(//
                        JavaPlainValueExpression.builder()//
                                .value("1")//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Test(1)", expression);
    }

    @Test
    void javaNewInstanceExpression_with_nested_javaNewInstanceExpression_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("Test")//
                .arguments(List.of(//
                        JavaNewInstanceExpression.builder()//
                                .name("Test")//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Test(new Test())", expression);
    }

    @Test
    void javaNewInstanceExpression_with_nested_argument_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("Test")//
                .arguments(List.of(//
                        JavaMethodInvocationExpression.builder()//
                                .target("someObject")//
                                .invokes(List.of(//
                                        MethodInvoke.builder()//
                                                .method("getValue")//
                                                .arguments(List.of(//
                                                        JavaPlainValueExpression.builder()//
                                                                .value("1")//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Test(someObject.getValue(1))", expression);
    }

    @Test
    void javaNewInstanceExpression_with_multiple_argument_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("Test")//
                .arguments(List.of(//
                        JavaPlainValueExpression.builder()//
                                .value("1")//
                                .build(),//
                        JavaPlainValueExpression.builder()//
                                .value("true")//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Test(1, true)", expression);
    }

    @Test
    void javaNewInstanceExpression_with_invokes_and_nested_javaNewInstanceExpression_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("java.util.Date")//
                .invokes(List.of(//
                        MethodInvoke.builder()//
                                .method("after")//
                                .arguments(List.of(//
                                        JavaNewInstanceExpression.builder()//
                                                .name("java.util.Date")//
                                                .build()))//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Date().after(new Date())", expression);
    }

    @Test
    void javaNewInstanceExpression_with_operand_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("java.util.Date")//
                .operand(JavaOperand.AND)
                .invokes(List.of(//
                        MethodInvoke.builder()//
                                .method("after")//
                                .arguments(List.of(//
                                        JavaNewInstanceExpression.builder()//
                                                .name("java.util.Date")//
                                                .build()))//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Date().after(new Date()) && ", expression);
    }

    @Test
    void javaNewInstanceExpression_with_multiple_invokes_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("java.util.Date")//
                .invokes(List.of(//
                        MethodInvoke.builder()//
                                .method("after")//
                                .build(),//
                        MethodInvoke.builder()//
                                .method("before")//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Date().after().before()", expression);
    }

}
