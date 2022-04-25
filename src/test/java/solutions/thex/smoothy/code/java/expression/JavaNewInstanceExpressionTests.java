package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.JavaOperand;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaNewInstanceExpressionTests {

    private JavaNewInstanceExpression javaNewInstanceExpression;

    @Test
    void javaNewInstanceExpression_should_render_correct_expression() {
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
                        JavaVariableExpression.builder()//
                                .variable("someVariable")//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Test(someVariable)", expression);
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
                                        JavaMethodInvoke.builder()//
                                                .method("getValue")//
                                                .arguments(List.of(//
                                                        JavaVariableExpression.builder()//
                                                                .variable("someVariable")//
                                                                .build()))//
                                                .build()))//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Test(someObject.getValue(someVariable))", expression);
    }

    @Test
    void javaNewInstanceExpression_with_multiple_argument_should_render_correct_expression() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("Test")//
                .arguments(List.of(//
                        JavaValueExpression.builder()//
                                .value("1")//
                                .type(Integer.class)
                                .build(),//
                        JavaVariableExpression.builder()//
                                .variable("true")//
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
                        JavaMethodInvoke.builder()//
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
                        JavaMethodInvoke.builder()//
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
                        JavaMethodInvoke.builder()//
                                .method("after")//
                                .build(),//
                        JavaMethodInvoke.builder()//
                                .method("before")//
                                .build()))//
                .build();

        // When
        String expression = javaNewInstanceExpression.render();

        // Then
        assertEquals("new Date().after().before()", expression);
    }

    @Test
    void javaNewInstanceExpression_should_return_correct_imports() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("java.util.Date")//
                .build();

        // When
        Set<String> imports = javaNewInstanceExpression.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("java.util.Date", imports.iterator().next());
    }

    @Test
    void javaNewInstanceExpression_with_invokes_and_nested_javaNewInstanceExpression_should_eliminate_same_imports_and_return_correct_imports() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("java.util.Date")//
                .invokes(List.of(//
                        JavaMethodInvoke.builder()//
                                .method("after")//
                                .arguments(List.of(//
                                        JavaNewInstanceExpression.builder()//
                                                .name("java.util.Date")//
                                                .build()))//
                                .build()))//
                .build();

        // When
        Set<String> imports = javaNewInstanceExpression.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("java.util.Date", imports.iterator().next());
    }

    @Test
    void javaNewInstanceExpression_should_with_argument_return_correct_imports() {
        // Given
        javaNewInstanceExpression = JavaNewInstanceExpression.builder()//
                .name("java.util.Date")//
                .arguments(List.of(//
                        JavaValueExpression.builder()//
                                .value("org.springframework.boot.SpringApplication")//
                                .type(Class.class)//
                                .build()))//
                .build();

        // When
        Set<String> imports = javaNewInstanceExpression.imports();
        Iterator<String> iterator = imports.iterator();

        // Then
        assertEquals(2, imports.size());
        assertEquals("java.util.Date", iterator.next());
        assertEquals("org.springframework.boot.SpringApplication", iterator.next());
    }

}
