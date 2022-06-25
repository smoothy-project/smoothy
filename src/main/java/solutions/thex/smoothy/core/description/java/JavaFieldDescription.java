package solutions.thex.smoothy.core.description.java;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.LinkedList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class JavaFieldDescription {

    private String name;
    private String type;
    private Object value;
    @Builder.Default
    private List<String> genericTypes = new LinkedList<>();
    @Builder.Default
    private List<String> modifiers = new LinkedList<>();

}
