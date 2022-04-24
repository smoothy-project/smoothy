package solutions.thex.smoothy.code.java.expression;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JavaDotClassExpressionTests {

    private JavaDotClassExpression javaDotClassExpression;

    @Test
    void javaDotClassExpression_should_render_correct_expression() {
        // Given
        javaDotClassExpression = JavaDotClassExpression.builder()//
                .name("SmoothyApplication")//
                .build();

        // When
        String expression = javaDotClassExpression.render();

        // Then
        assertEquals("SmoothyApplication.class", expression);
    }

}
