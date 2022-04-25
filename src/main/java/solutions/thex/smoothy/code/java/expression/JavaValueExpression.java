package solutions.thex.smoothy.code.java.expression;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.java.Operable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
public class JavaValueExpression extends Operable implements Expression {

    private final String value;
    private final Class<?> type;

    @Override
    public String render() {
        String renderedValue;
        if (this.type.equals(Class.class)) {
            renderedValue = String.format("%s.class", JavaSourceCodeWriter.getUnqualifiedName(this.value));
        } else if (Enum.class.isAssignableFrom(this.type)) {
            String enumValue = this.value.substring(this.value.lastIndexOf(".") + 1);
            String enumClass = this.value.substring(0, this.value.lastIndexOf("."));
            renderedValue = String.format("%s.%s", JavaSourceCodeWriter.getUnqualifiedName(enumClass), enumValue);
        } else if (this.type.equals(String.class)) {
            renderedValue = String.format("\"%s\"", this.value);
        } else {
            renderedValue = String.format("%s", this.value);
        }
        return renderedValue + super.render();
    }

    @Override
    public Set<String> imports() {
        List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.value)) {
            if (this.type == Class.class) imports.add(this.value);
            if (Enum.class.isAssignableFrom(this.type))
                imports.add(this.value.substring(0, this.value.lastIndexOf(".")));
        }
        return new LinkedHashSet<>(imports);
    }

}
