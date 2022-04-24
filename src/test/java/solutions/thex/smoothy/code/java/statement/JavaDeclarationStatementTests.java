package solutions.thex.smoothy.code.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.JavaPlainValueExpression;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaDeclarationStatementTests {

    private JavaDeclarationStatement javaDeclarationStatement;

    @Test
    void javaAssignStatement_not_initialized_should_render_correct_statement() {
        // Given
        javaDeclarationStatement = JavaDeclarationStatement.builder()//
                .name("variable")//
                .type("String")//
                .build();

        // When
        String statement = javaDeclarationStatement.render();

        // Then
        assertEquals("String variable;", statement);
    }

    @Test
    void javaAssignStatement_with_modifier_should_render_correct_statement() {
        // Given
        javaDeclarationStatement = JavaDeclarationStatement.builder()//
                .modifiers(Modifier.FINAL)//
                .name("variable")//
                .type("String")//
                .build();

        // When
        String statement = javaDeclarationStatement.render();

        // Then
        assertEquals("final String variable;", statement);
    }

    @Test
    void javaAssignStatement_is_initialized_should_render_correct_statement() {
        // Given
        javaDeclarationStatement = JavaDeclarationStatement.builder()//
                .name("variable")//
                .type("String")//
                .initialized(true)//
                .expression(JavaPlainValueExpression.builder()//
                        .value("\"value\"")//
                        .build())//
                .build();

        // When
        String statement = javaDeclarationStatement.render();

        // Then
        assertEquals("String variable = \"value\";", statement);
    }

}
