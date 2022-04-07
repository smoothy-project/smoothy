package solutions.thex.smoothy.code.java;

import java.util.List;

/**
 * A representation of something that can be annotated.
 */
public interface Annotatable {

    void annotate(Annotation annotation);

    List<Annotation> getAnnotations();

}
