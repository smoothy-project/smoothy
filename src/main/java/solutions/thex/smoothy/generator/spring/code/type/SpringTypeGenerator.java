package solutions.thex.smoothy.generator.spring.code.type;

import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.java.declaration.JavaFieldDeclaration;
import solutions.thex.smoothy.code.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.java.source.JavaTypeDeclaration;
import solutions.thex.smoothy.code.java.util.JavaAnnotation;
import solutions.thex.smoothy.code.java.util.JavaModifier;
import solutions.thex.smoothy.code.java.util.JavaType;
import solutions.thex.smoothy.description.java.JavaTypeDescription;
import solutions.thex.smoothy.util.StringFormatter;

import java.util.List;

@SuperBuilder
public abstract class SpringTypeGenerator {

    protected JavaTypeDescription type;

    abstract public List<JavaAnnotation> generateAnnotations();

    abstract public List<JavaFieldDeclaration> generateFields();

    abstract public List<JavaMethodDeclaration> generateMethods();

    public JavaTypeDeclaration generateTypeDeclaration() {
        return JavaTypeDeclaration.builder()//
                .type(JavaType.CLASS)//
                .name(StringFormatter.toPascalCase(type.getName()))//
                .extendedClassName(type.getExtendedClassName())//
                .implementedClassName(type.getImplementedClassName())//
                .modifiers(generateModifiers(type.getModifiers()))//
                .annotations(generateAnnotations())//
                .fieldDeclarations(generateFields())//
                .methodDeclarations(generateMethods())//
                .build();
    }

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
