package solutions.thex.smoothy.generate.spring.code.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.core.declaration.java.declaration.JavaFieldDeclaration;
import solutions.thex.smoothy.core.declaration.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.core.declaration.java.source.JavaTypeDeclaration;
import solutions.thex.smoothy.core.declaration.java.util.JavaAnnotation;
import solutions.thex.smoothy.core.declaration.java.util.JavaType;
import solutions.thex.smoothy.core.description.java.type.JavaRepositoryTypeDescription;
import solutions.thex.smoothy.util.StringFormatter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class SpringRepositoryGenerator extends SpringTypeGenerator {

    private String projectName;

    @Override
    public JavaTypeDeclaration generateTypeDeclaration() {
        return JavaTypeDeclaration.builder()//
                .type(JavaType.INTERFACE)//
                .name(StringFormatter.toPascalCase(type.getName(), "Repository"))//
                .extendedClassName(((JavaRepositoryTypeDescription) type).getProvider().getPackageName())//
                .implementedClassName(type.getImplementedClassName())//
                .tailGenericTypes(generateTailGenericTypes())//
                .modifiers(generateModifiers(type.getModifiers()))//
                .annotations(generateAnnotations())//
                .fieldDeclarations(generateFields())//
                .methodDeclarations(generateMethods())//
                .build();
    }

    private List<String> generateTailGenericTypes() {
        if (type.getTailGenericTypes().isEmpty())
            return List.of("website.smoothy."//
                    .concat(projectName.toLowerCase())//
                    .concat(".dao.repository.").concat(StringFormatter.toPascalCase(type.getName())), "String");
        return type.getTailGenericTypes();
    }

    @Override
    public List<JavaAnnotation> generateAnnotations() {
        List<JavaAnnotation> annotations = new ArrayList<>();
        annotations.add(JavaAnnotation.builder()//
                .name("org.springframework.stereotype.Repository")//
                .build());
        if (((JavaRepositoryTypeDescription) type).isCrossOrigin()) {
            annotations.add(JavaAnnotation.builder()//
                    .name("org.springframework.web.bind.annotation.CrossOrigin")//
                    .build());
        }
        if (((JavaRepositoryTypeDescription) type).isDataRestRepository()) {
            annotations.add(JavaAnnotation.builder()//
                    .name("org.springframework.data.rest.core.annotation.RepositoryRestResource")//
                    .attributes(List.of(//
                            JavaAnnotation.Attribute.builder()//
                                    .name("collectionResourceRel")//
                                    .type(String.class)//
                                    .values(List.of("user"))//
                                    .build(),//
                            JavaAnnotation.Attribute.builder()//
                                    .name("path")//
                                    .type(String.class)//
                                    .values(List.of("user"))//
                                    .build()))//
                    .build());
        }
        return annotations;
    }

    @Override
    public List<JavaFieldDeclaration> generateFields() {
        return new ArrayList<>();
    }

    @Override
    public List<JavaMethodDeclaration> generateMethods() {
        //TODO queries methods
        return new ArrayList<>();
    }
}
