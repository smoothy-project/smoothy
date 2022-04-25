package solutions.thex.smoothy.code.java.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.JavaModifier;
import solutions.thex.smoothy.code.java.expression.JavaNewInstanceExpression;
import solutions.thex.smoothy.code.java.expression.JavaValueExpression;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Set;

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
                        .type(JavaModifier.FIELD_MODIFIERS)
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

    @Test
    void javaDeclarationStatement_should_return_correct_imports() {
        // Given
        javaDeclarationStatement = JavaDeclarationStatement.builder()//
                .name("variable")//
                .type("java.util.Date")//
                .build();

        // When
        Set<String> imports = javaDeclarationStatement.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("java.util.Date", imports.iterator().next());
    }

    @Test
    void javaDeclarationStatement_is_initialized_should_eliminate_same_imports_and_return_correct_imports() {
        // Given
        javaDeclarationStatement = JavaDeclarationStatement.builder()//
                .name("variable")//
                .type("java.util.Date")//
                .initialized(true)//
                .expression(JavaNewInstanceExpression.builder()//
                        .name("java.util.Date")//
                        .build())//
                .build();

        // When
        Set<String> imports = javaDeclarationStatement.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("java.util.Date", imports.iterator().next());
    }

    @Test
    void javaDeclarationStatement_is_initialized_should_return_correct_imports() {
        // Given
        javaDeclarationStatement = JavaDeclarationStatement.builder()//
                .name("variable")//
                .type("package.Test1")//
                .initialized(true)//
                .expression(JavaValueExpression.builder()//
                        .value("package.Test2")//
                            .type(Class.class)//
                        .build())//
                .build();

        // When
        Set<String> imports = javaDeclarationStatement.imports();
        Iterator<String> iterator = imports.iterator();

        // Then
        assertEquals(2, imports.size());
        assertEquals("package.Test1", iterator.next());
        assertEquals("package.Test2", iterator.next());
    }

}
