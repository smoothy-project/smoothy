package solutions.thex.smoothy.core.description.java.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.core.description.java.JavaTypeDescription;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class JavaRepositoryTypeDescription extends JavaTypeDescription {

    private boolean dataRestRepository;
    private boolean crossOrigin;
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
