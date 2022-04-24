package solutions.thex.smoothy.code.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.JavaVariableExpression;

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

}
