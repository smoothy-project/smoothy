package solutions.thex.smoothy.code;

import solutions.thex.smoothy.code.formatting.IndentingWriter;

import java.util.Set;

/**
 * A Java expression.
 */
public interface Expression {

    String render();

    Set<String> imports();

}
