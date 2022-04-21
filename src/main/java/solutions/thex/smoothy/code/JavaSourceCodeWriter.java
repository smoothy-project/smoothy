package solutions.thex.smoothy.code;

import solutions.thex.smoothy.code.declaration.JavaFieldDeclaration;
import solutions.thex.smoothy.code.declaration.JavaMethodDeclaration;
import solutions.thex.smoothy.code.declaration.JavaTypeDeclaration;
import solutions.thex.smoothy.code.expression.JavaMethodInvocationExpression;
import solutions.thex.smoothy.code.formatting.IndentingWriter;
import solutions.thex.smoothy.code.formatting.IndentingWriterFactory;
import solutions.thex.smoothy.soy.ISoyConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

/**
 * A SourceCodeWriter that writes SourceCode in Java.
 */
public class JavaSourceCodeWriter {

    public static final Map<Predicate<Integer>, String> TYPE_MODIFIERS;
    public static final Map<Predicate<Integer>, String> FIELD_MODIFIERS;
    public static final Map<Predicate<Integer>, String> METHOD_MODIFIERS;

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

    private final String sourceFileExtension;
    private final String sourcesDirectory;
    private final String testsDirectory;
    private final IndentingWriterFactory indentingWriterFactory;

    public JavaSourceCodeWriter(IndentingWriterFactory indentingWriterFactory) {
        this.indentingWriterFactory = indentingWriterFactory;
        this.sourceFileExtension = "java";
        this.sourcesDirectory = "src/main/java";
        this.testsDirectory = "src/test/java";
    }

