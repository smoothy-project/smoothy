package solutions.thex.smoothy.code.java;

public enum JavaOperand {

    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    POWER("^"),
    MODULO("%"),
    EXPONENT("**"),
    AND("&&"),
    OR("||"),
    NOT("!"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN_OR_EQUAL("<="),
    ASSIGNMENT("="),
    PLUS_ASSIGNMENT("+="),
    MINUS_ASSIGNMENT("-="),
    MULTIPLY_ASSIGNMENT("*="),
    DIVIDE_ASSIGNMENT("/="),
    POWER_ASSIGNMENT("^="),
    MODULO_ASSIGNMENT("%="),
    EXPONENT_ASSIGNMENT("**="),
    AND_ASSIGNMENT("&&="),
    OR_ASSIGNMENT("||="),
    NOT_ASSIGNMENT("!="),
    INCREMENT("++"),
    DECREMENT("--"),
    LEFT_SHIFT("<<"),
    RIGHT_SHIFT(">>"),
    LEFT_SHIFT_ASSIGNMENT("<<="),
    RIGHT_SHIFT_ASSIGNMENT(">>="),
    BITWISE_AND("&"),
    BITWISE_AND_ASSIGNMENT("&="),
    BITWISE_OR("|"),
    BITWISE_OR_ASSIGNMENT("|="),
    BITWISE_XOR("^"),
    BITWISE_XOR_ASSIGNMENT("^="),
    BITWISE_NOT("~"),
    BITWISE_NOT_ASSIGNMENT("~=");

    private final String symbol;

    JavaOperand(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
