package solutions.thex.smoothy.code.java.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import solutions.thex.smoothy.code.java.util.JavaAnnotation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JavaAnnotationTests {

    private JavaAnnotation annotation;

    @Test
    void annotation_should_render_correctly() {
        // Given
        annotation = JavaAnnotation.builder()//
                .name("Test")//
                .build();

        // When
        String annot = annotation.render();

        // Then
        assertEquals("@Test\n", annot);
    }

    @Test
    void annotation_with_one_attribute_should_render_correctly() {
        // Given
        annotation = JavaAnnotation.builder()//
                .name("Test")//
                .attributes(List.of(//
                        JavaAnnotation.Attribute.builder()//
                                .type(String.class)//
                                .values(List.of("test"))//
                                .build()))//
                .build();

        // When
        String annot = annotation.render();

        // Then
        assertEquals("@Test(\"test\")\n", annot);
    }

    @Test
    void annotation_with_one_attribute_and_multiple_values_should_render_correctly() {
        // Given
        annotation = JavaAnnotation.builder()//
                .name("Test")//
                .attributes(List.of(//
                        JavaAnnotation.Attribute.builder()//
                                .type(String.class)//
                                .values(List.of("test1", "test2"))//
                                .build()))//
                .build();

        // When
        String annot = annotation.render();

        // Then
        assertEquals("@Test({ \"test1\", \"test2\" })\n", annot);
    }

    @Test
    void annotation_with_multiple_attributes_should_render_correctly() {
        // Given
        annotation = JavaAnnotation.builder()//
                .name("Test")//
                .attributes(List.of(//
                        JavaAnnotation.Attribute.builder()//
                                .name("attr1")
                                .type(String.class)//
                                .values(List.of("test1"))//
                                .build(),//
                        JavaAnnotation.Attribute.builder()//
                                .name("attr2")
                                .type(String.class)//
                                .values(List.of("test2"))//
                                .build()))//
                .build();

        // When
        String annot = annotation.render();

        // Then
        assertEquals("@Test(attr1 = \"test1\", attr2 = \"test2\")\n", annot);
    }

    @Test
    void annotation_should_return_correct_imports() {
        // Given
        annotation = JavaAnnotation.builder()//
                .name("org.junit.jupiter.api.TestInstance")//
                .build();

        // When
        Set<String> imports = annotation.imports();

        // Then
        assertEquals(1, imports.size());
        assertEquals("org.junit.jupiter.api.TestInstance", imports.iterator().next());
    }

    @Test
    void annotation_with_attribute_should_return_correct_imports() {
        // Given
        annotation = JavaAnnotation.builder()//
                .name("org.junit.jupiter.api.TestInstance")//
                .attributes(List.of(//
                        JavaAnnotation.Attribute.builder()//
                                .type(TestInstance.Lifecycle.class)//
                                .values(List.of("org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD"))//
                                .build()))//
                .build();

        // When
        Set<String> imports = annotation.imports();
        Iterator<String> iterator = imports.iterator();

        // Then
        assertEquals(2, imports.size());
        assertEquals("org.junit.jupiter.api.TestInstance", iterator.next());
        assertEquals("org.junit.jupiter.api.TestInstance.Lifecycle", iterator.next());
    }

}
