package solutions.thex.smoothy.util;

public class StringFormatter {

    public static String toPascalCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String toCamelCase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    public static String camelToKebab(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

}
