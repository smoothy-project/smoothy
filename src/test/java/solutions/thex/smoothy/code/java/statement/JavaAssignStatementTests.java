package solutions.thex.smoothy.code.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.JavaValueExpression;
import solutions.thex.smoothy.code.java.expression.JavaVariableExpression;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaAssignStatementTests {

    private JavaAssignStatement javaAssignStatement;

    @Test
    void javaAssignStatement_should_render_correct_statement() {
        // Given
        javaAssignStatement = JavaAssignStatement.builder()//
                .variable("variable")
                .expression(JavaVariableExpression.builder()//
                        .variable("value")//
                        .build())//
                .build();

        // When
        String statement = javaAssignStatement.render();

        // Then
        assertEquals("variable = value;", statement);
    }

    @Test
    void javaAssignStatement_should_return_correct_imports() {
        // Given
        javaAssignStatement = JavaAssignStatement.builder()//
                .variable("variable")//
                .expression(JavaValueExpression.builder()//
                        .value("org.springframework.boot.SpringApplication")//
                        .type(Class.class)//
                        .build())//
                .build();

        // When
        Set<String> imports = javaAssignStatement.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", imports.iterator().next());
    }

}
