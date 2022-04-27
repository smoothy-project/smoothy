package solutions.thex.smoothy.code.java.expression.util;

import lombok.Builder;
import lombok.experimental.SuperBuilder;
import solutions.thex.smoothy.code.java.util.JavaOperand;

@SuperBuilder
public class Operable {

    @Builder.Default
    private JavaOperand operand = null;

    public String render() {
        return (operand != null) ? " " + operand.getSymbol() + " " : "";
    }

}
