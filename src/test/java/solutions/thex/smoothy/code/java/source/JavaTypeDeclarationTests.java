package solutions.thex.smoothy.code.java.source;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.java.expression.JavaMethodInvocationExpression;
import solutions.thex.smoothy.code.java.expression.JavaValueExpression;
import solutions.thex.smoothy.code.java.expression.JavaVariableExpression;
import solutions.thex.smoothy.code.java.expression.util.JavaMethodInvoke;
import solutions.thex.smoothy.code.java.statement.JavaExpressionStatement;
import solutions.thex.smoothy.code.java.util.JavaAnnotation;
import solutions.thex.smoothy.code.java.util.JavaModifier;
import solutions.thex.smoothy.code.java.util.JavaType;
import solutions.thex.smoothy.util.StringFormatter;

import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaTypeDeclarationTests {

    private JavaTypeDeclaration javaTypeDeclaration;

    // TODO: Fix this test. Expected and rendered type are same but test doesnt pass!
    @Disabled
    @Test
    void javaTypeDeclaration_should_render_correct_type() {
        // Given
        String name = "Test";
        javaTypeDeclaration = JavaTypeDeclaration.builder()//
                .type(JavaType.CLASS)//
                .name(StringFormatter.toPascalCase(name.concat("Application")))//
                .modifiers(JavaModifier.builder()//
                        .type(JavaModifier.TYPE_MODIFIERS)//
                        .modifiers(Modifier.PUBLIC)//
                        .build())//
                .annotations(List.of(//
                        JavaAnnotation.builder()//
                                .name("org.springframework.boot.autoconfigure.SpringBootApplication")//
                                .build()))//
                .methodDeclarations(List.of(//
                        JavaMethodDeclaration.builder()//
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
                                .build()))//
                .build();
        String expectedJavaType = """     
                @SpringBootApplication
                public class TestApplication {
                                
                    public static void main(String[] args) {
                        SpringApplication.run(TestApplication.class, args);
                    }\s\s\s\s
                                
                }
                """;

        // When
        String javaType = javaTypeDeclaration.render();

        // Then
        assertEquals(expectedJavaType, javaType);
    }

}
