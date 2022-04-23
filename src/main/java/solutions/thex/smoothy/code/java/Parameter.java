package solutions.thex.smoothy.code.java;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * A parameter, typically of a method or function.
 */

@Builder
@Data
public class Parameter {

    @Builder.Default
    private final List<String> genericTypes = new LinkedList<>();
    private final String type;
    private final String name;

}
