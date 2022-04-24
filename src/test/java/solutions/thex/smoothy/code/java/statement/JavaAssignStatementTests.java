package solutions.thex.smoothy.code.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.JavaPlainValueExpression;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaAssignStatementTests {

    private JavaAssignStatement javaAssignStatement;

    @Test
    void javaAssignStatement_should_render_correct_statement() {
        // Given
        javaAssignStatement = JavaAssignStatement.builder()//
                .variable("variable")
                .expression(JavaPlainValueExpression.builder()//
                        .value("value")//
                        .build())//
                .build();

        // When
        String statement = javaAssignStatement.render();

        // Then
        assertEquals("variable = value;", statement);
    }

}
