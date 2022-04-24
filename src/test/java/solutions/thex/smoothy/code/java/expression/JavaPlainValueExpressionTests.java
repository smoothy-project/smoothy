package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaOperand;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaPlainValueExpressionTests {

    private JavaPlainValueExpression javaPlainValueExpression;

    @Test
    void javaPlainValueExpression_should_render_correct_expression() {
        // Given
        javaPlainValueExpression = JavaPlainValueExpression.builder()//
                .value("1")//
                .build();

        // When
        String expression = javaPlainValueExpression.render();

        // Then
        assertEquals("1", expression);
    }

    @Test
    void javaPlainValueExpression_with_operand_should_render_correct_expression() {
        // Given
        javaPlainValueExpression = JavaPlainValueExpression.builder()//
                .value("1")//
                .operand(JavaOperand.PLUS)//
                .build();

        // When
        String expression = javaPlainValueExpression.render();

        // Then
        assertEquals("1 + ", expression);
    }

}
