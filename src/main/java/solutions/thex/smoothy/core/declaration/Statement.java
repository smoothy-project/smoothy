package solutions.thex.smoothy.core.declaration;

import java.util.Set;

/**
 * A statement in Java.
 */
public interface Statement {

    String render();

    Set<String> imports();

}
