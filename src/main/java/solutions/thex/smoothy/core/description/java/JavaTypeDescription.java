package solutions.thex.smoothy.core.description.java;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solutions.thex.smoothy.core.description.ObjectType;
import solutions.thex.smoothy.core.description.java.field.JavaDAOFieldDescription;

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
    private List<JavaAnnotationDescription> annotations = new LinkedList<>();
    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = JavaDAOFieldDescription.class, name = "DAO")}
    )
    private List<JavaFieldDescription> fields = new LinkedList<>();
    private List<JavaMethodDescription> methods = new LinkedList<>();

}
