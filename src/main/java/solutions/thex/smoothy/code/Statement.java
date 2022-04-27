package solutions.thex.smoothy.code;

import java.util.Set;

/**
 * A statement in Java.
 */
public interface Statement {

    String render();

    Set<String> imports();

}
