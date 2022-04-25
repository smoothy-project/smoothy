package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaOperand;
import solutions.thex.smoothy.code.java.JavaType;

import java.util.Set;

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
                .value("solutions.thex.smoothy.code.java.JavaType.INTERFACE")//
                .type(JavaType.class)//
                .build();

        // When
        String expression = javaValueExpression.render();

        // Then
        assertEquals("JavaType.INTERFACE", expression);
    }

    @Test
    void javaValueExpression_with_type_class_should_return_correct_imports() {
        // Given
        javaValueExpression = JavaValueExpression.builder()//
                .value("org.springframework.boot.SpringApplication")//
                .type(Class.class)//
                .build();

        // When
        Set<String> imports = javaValueExpression.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", imports.iterator().next());
    }

    @Test
    void javaValueExpression_with_type_enum_should_return_correct_imports() {
        // Given
        javaValueExpression = JavaValueExpression.builder()//
                .value("solutions.thex.smoothy.code.java.JavaType.INTERFACE")//
                .type(JavaType.class)//
                .build();

        // When
        Set<String> imports = javaValueExpression.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("solutions.thex.smoothy.code.java.JavaType", imports.iterator().next());
    }

}
