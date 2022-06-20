package solutions.thex.smoothy.generator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solutions.thex.smoothy.generator.spring.SpringBootApplicationDescription;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDescription {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,//
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,//
            property = "applicationType")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SpringBootApplicationDescription.class, name = "spring-boot")
    })
    private Application application;

}
