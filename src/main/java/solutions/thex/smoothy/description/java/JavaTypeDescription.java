package solutions.thex.smoothy.description.java;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solutions.thex.smoothy.description.ObjectType;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JavaTypeDescription {

    private ObjectType type;
    private String name;
    private String extendedClassName;
    private String implementedClassName;
    @Builder.Default
    private List<String> modifiers = new LinkedList<>();
    @Builder.Default
    private List<AnnotationDescription> annotations = new LinkedList<>();
    @Builder.Default
    private List<JavaFieldDescription> fields = new LinkedList<>();
    @Builder.Default
    private List<MethodDescription> methods = new LinkedList<>();

}
