package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaOperand;
import solutions.thex.smoothy.code.java.JavaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JavaValueExpressionTests {

    private JavaValueExpression javaValueExpression;

    @Test
    void javaValueExpression_with_type_class_should_render_correct_expression() {
        // Given
        javaValueExpression = JavaValueExpression.builder()//
                .value("SmoothyApplication")//
                .type(Class.class)//
                .build();

        // When
        String expression = javaValueExpression.render();

        // Then
        assertEquals("SmoothyApplication.class", expression);
    }

    @Test
    void javaValueExpression_with_primitive_class_should_render_correct_expression() {
        // Given
        javaValueExpression = JavaValueExpression.builder()//
                .value("1")//
                .type(Integer.class)//
                .build();

        // When
        String expression = javaValueExpression.render();

        // Then
        assertEquals("1", expression);
    }

    @Test
    void javaValueExpression_with_operand_should_render_correct_expression() {
        // Given
        javaValueExpression = JavaValueExpression.builder()//
                .value("1")//
                .type(Integer.class)//
                .operand(JavaOperand.PLUS)
                .build();

        // When
        String expression = javaValueExpression.render();

        // Then
        assertEquals("1 + ", expression);
    }

    @Test
    void javaValueExpression_with_type_enum_should_render_correct_expression() {
        // Given
        javaValueExpression = JavaValueExpression.builder()//
                .value("JavaType.INTERFACE")//
                .type(JavaType.class)//
                .build();

        // When
        String expression = javaValueExpression.render();

        // Then
        assertEquals("JavaType.INTERFACE", expression);
    }

}
