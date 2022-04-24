package solutions.thex.smoothy.code.java;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An annotation.
 */
@Builder
@Getter
public final class Annotation {

    /**
     * Define an attribute of an annotation.
     */
    @Builder
    @Getter
    public static final class Attribute {
        private final String name;
        private final Class<?> type;
        @Default
        private final List<String> values = new LinkedList<>();
    }

    @Default
    private final List<Attribute> attributes = new LinkedList<>();
    private final String name;

    public Set<String> imports() {
        return null;
    }

    public String render() {
        StringBuilder annotation = new StringBuilder();
        annotation.append("@").append(JavaSourceCodeWriter.getUnqualifiedName(this.name));
        List<Annotation.Attribute> attributes = this.getAttributes();
        if (!attributes.isEmpty()) {
            annotation.append("(");
            if (attributes.get(0).getName() != null) {
                if (attributes.size() == 1 && attributes.get(0).getName().equals("value")) {
                    annotation.append(formatAnnotationAttribute(attributes.get(0)));
                } else {
                    annotation.append(attributes.stream()
                            .map((attribute) -> attribute.getName() + " = " + formatAnnotationAttribute(attribute))
                            .collect(Collectors.joining(", ")));
                }
            } else {
                annotation.append(formatAnnotationAttribute(attributes.get(0)));
            }
            annotation.append(")");
        }
        annotation.append("\n");
        return annotation.toString();
    }

    private String formatAnnotationAttribute(Annotation.Attribute attribute) {
        List<String> values = attribute.getValues();
        if (attribute.getType().equals(Class.class)) {
            return formatValues(values, (value) -> String.format("%s.class", JavaSourceCodeWriter.getUnqualifiedName(value)));
        }
        if (Enum.class.isAssignableFrom(attribute.getType())) {
            return formatValues(values, (value) -> {
                String enumValue = value.substring(value.lastIndexOf(".") + 1);
                String enumClass = value.substring(0, value.lastIndexOf("."));
                return String.format("%s.%s", JavaSourceCodeWriter.getUnqualifiedName(enumClass), enumValue);
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

}
