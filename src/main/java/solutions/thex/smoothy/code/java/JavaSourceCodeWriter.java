package solutions.thex.smoothy.code.java;

import solutions.thex.smoothy.code.java.formatting.IndentingWriter;
import solutions.thex.smoothy.code.java.formatting.IndentingWriterFactory;
import solutions.thex.smoothy.code.java.declaration.JavaFieldDeclaration;
import solutions.thex.smoothy.code.java.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.java.declaration.JavaTypeDeclaration;
import solutions.thex.smoothy.code.java.expression.JavaMethodInvocation;
import solutions.thex.smoothy.code.java.statement.JavaExpressionStatement;
import solutions.thex.smoothy.code.java.statement.JavaReturnStatement;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A SourceCodeWriter that writes SourceCode in Java.
 */
public class JavaSourceCodeWriter {

    private static final Map<Predicate<Integer>, String> TYPE_MODIFIERS;

    private static final Map<Predicate<Integer>, String> FIELD_MODIFIERS;

    private static final Map<Predicate<Integer>, String> METHOD_MODIFIERS;

    static {
        Map<Predicate<Integer>, String> typeModifiers = new LinkedHashMap<>();
        typeModifiers.put(Modifier::isPublic, "public");
        typeModifiers.put(Modifier::isProtected, "protected");
        typeModifiers.put(Modifier::isPrivate, "private");
        typeModifiers.put(Modifier::isAbstract, "abstract");
        typeModifiers.put(Modifier::isStatic, "static");
        typeModifiers.put(Modifier::isFinal, "final");
        typeModifiers.put(Modifier::isStrict, "strictfp");
        TYPE_MODIFIERS = typeModifiers;
        Map<Predicate<Integer>, String> fieldModifiers = new LinkedHashMap<>();
        fieldModifiers.put(Modifier::isPublic, "public");
        fieldModifiers.put(Modifier::isProtected, "protected");
        fieldModifiers.put(Modifier::isPrivate, "private");
        fieldModifiers.put(Modifier::isStatic, "static");
        fieldModifiers.put(Modifier::isFinal, "final");
        fieldModifiers.put(Modifier::isTransient, "transient");
        fieldModifiers.put(Modifier::isVolatile, "volatile");
        FIELD_MODIFIERS = fieldModifiers;
        Map<Predicate<Integer>, String> methodModifiers = new LinkedHashMap<>(typeModifiers);
        methodModifiers.put(Modifier::isSynchronized, "synchronized");
        methodModifiers.put(Modifier::isNative, "native");
        METHOD_MODIFIERS = methodModifiers;
    }

    private final IndentingWriterFactory indentingWriterFactory;

    public JavaSourceCodeWriter(IndentingWriterFactory indentingWriterFactory) {
        this.indentingWriterFactory = indentingWriterFactory;
    }

    public void writeTo(SourceStructure structure, JavaSourceCode sourceCode) throws IOException {
        for (JavaCompilationUnit compilationUnit : sourceCode.getCompilationUnits()) {
            writeTo(structure, compilationUnit);
        }
    }

