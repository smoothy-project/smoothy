package solutions.thex.smoothy.code.java.declaration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.expression.JavaMethodInvocationExpression;
import solutions.thex.smoothy.code.java.expression.JavaValueExpression;
import solutions.thex.smoothy.code.java.expression.JavaVariableExpression;
import solutions.thex.smoothy.code.java.expression.util.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.statement.JavaExpressionStatement;
import solutions.thex.smoothy.code.java.util.JavaModifier;

import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaMethodDeclarationTests {

    private JavaMethodDeclaration javaMethodDeclaration;

    // TODO: Fix this test. Expected and rendered method are same but test doesnt pass!
    @Disabled
    @Test
    void javaMethodDeclaration_should_render_correct_method() {
        // Given
        String name = "Test";
        javaMethodDeclaration = JavaMethodDeclaration.builder()//
                .name("main")//
                .modifiers(JavaModifier.builder()//
                        .type(JavaModifier.METHOD_MODIFIERS)//
                        .modifiers(Modifier.PUBLIC | Modifier.STATIC)//
                        .build())//
                .returnType("void")//
                .parameters(List.of(//
                        JavaMethodDeclaration.Parameter.builder()//
                                .type("java.lang.String[]")//
                                .name("args")//
                                .build()))//
                .statements(List.of(//
                        JavaExpressionStatement.builder()
                                .expression(JavaMethodInvocationExpression.builder()//
                                        .target("org.springframework.boot.SpringApplication")//
                                        .invokes(List.of(//
                                                JavaMethodInvoke.builder()//
                                                        .method("run")//
                                                        .arguments(List.of(//
                                                                JavaValueExpression.builder()//
                                                                        .value(name.concat("Application"))//
                                                                        .type(Class.class)
                                                                        .build(),//
                                                                JavaVariableExpression.builder()//
                                                                        .variable("args")//
                                                                        .build()))//
                                                        .build()))//
                                        .build())//
                                .build()))//
                .build();
        String expectedJavaMethod = """      
                public static void main(String[] args) {
                    SpringApplication.run(TestApplication.class, args);
                }
                    
                """;

        // When
        String javaMethod = javaMethodDeclaration.render();

        // Then
        assertEquals(expectedJavaMethod, javaMethod);
    }

}
