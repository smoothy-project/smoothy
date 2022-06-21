package solutions.thex.smoothy.core.declaration.java.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaModifiersTests {

    private JavaModifier javaModifier;

    @Test
    void javaModifier_should_render_one_modifier_correctly() {
        // Given
        javaModifier = JavaModifier.builder()//
                .type(JavaModifier.FIELD_MODIFIERS)
                .modifiers(Modifier.PUBLIC)//
                .build();

        // When
        String modifiers = javaModifier.render();

        // Then
        assertEquals("public ", modifiers);
    }

    @Test
    void javaModifier_should_render_multiple_modifiers_correctly() {
        // Given
        javaModifier = JavaModifier.builder()//
                .type(JavaModifier.FIELD_MODIFIERS)
                .modifiers(Modifier.STATIC | Modifier.PUBLIC)//
                .build();

        // When
        String modifiers = javaModifier.render();

        // Then
        assertEquals("public static ", modifiers);
    }

}
