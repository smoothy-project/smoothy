package solutions.thex.smoothy.core.description.java.field;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.core.description.java.JavaFieldDescription;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class JavaDAOFieldDescription extends JavaFieldDescription {

    private JavaFieldRelationDescription relation;
    @Builder.Default
    private boolean unique = false;
    @Builder.Default
    private boolean nullable = true;
    @Builder.Default
    private boolean updatable = true;
    @Builder.Default
    private boolean excludeFromJson = false;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class JavaFieldRelationDescription {

        public enum Relation {

            ONE_TO_ONE("OneToOne"),
            ONE_TO_MANY("OneToMany"),
            MANY_TO_ONE("ManyToOne"),
            MANY_TO_MANY("ManyToMany");

            private final String name;

            Relation(String name) {
                this.name = name;
            }

            @JsonValue
            @Override
            public String toString() {
                return name;
            }

        }

        private Relation type;
        private String mappedBy;
        @Builder.Default
        private boolean join = false;

    }

}