    public void writeTo(JavaSourceCode sourceCode, OutputStream out) throws IOException {
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("java", out)) {
            for (JavaCompilationUnit compilationUnit : sourceCode.getCompilationUnits()) {
                writer.putNextEntry(new ZipEntry(resolveFileName(compilationUnit, this.sourcesDirectory)));
                compilationUnit.render(writer);
                writer.closeEntry();
            }
            for (JavaCompilationUnit compilationUnit : sourceCode.getTestCompilationUnits()) {
                writer.putNextEntry(new ZipEntry(resolveFileName(compilationUnit, this.testsDirectory)));
                compilationUnit.render(writer);
                writer.closeEntry();
            }
            for (ISoyConfiguration staticUnit : sourceCode.getStaticCompilationUnits()) {
                writer.putNextEntry(new ZipEntry(staticUnit.getPath().toString()));
                writer.println(staticUnit.render());
                writer.closeEntry();
            }
        }
    }

    private String resolveFileName(JavaCompilationUnit compilationUnit, String source) {
        String file = compilationUnit.getName() + "." + this.sourceFileExtension;
        return resolve(resolvePackage(source, compilationUnit.getPackageName()), file);
    }

    private String resolvePackage(String directory, String packageName) {
        return resolve(directory, packageName.replace('.', '/'));
    }

    private String resolve(String directory, String path) {
        return directory.concat("/").concat(path);
    }

    public static void writeAnnotations(IndentingWriter writer, Annotatable annotatable) {
        annotatable.getAnnotations().forEach(annotation -> writeAnnotation(writer, annotation));
    }

    private static void writeAnnotation(IndentingWriter writer, Annotation annotation) {
        writer.print("@" + getUnqualifiedName(annotation.getName()));
        List<Annotation.Attribute> attributes = annotation.getAttributes();
        if (!attributes.isEmpty()) {
            writer.print("(");
            if (attributes.get(0).getName() != null) {
                if (attributes.size() == 1 && attributes.get(0).getName().equals("value")) {
                    writer.print(formatAnnotationAttribute(attributes.get(0)));
                } else {
                    writer.print(attributes.stream()
                            .map((attribute) -> attribute.getName() + " = " + formatAnnotationAttribute(attribute))
                            .collect(Collectors.joining(", ")));
                }
            } else {
                writer.print(formatAnnotationAttribute(attributes.get(0)));
            }
            writer.print(")");
        }
        writer.println();
    }

    private static String formatAnnotationAttribute(Annotation.Attribute attribute) {
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

    private static String formatValues(List<String> values, Function<String, String> formatter) {
        String result = values.stream().map(formatter).collect(Collectors.joining(", "));
        return (values.size() > 1) ? "{ " + result + " }" : result;
    }

    public static void writeModifiers(IndentingWriter
                                              writer, Map<Predicate<Integer>, String> availableModifiers,
                                      int declaredModifiers) {
        String modifiers = availableModifiers.entrySet().stream()
                .filter((entry) -> entry.getKey().test(declaredModifiers)).map(Entry::getValue)
                .collect(Collectors.joining(" "));
        if (!modifiers.isEmpty()) {
            writer.print(modifiers);
            writer.print(" ");
        }
    }

    public static Set<String> determineImports(JavaCompilationUnit compilationUnit) {
        List<String> imports = new ArrayList<>();
        for (JavaTypeDeclaration typeDeclaration : compilationUnit.getTypeDeclarations()) {
            if (requiresImport(typeDeclaration.getExtendedClassName())) {
                imports.add(typeDeclaration.getExtendedClassName());
            }
            if (requiresImport(typeDeclaration.getImplementedClassName())) {
                imports.add(typeDeclaration.getImplementedClassName());
            }
            imports.addAll(getRequiredImports(typeDeclaration.getAnnotations(), JavaSourceCodeWriter::determineImports));
            for (JavaFieldDeclaration fieldDeclaration : typeDeclaration.getFieldDeclarations()) {
                if (requiresImport(fieldDeclaration.getType())) {
                    imports.add(fieldDeclaration.getType());
                }
                imports.addAll(getRequiredImports(fieldDeclaration.getAnnotations(), JavaSourceCodeWriter::determineImports));
            }
            for (JavaMethodDeclaration methodDeclaration : typeDeclaration.getMethodDeclarations()) {
                if (requiresImport(methodDeclaration.getReturnType())) {
                    imports.add(methodDeclaration.getReturnType());
                }
                imports.addAll(getRequiredImports(methodDeclaration.getAnnotations(), JavaSourceCodeWriter::determineImports));
                imports.addAll(getRequiredImports(methodDeclaration.getExceptions(),
                        Collections::singletonList));
                imports.addAll(getRequiredImports(methodDeclaration.getParameters(),
                        (parameter) -> Collections.singletonList(parameter.getType())));
                imports.addAll(getRequiredImports(//
                        methodDeclaration.getParameters().stream().map(Parameter::getGenericTypes).flatMap(List::stream).collect(Collectors.toList()),
                        Collections::singletonList));
                imports.addAll(getRequiredImports(
                        methodDeclaration.getStatements().stream()//
                                .map(JavaStatement::getExpression)//
                                .filter(JavaMethodInvocationExpression.class::isInstance).map(JavaMethodInvocationExpression.class::cast),
                        (methodInvocation) -> Collections.singleton(methodInvocation.getTarget())));
                imports.addAll(getRequiredImports(
                        methodDeclaration.getStatements().stream()//
                                .map(JavaStatement::getExpression)//
                                .filter(JavaMethodInvocationExpression.class::isInstance)//
                                .map(JavaMethodInvocationExpression.class::cast)//
                                .map(JavaMethodInvocationExpression::getInvokes)//
                                .flatMap(List::stream)//
                                .map(JavaMethodInvocationExpression.MethodInvoke::getArguments)//
                                .flatMap(List::stream)//
                                .map(Argument::getName)
                                .map(str -> {
                                    if (str.startsWith("\""))
                                        return "";
                                    else if (str.contains("::"))
                                        return str.split("::")[0];
                                    else if (str.endsWith(".class"))
                                        return str.substring(0, str.length() - 6);
                                    else if (str.contains(".") && isUpperCase(str.split("\\.")[str.split("\\.").length - 1]))
                                        return str.substring(0, str.length() - (str.split("\\.")[str.split("\\.").length - 1].length() + 1));

                                    return str;
                                })
                        ,
                        Collections::singletonList));
            }
        }
        Collections.sort(imports);
        return new LinkedHashSet<>(imports);
    }

    public static boolean isUpperCase(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isUpperCase(c))
                return false;
        }
        return true;
    }

    private static Collection<String> determineImports(Annotation annotation) {
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

    private static <T> List<String> getRequiredImports(List<T> candidates, Function<T, Collection<String>> mapping) {
        return getRequiredImports(candidates.stream(), mapping);
    }

    private static <T> List<String> getRequiredImports(Stream<T> candidates, Function<T, Collection<String>> mapping) {
        return candidates.map(mapping).flatMap(Collection::stream).filter(JavaSourceCodeWriter::requiresImport)
                .collect(Collectors.toList());
    }

    public static String getUnqualifiedName(String name) {
        if (!name.contains(".")) {
            return name;
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private static boolean requiresImport(String name) {
        if (name == null || !name.contains(".")) {
            return false;
        }
        String packageName = name.substring(0, name.lastIndexOf('.'));
        return !"java.lang".equals(packageName);
    }

}
