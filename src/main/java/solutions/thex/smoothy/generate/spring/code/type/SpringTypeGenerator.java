package solutions.thex.smoothy.generate.spring.code.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.core.declaration.java.declaration.JavaFieldDeclaration;
import solutions.thex.smoothy.core.declaration.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.core.declaration.java.source.JavaTypeDeclaration;
import solutions.thex.smoothy.core.declaration.java.util.JavaAnnotation;
import solutions.thex.smoothy.core.declaration.java.util.JavaModifier;
import solutions.thex.smoothy.core.description.java.JavaTypeDescription;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class SpringTypeGenerator {

    protected JavaTypeDescription type;

    abstract public List<JavaAnnotation> generateAnnotations();

    abstract public List<JavaFieldDeclaration> generateFields();

    abstract public List<JavaMethodDeclaration> generateMethods();

    abstract public JavaTypeDeclaration generateTypeDeclaration();

    protected JavaModifier generateModifiers(List<String> modifiers) {
        return JavaModifier.builder()//
                .type(JavaModifier.TYPE_MODIFIERS)//
                .modifiers(modifiers.stream()//
                        .map(this::decodeModifier)//
                        .reduce(0, (a, b) -> a | b))//
                .build();
    }

    private int decodeModifier(String modifier) {
        return switch (modifier) {
            case "public" -> 0x00000001;
            case "private" -> 0x00000002;
            default -> 0;
        };
    }

}
