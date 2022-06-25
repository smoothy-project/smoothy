package solutions.thex.smoothy.core.description.java.type;

import lombok.*;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.core.description.java.JavaTypeDescription;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class JavaDAOTypeDescription extends JavaTypeDescription {

    @Builder.Default
    private boolean storeCreatedDate = false;
    @Builder.Default
    private boolean storeUpdatedDate = false;

}
