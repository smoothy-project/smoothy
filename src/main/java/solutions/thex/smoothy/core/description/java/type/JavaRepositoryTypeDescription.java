package solutions.thex.smoothy.core.description.java.type;

import lombok.*;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.core.description.java.JavaTypeDescription;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class JavaRepositoryTypeDescription extends JavaTypeDescription {

    @Builder.Default
    private boolean dataRestRepository = false;
    @Builder.Default
    private boolean crossOrigin = false;
    private JavaRepositoryProvider provider;

    public enum JavaRepositoryProvider {

        JPA("org.springframework.data.jpa.repository.JpaRepository");

        private final String packageName;

        JavaRepositoryProvider(String packageName) {
            this.packageName = packageName;
        }

        @Override
        public String toString() {
            return this.name();
        }

        public String getPackageName() {
            return packageName;
        }

    }

}
