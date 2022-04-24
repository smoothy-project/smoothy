package solutions.thex.smoothy.code.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaModifier;
import solutions.thex.smoothy.code.java.expression.JavaValueExpression;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaDeclarationStatementTests {

    private JavaDeclarationStatement javaDeclarationStatement;

    @Test
    void javaDeclarationStatement_not_initialized_should_render_correct_statement() {
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
    void javaDeclarationStatement_with_modifier_should_render_correct_statement() {
        // Given
        javaDeclarationStatement = JavaDeclarationStatement.builder()//
                .modifiers(JavaModifier.builder()//
                        .modifiers(Modifier.FINAL)//
                        .build())//
                .name("variable")//
                .type("String")//
                .build();

        // When
        String statement = javaDeclarationStatement.render();

        // Then
        assertEquals("final String variable;", statement);
    }

    @Test
    void javaDeclarationStatement_is_initialized_should_render_correct_statement() {
        // Given
        javaDeclarationStatement = JavaDeclarationStatement.builder()//
                .name("variable")//
                .type("String")//
                .initialized(true)//
                .expression(JavaValueExpression.builder()//
                        .value("value")//
                        .type(String.class)//
                        .build())//
                .build();

        // When
        String statement = javaDeclarationStatement.render();

        // Then
        assertEquals("String variable = \"value\";", statement);
    }

}
