package solutions.thex.smoothy.code.java;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AnnotationTests {

    private Annotation annotation;

    @Test
    void annotation_should_render_correctly() {
        // Given
        annotation = Annotation.builder()//
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
        annotation = Annotation.builder()//
                .name("Test")//
                .attributes(List.of(//
                        Annotation.Attribute.builder()//
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
        annotation = Annotation.builder()//
                .name("Test")//
                .attributes(List.of(//
                        Annotation.Attribute.builder()//
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
        annotation = Annotation.builder()//
                .name("Test")//
                .attributes(List.of(//
                        Annotation.Attribute.builder()//
                                .name("attr1")
                                .type(String.class)//
                                .values(List.of("test1"))//
                                .build(),//
                        Annotation.Attribute.builder()//
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

}
