package solutions.thex.smoothy.code.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.expression.JavaMethodInvocationExpression;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaExpressionStatementTests {

    private JavaExpressionStatement javaExpressionStatement;

    @Test
    void javaExpressionStatement_should_render_correct_statement() {
        // Given
        javaExpressionStatement = JavaExpressionStatement.builder()//
                .expression(JavaMethodInvocationExpression.builder()//
                        .target("variable")//
                        .invokes(List.of(//
                                JavaMethodInvoke.builder()//
                                        .method("method")//
                                        .build()))//
                        .build())//
                .build();

        // When
        String statement = javaExpressionStatement.render();

        // Then
        assertEquals("variable.method();", statement);
    }

}
