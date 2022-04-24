package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaOperand;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaVariableExpressionTests {

    private JavaVariableExpression javaPlainValueExpression;

    @Test
    void javaPlainValueExpression_should_render_correct_expression() {
        // Given
        javaPlainValueExpression = JavaVariableExpression.builder()//
                .variable("someVariable")//
                .build();

        // When
        String expression = javaPlainValueExpression.render();

        // Then
        assertEquals("someVariable", expression);
    }

    @Test
    void javaPlainValueExpression_with_operand_should_render_correct_expression() {
        // Given
        javaPlainValueExpression = JavaVariableExpression.builder()//
                .variable("someVariable")//
                .operand(JavaOperand.PLUS)//
                .build();

        // When
        String expression = javaPlainValueExpression.render();

        // Then
        assertEquals("someVariable + ", expression);
    }

}
