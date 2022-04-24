package solutions.thex.smoothy.code.java.expression;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;

@Builder
@Getter
public class JavaValueExpression implements Expression {

    private final String value;
    private final Class<?> type;

    @Override
    public String render() {
        if (this.type.equals(Class.class)) {
            return String.format("%s.class", JavaSourceCodeWriter.getUnqualifiedName(this.value));
        }
        if (Enum.class.isAssignableFrom(this.type)) {
            String enumValue = this.value.substring(this.value.lastIndexOf(".") + 1);
            String enumClass = this.value.substring(0, this.value.lastIndexOf("."));
            return String.format("%s.%s", JavaSourceCodeWriter.getUnqualifiedName(enumClass), enumValue);
        }
        if (this.type.equals(String.class)) {
            return String.format("\"%s\"", this.value);
        }
        return String.format("%s", this.value);
    }

}
