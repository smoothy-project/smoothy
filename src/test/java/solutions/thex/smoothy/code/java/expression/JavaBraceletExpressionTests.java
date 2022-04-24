package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaOperand;
import solutions.thex.smoothy.code.java.MethodInvoke;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaBraceletExpressionTests {


    private JavaBraceletExpression javaBraceletExpression;

    @Test
    void javaBraceletExpression_with_one_expression_should_render_correct_expression() {
        // Given
        javaBraceletExpression = JavaBraceletExpression.builder()//
                .expressions(List.of(//
                        JavaPlainValueExpression.builder()//
                                .value("1") //
                                .build()//
                ))
                .build();

        // When
        String expression = javaBraceletExpression.render();

        // Then
        assertEquals("(1)", expression);
    }

    @Test
    void javaBraceletExpression_with_one_expression_without_bracelet_should_render_correct_expression() {
        // Given
        javaBraceletExpression = JavaBraceletExpression.builder()//
                .bracelet(false)//
                .expressions(List.of(//
                        JavaPlainValueExpression.builder()//
                                .value("1") //
                                .build()//
                ))
                .build();

        // When
        String expression = javaBraceletExpression.render();

        // Then
        assertEquals("1", expression);
    }

    @Test
    void javaBraceletExpression_with_multiple_expressions_should_render_correct_expression() {
        // Given
        javaBraceletExpression = JavaBraceletExpression.builder()//
                .expressions(List.of(//
                        JavaPlainValueExpression.builder()//
                                .operand(JavaOperand.PLUS)
                                .value("1") //
                                .build(),//
                        JavaPlainValueExpression.builder()//
                                .value("2") //
                                .build()//
                ))
                .build();

        // When
        String expression = javaBraceletExpression.render();

        // Then
        assertEquals("(1 + 2)", expression);
    }

    @Test
    void javaBraceletExpression_with_nested_bracelet_should_render_correct_expression() {
        // Given
        javaBraceletExpression = JavaBraceletExpression.builder()//
                .expressions(List.of(//
                        JavaPlainValueExpression.builder()//
                                .operand(JavaOperand.AND)
                                .value("true") //
                                .build(),//
                        JavaBraceletExpression.builder()//
                                .expressions(List.of(//
                                        JavaNewInstanceExpression.builder()//
                                                .name("java.util.Date")//
                                                .invokes(List.of(//
                                                        MethodInvoke.builder()//
                                                                .method("after")//
                                                                .arguments(List.of(//
                                                                        JavaNewInstanceExpression.builder()//
                                                                                .name("java.util.Date")//
                                                                                .build()))//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();

        // When
        String expression = javaBraceletExpression.render();

        // Then
        assertEquals("(true && (new Date().after(new Date())))", expression);
    }

}
