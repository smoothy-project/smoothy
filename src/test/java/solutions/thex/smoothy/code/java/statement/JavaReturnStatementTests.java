package solutions.thex.smoothy.code.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.JavaValueExpression;
import solutions.thex.smoothy.code.java.expression.JavaVariableExpression;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaReturnStatementTests {

    private JavaReturnStatement javaReturnStatement;

    @Test
    void javaReturnStatement_should_render_correct_statement() {
        // Given
        javaReturnStatement = JavaReturnStatement.builder()//
                .expression(JavaVariableExpression.builder()//
                        .variable("variable")
                        .build())//
                .build();

        // When
        String statement = javaReturnStatement.render();

        // Then
        assertEquals("return variable;", statement);
    }

    @Test
    void javaReturnStatement_should_return_correct_imports() {
        // Given
        javaReturnStatement = JavaReturnStatement.builder()//
                .expression(JavaValueExpression.builder()//
                        .value("org.springframework.boot.SpringApplication")//
                        .type(Class.class)//
                        .build())//
                .build();

        // When
        Set<String> imports = javaReturnStatement.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", imports.iterator().next());
    }

}
