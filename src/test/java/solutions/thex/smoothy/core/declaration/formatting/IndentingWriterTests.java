package solutions.thex.smoothy.core.declaration.formatting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class IndentingWriterTests {

    private IndentingWriter writer;

    @BeforeEach
    void setup() {
        writer = new IndentingWriter(new SimpleIndentStrategy("    "));
    }

    @Test
    void indentWriter_should_render_indents_correct() {
        // When
        writer.println("has not indent");
        writer.indented(() -> {
            writer.print("has indent");
        });

        // Then
        assertEquals("has not indent"//
                + System.lineSeparator()//
                + "    has indent", writer.render());
    }

    @Test
    void indentWriter_should_render_nested_indents_correct() {
        // Given
        IndentingWriter nestedWriter = new IndentingWriter(new SimpleIndentStrategy("    "));

        // When
        nestedWriter.println("has indent");
        nestedWriter.indented(() -> {
            nestedWriter.print("has nested indent");
        });
        writer.println("has not indent");
        writer.indented(() -> {
            writer.print(nestedWriter.render());
        });

        // Then
        assertEquals("has not indent"//
                + System.lineSeparator()//
                + "    has indent"//
                + System.lineSeparator()//
                + "        has nested indent", writer.render());
    }

}
