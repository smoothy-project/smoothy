package solutions.thex.smoothy.code.java.declaration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.util.JavaModifier;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaFieldDeclarationTests {

    private JavaFieldDeclaration javaFieldDeclaration;

    @Test
    void javaFieldDeclaration_not_initialized_should_render_correct_method() {
        // Given
        javaFieldDeclaration = JavaFieldDeclaration.builder()//
                .name("userDetailsService")//
                .type("org.springframework.security.core.userdetails.UserDetailsService")//
                .modifiers(JavaModifier.builder()//
                        .type(JavaModifier.FIELD_MODIFIERS)//
                        .modifiers(Modifier.PRIVATE)//
                        .build())//
                .build();

        // When
        String field = javaFieldDeclaration.render();

        // Then
        assertEquals("private UserDetailsService userDetailsService;\n", field);
    }

    @Test
    void javaFieldDeclaration_is_initialized_should_render_correct_field() {
        // Given
        javaFieldDeclaration = JavaFieldDeclaration.builder()//
                .name("userDetailsService")//
                .type("org.springframework.security.core.userdetails.UserDetailsService")//
                .modifiers(JavaModifier.builder()//
                        .type(JavaModifier.FIELD_MODIFIERS)//
                        .modifiers(Modifier.PRIVATE)//
                        .build())//
                .initialized(true)//
                .value("otherUserDetailsService")//
                .build();

        // When
        String field = javaFieldDeclaration.render();

        // Then
        assertEquals("private UserDetailsService userDetailsService = otherUserDetailsService;\n", field);
    }

    @Test
    void javaFieldDeclaration_is_initialized_type_Long_should_render_correct_field() {
        // Given
        javaFieldDeclaration = JavaFieldDeclaration.builder()//
                .name("number")//
                .type("Long")//
                .modifiers(JavaModifier.builder()//
                        .type(JavaModifier.FIELD_MODIFIERS)//
                        .modifiers(Modifier.PRIVATE)//
                        .build())//
                .initialized(true)//
                .value(12345678901L)//
                .build();

        // When
        String field = javaFieldDeclaration.render();

        // Then
        assertEquals("private Long number = 12345678901L;\n", field);
    }

    @Test
    void javaFieldDeclaration_is_initialized_type_Float_should_render_correct_field() {
        // Given
        javaFieldDeclaration = JavaFieldDeclaration.builder()//
                .name("number")//
                .type("Float")//
                .modifiers(JavaModifier.builder()//
                        .type(JavaModifier.FIELD_MODIFIERS)//
                        .modifiers(Modifier.PRIVATE)//
                        .build())//
                .initialized(true)//
                .value(1278901.1F)//
                .build();

        // When
        String field = javaFieldDeclaration.render();

        // Then
        assertEquals("private Float number = 1278901.1F;\n", field);
    }

    @Test
    void javaFieldDeclaration_is_initialized_type_Double_should_render_correct_field() {
        // Given
        javaFieldDeclaration = JavaFieldDeclaration.builder()//
                .name("number")//
                .type("Double")//
                .modifiers(JavaModifier.builder()//
                        .type(JavaModifier.FIELD_MODIFIERS)//
                        .modifiers(Modifier.PRIVATE)//
                        .build())//
                .initialized(true)//
                .value(12345678901.1D)//
                .build();

        // When
        String field = javaFieldDeclaration.render();

        // Then
        assertEquals("private Double number = 12345678901.1D;\n", field);
    }

}
