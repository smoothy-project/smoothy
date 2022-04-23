package solutions.thex.smoothy.code.java;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Operable {

    @Builder.Default
    private JavaOperand operand = null;

    public String render() {
        return (operand != null) ? " " + operand.getSymbol() + " " : "";
    }

}
