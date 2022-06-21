package solutions.thex.smoothy.core.declaration.java.util;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import solutions.thex.smoothy.core.declaration.java.JavaSourceCodeWriter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An annotation.
 */
@Builder
@Getter
public final class JavaAnnotation {

    /**
     * Define an attribute of an annotation.
     */
    @Builder
    @Getter
    public static final class Attribute {

        @Default
        private final List<String> values = new LinkedList<>();
        private final String name;
        private final Class<?> type;

        public String render() {
            if (this.type.equals(Class.class)) {
                return formatValues(values, (value) -> String.format("%s.class", JavaSourceCodeWriter.getUnqualifiedName(value)));
            }
            if (Enum.class.isAssignableFrom(this.type)) {
                return formatValues(values, (value) -> {
                    String enumValue = value.substring(value.lastIndexOf(".") + 1);
                    String enumClass = value.substring(0, value.lastIndexOf("."));
                    return String.format("%s.%s", JavaSourceCodeWriter.getUnqualifiedName(enumClass), enumValue);
                });
            }
            if (this.type.equals(String.class)) {
                return formatValues(values, (value) -> String.format("\"%s\"", value));
            }
            return formatValues(values, (value) -> String.format("%s", value));
        }

        private String formatValues(List<String> values, Function<String, String> formatter) {
            String result = values.stream().map(formatter).collect(Collectors.joining(", "));
            return (values.size() > 1) ? "{ " + result + " }" : result;
        }

    }

    @Default
    private final List<Attribute> attributes = new LinkedList<>();
    private final String name;

    public String render() {
        StringBuilder annotation = new StringBuilder();
        annotation.append("@").append(JavaSourceCodeWriter.getUnqualifiedName(this.name));
        List<JavaAnnotation.Attribute> attributes = this.getAttributes();
        if (!attributes.isEmpty()) {
            annotation.append("(");
            if (attributes.get(0).getName() != null) {
                if (attributes.size() == 1 && attributes.get(0).getName().equals("value")) {
                    annotation.append(attributes.get(0).render());
                } else {
                    annotation.append(attributes.stream()//
                            .map((attribute) -> attribute.getName() + " = " + attribute.render())//
                            .collect(Collectors.joining(", ")));
                }
            } else {
                annotation.append(attributes.get(0).render());
            }
            annotation.append(")");
        }
        annotation.append(System.lineSeparator());
        return annotation.toString();
    }

    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.name)) imports.add(this.name);
        this.attributes.forEach((attribute) -> {
            if (attribute.getType() == Class.class) {
                imports.addAll(attribute.getValues());
            }
            if (Enum.class.isAssignableFrom(attribute.getType())) {
                imports.addAll(attribute.getValues().stream().map((value) -> value.substring(0, value.lastIndexOf(".")))
                        .collect(Collectors.toList()));
            }
        });
        return new LinkedHashSet<>(imports);
    }

}
