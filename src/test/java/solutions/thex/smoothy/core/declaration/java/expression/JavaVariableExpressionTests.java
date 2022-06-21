package solutions.thex.smoothy.core.declaration.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.core.declaration.java.util.JavaOperand;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaVariableExpressionTests {

    private JavaVariableExpression javaVariableExpression;

    @Test
    void javaVariableExpression_should_render_correct_expression() {
        // Given
        javaVariableExpression = JavaVariableExpression.builder()//
                .variable("someVariable")//
                .build();

        // When
        String expression = javaVariableExpression.render();

        // Then
        assertEquals("someVariable", expression);
    }

    @Test
    void javaVariableExpression_with_operand_should_render_correct_expression() {
        // Given
        javaVariableExpression = JavaVariableExpression.builder()//
                .variable("someVariable")//
                .operand(JavaOperand.PLUS)//
                .build();

        // When
        String expression = javaVariableExpression.render();

        // Then
        assertEquals("someVariable + ", expression);
    }

    @Test
    void javaVariableExpression_return_empty_imports_no_matter_what() {
        // Given
        javaVariableExpression = JavaVariableExpression.builder()//
                .variable("someVariable")//
                .build();

        // When
        Set<String> imports = javaVariableExpression.imports();

        // Then
        assertEquals(0, imports.size());
    }

}