    private void writeTo(SourceStructure structure, JavaCompilationUnit compilationUnit) throws IOException {
        Path output = structure.createSourceFile(compilationUnit.getPackageName(), compilationUnit.getName());
        Files.createDirectories(output.getParent());
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("java",
                Files.newBufferedWriter(output))) {
            writer.println("package " + compilationUnit.getPackageName() + ";");
            writer.println();
            Set<String> imports = determineImports(compilationUnit);
            if (!imports.isEmpty()) {
                for (String importedType : imports) {
                    writer.println("import " + importedType + ";");
                }
                writer.println();
            }
            for (JavaTypeDeclaration type : compilationUnit.getTypeDeclarations()) {
                writeAnnotations(writer, type);
                writeModifiers(writer, TYPE_MODIFIERS, type.getModifiers());
                writer.print(type.getType() + " " + type.getName());
                if (type.getExtendedClassName() != null) {
                    writer.print(" extends " + getUnqualifiedName(type.getExtendedClassName()));
                }
                writer.println(" {");
                writer.println();
                List<JavaFieldDeclaration> fieldDeclarations = type.getFieldDeclarations();
                if (!fieldDeclarations.isEmpty()) {
                    writer.indented(() -> {
                        for (JavaFieldDeclaration fieldDeclaration : fieldDeclarations) {
                            writeFieldDeclaration(writer, fieldDeclaration);
                        }
                    });
                }
                List<JavaMethodDeclaration> methodDeclarations = type.getMethodDeclarations();
                if (!methodDeclarations.isEmpty()) {
                    writer.indented(() -> {
                        for (JavaMethodDeclaration methodDeclaration : methodDeclarations) {
                            writeMethodDeclaration(writer, methodDeclaration);
                        }
                    });
                }
                writer.println("}");
            }
        }
    }

    private void writeAnnotations(IndentingWriter writer, Annotatable annotatable) {
        annotatable.getAnnotations().forEach(annotation -> writeAnnotation(writer, annotation));
    }

    private void writeAnnotation(IndentingWriter writer, Annotation annotation) {
        writer.print("@" + getUnqualifiedName(annotation.getName()));
        List<Annotation.Attribute> attributes = annotation.getAttributes();
        if (!attributes.isEmpty()) {
            writer.print("(");
            if (attributes.size() == 1 && attributes.get(0).getName().equals("value")) {
                writer.print(formatAnnotationAttribute(attributes.get(0)));
            } else {
                writer.print(attributes.stream()
                        .map((attribute) -> attribute.getName() + " = " + formatAnnotationAttribute(attribute))
                        .collect(Collectors.joining(", ")));
            }
            writer.print(")");
        }
        writer.println();
    }

    private String formatAnnotationAttribute(Annotation.Attribute attribute) {
        List<String> values = attribute.getValues();
        if (attribute.getType().equals(Class.class)) {
            return formatValues(values, (value) -> String.format("%s.class", getUnqualifiedName(value)));
        }
        if (Enum.class.isAssignableFrom(attribute.getType())) {
            return formatValues(values, (value) -> {
                String enumValue = value.substring(value.lastIndexOf(".") + 1);
                String enumClass = value.substring(0, value.lastIndexOf("."));
                return String.format("%s.%s", getUnqualifiedName(enumClass), enumValue);
            });
        }
        if (attribute.getType().equals(String.class)) {
            return formatValues(values, (value) -> String.format("\"%s\"", value));
        }
        return formatValues(values, (value) -> String.format("%s", value));
    }

    private String formatValues(List<String> values, Function<String, String> formatter) {
        String result = values.stream().map(formatter).collect(Collectors.joining(", "));
        return (values.size() > 1) ? "{ " + result + " }" : result;
    }

    private void writeFieldDeclaration(IndentingWriter writer, JavaFieldDeclaration fieldDeclaration) {
        writeAnnotations(writer, fieldDeclaration);
        writeModifiers(writer, FIELD_MODIFIERS, fieldDeclaration.getModifiers());
        writer.print(getUnqualifiedName(fieldDeclaration.getType()));
        writer.print(" ");
        writer.print(fieldDeclaration.getName());
        if (fieldDeclaration.isInitialized()) {
            writer.print(" = ");
            writer.print(String.valueOf(fieldDeclaration.getValue()));
        }
        writer.println(";");
        writer.println();
    }

    private void writeMethodDeclaration(IndentingWriter writer, JavaMethodDeclaration methodDeclaration) {
        writeAnnotations(writer, methodDeclaration);
        writeModifiers(writer, METHOD_MODIFIERS, methodDeclaration.getModifiers());
        writer.print(getUnqualifiedName(methodDeclaration.getReturnType()) + " " + methodDeclaration.getName() + "(");
        List<Parameter> parameters = methodDeclaration.getParameters();
        if (!parameters.isEmpty()) {
            writer.print(parameters.stream()
                    .map((parameter) -> getUnqualifiedName(parameter.getType()) + " " + parameter.getName())
                    .collect(Collectors.joining(", ")));
        }
        writer.println(") {");
        writer.indented(() -> {
            List<JavaStatement> statements = methodDeclaration.getStatements();
            for (JavaStatement statement : statements) {
                if (statement instanceof JavaExpressionStatement) {
                    writeExpression(writer, ((JavaExpressionStatement) statement).getExpression());
                } else if (statement instanceof JavaReturnStatement) {
                    writer.print("return ");
                    writeExpression(writer, ((JavaReturnStatement) statement).getExpression());
                }
                writer.println(";");
            }
        });
        writer.println("}");
        writer.println();
    }

    private void writeModifiers(IndentingWriter writer, Map<Predicate<Integer>, String> availableModifiers,
                                int declaredModifiers) {
        String modifiers = availableModifiers.entrySet().stream()
                .filter((entry) -> entry.getKey().test(declaredModifiers)).map(Entry::getValue)
                .collect(Collectors.joining(" "));
        if (!modifiers.isEmpty()) {
            writer.print(modifiers);
            writer.print(" ");
        }
    }

    private void writeExpression(IndentingWriter writer, JavaExpression expression) {
        if (expression instanceof JavaMethodInvocation) {
            writeMethodInvocation(writer, (JavaMethodInvocation) expression);
        }
    }

    private void writeMethodInvocation(IndentingWriter writer, JavaMethodInvocation methodInvocation) {
        writer.print(getUnqualifiedName(methodInvocation.getTarget()) + "." + methodInvocation.getName() + "("
                + String.join(", ", methodInvocation.getArguments()) + ")");
    }

    private Set<String> determineImports(JavaCompilationUnit compilationUnit) {
        List<String> imports = new ArrayList<>();
        for (JavaTypeDeclaration typeDeclaration : compilationUnit.getTypeDeclarations()) {
            if (requiresImport(typeDeclaration.getExtendedClassName())) {
                imports.add(typeDeclaration.getExtendedClassName());
            }
            imports.addAll(getRequiredImports(typeDeclaration.getAnnotations(), this::determineImports));
            for (JavaFieldDeclaration fieldDeclaration : typeDeclaration.getFieldDeclarations()) {
                if (requiresImport(fieldDeclaration.getType())) {
                    imports.add(fieldDeclaration.getType());
                }
                imports.addAll(getRequiredImports(fieldDeclaration.getAnnotations(), this::determineImports));
            }
            for (JavaMethodDeclaration methodDeclaration : typeDeclaration.getMethodDeclarations()) {
                if (requiresImport(methodDeclaration.getReturnType())) {
                    imports.add(methodDeclaration.getReturnType());
                }
                imports.addAll(getRequiredImports(methodDeclaration.getAnnotations(), this::determineImports));
                imports.addAll(getRequiredImports(methodDeclaration.getParameters(),
                        (parameter) -> Collections.singletonList(parameter.getType())));
                imports.addAll(getRequiredImports(
                        methodDeclaration.getStatements().stream().filter(JavaExpressionStatement.class::isInstance)
                                .map(JavaExpressionStatement.class::cast).map(JavaExpressionStatement::getExpression)
                                .filter(JavaMethodInvocation.class::isInstance).map(JavaMethodInvocation.class::cast),
                        (methodInvocation) -> Collections.singleton(methodInvocation.getTarget())));
            }
        }
        Collections.sort(imports);
        return new LinkedHashSet<>(imports);
    }

    private Collection<String> determineImports(Annotation annotation) {
        List<String> imports = new ArrayList<>();
        imports.add(annotation.getName());
        annotation.getAttributes().forEach((attribute) -> {
            if (attribute.getType() == Class.class) {
                imports.addAll(attribute.getValues());
            }
            if (Enum.class.isAssignableFrom(attribute.getType())) {
                imports.addAll(attribute.getValues().stream().map((value) -> value.substring(0, value.lastIndexOf(".")))
                        .collect(Collectors.toList()));
            }
        });
        return imports;
    }

    private <T> List<String> getRequiredImports(List<T> candidates, Function<T, Collection<String>> mapping) {
        return getRequiredImports(candidates.stream(), mapping);
    }

    private <T> List<String> getRequiredImports(Stream<T> candidates, Function<T, Collection<String>> mapping) {
        return candidates.map(mapping).flatMap(Collection::stream).filter(this::requiresImport)
                .collect(Collectors.toList());
    }

    private String getUnqualifiedName(String name) {
        if (!name.contains(".")) {
            return name;
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private boolean requiresImport(String name) {
        if (name == null || !name.contains(".")) {
            return false;
        }
        String packageName = name.substring(0, name.lastIndexOf('.'));
        return !"java.lang".equals(packageName);
    }

}
