package solutions.thex.smoothy.core.description.java;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.core.description.java.field.JavaDAOFieldDescription;

import java.util.LinkedList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class JavaTypeDescription {

    private JavaObjectType type;
    private String name;
    private String extendedClassName;
    private String implementedClassName;
    @Builder.Default
    private List<String> tailGenericTypes = new LinkedList<>();
    @Builder.Default
    private List<String> modifiers = new LinkedList<>();
    private List<JavaAnnotationDescription> annotations = new LinkedList<>();
    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = JavaDAOFieldDescription.class, name = "DAO"),
            //@JsonSubTypes.Type(value = JavaRepositoryFieldDescription.class, name = "REPOSITORY")
    }
    )
    private List<JavaFieldDescription> fields = new LinkedList<>();
    private List<JavaMethodDescription> methods = new LinkedList<>();

}
