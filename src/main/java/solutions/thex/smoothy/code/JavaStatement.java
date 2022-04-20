package solutions.thex.smoothy.code;

import solutions.thex.smoothy.code.formatting.IndentingWriter;

/**
 * A statement in Java.
 */
public interface JavaStatement {

    void render(IndentingWriter writer);

}
