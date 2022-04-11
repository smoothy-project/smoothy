package solutions.thex.smoothy.code.java;

public enum JavaType {

    CLASS("class"),
    INTERFACE("interface"),
    ENUM("enum"),
    ANNOTATION("annotation"),
    RECORD("record");

    private final String type;

    JavaType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }

    public static JavaType getType(String name) {
        for (JavaType type : values()) {
            if (type.toString().equals(name)) {
                return type;
            }
        }
        return null;
    }

}
