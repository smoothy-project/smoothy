package solutions.thex.smoothy.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringFormatter {

    public static String toPascalCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String toPascalCase(String... strings) {
        return Arrays.stream(strings).map(StringFormatter::toPascalCase).collect(Collectors.joining());
    }

    public static String toCamelCase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1).toLowerCase(Locale.ROOT);
    }

    public static String camelToKebab(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

}
