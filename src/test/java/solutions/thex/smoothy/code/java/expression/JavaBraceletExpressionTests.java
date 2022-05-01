package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.util.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.util.JavaOperand;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaBraceletExpressionTests {


    private JavaBraceletExpression javaBraceletExpression;

    @Test
    void javaBraceletExpression_with_one_expression_should_render_correct_expression() {
        // Given
        javaBraceletExpression = JavaBraceletExpression.builder()//
                .expressions(List.of(//
                        JavaValueExpression.builder()//
                                .value("1")//
                                .type(Integer.class)//
                                .build()))//
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
                        JavaValueExpression.builder()//
                                .value("1")//
                                .type(Integer.class)//
                                .build()))//
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
                        JavaValueExpression.builder()//
                                .value("1")//
                                .type(Integer.class)//
                                .operand(JavaOperand.PLUS)//
                                .build(),//
                        JavaValueExpression.builder()//
                                .value("2")//
                                .type(Integer.class)//
                                .build()))//
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
                        JavaValueExpression.builder()//
                                .value("true")//
                                .type(Boolean.class)//
                                .operand(JavaOperand.AND)//
                                .build(),//
                        JavaBraceletExpression.builder()//
                                .expressions(List.of(//
                                        JavaNewInstanceExpression.builder()//
                                                .name("java.util.Date")//
                                                .invokes(List.of(//
                                                        JavaMethodInvoke.builder()//
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

    @Test
    void javaBraceletExpression_should_return_correct_imports() {
        // Given
        javaBraceletExpression = JavaBraceletExpression.builder()//
                .expressions(List.of(//
                        JavaMethodInvocationExpression.builder()//
                                .target("org.springframework.boot.SpringApplication")//
                                .invokes(List.of(//
                                        JavaMethodInvoke.builder()//
                                                .method("run")//
                                                .build()))//
                                .build()))//
                .build();

        // When
        Set<String> imports = javaBraceletExpression.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", imports.iterator().next());
    }

    @Test
    void javaBraceletExpression_should_eliminate_same_imports_and_return_correct_imports() {
        // Given
        javaBraceletExpression = JavaBraceletExpression.builder()//
                .expressions(List.of(//
                        JavaMethodInvocationExpression.builder()//
                                .target("org.springframework.boot.SpringApplication")//
                                .invokes(List.of(//
                                        JavaMethodInvoke.builder()//
                                                .method("run")//
                                                .arguments(List.of(//
                                                        JavaValueExpression.builder()//
                                                                .value("org.springframework.boot.SpringApplication")//
                                                                .type(Class.class)//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();

        // When
        Set<String> imports = javaBraceletExpression.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", imports.iterator().next());
    }

}