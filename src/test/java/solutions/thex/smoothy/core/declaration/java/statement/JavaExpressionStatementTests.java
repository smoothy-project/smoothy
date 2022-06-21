package solutions.thex.smoothy.core.declaration.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.core.declaration.java.expression.util.JavaMethodInvoke;
import solutions.thex.smoothy.core.declaration.java.expression.JavaMethodInvocationExpression;
import solutions.thex.smoothy.core.declaration.java.expression.JavaValueExpression;

import java.util.List;
import java.util.Set;

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

    @Test
    void javaExpressionStatement_should_return_correct_imports() {
        // Given
        javaExpressionStatement = JavaExpressionStatement.builder()//
                .expression(JavaValueExpression.builder()//
                        .value("org.springframework.boot.SpringApplication")//
                        .type(Class.class)//
                        .build())//
                .build();

        // When
        Set<String> imports = javaExpressionStatement.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.springframework.boot.SpringApplication", imports.iterator().next());
    }

}
