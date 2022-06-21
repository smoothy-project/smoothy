package solutions.thex.smoothy.core.declaration.formatting;

import java.io.Writer;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A {@link Writer} with support for indenting.
 */
public class IndentingWriter {

    private final Function<Integer, String> indentStrategy;

    private final StringBuilder buffer = new StringBuilder();

    private int level = 0;

    private String indent = "";

    private boolean prependIndent = false;

    /**
     * Create a new instance with the specified {@linkplain Writer writer} and indent
     * strategy.
     *
     * @param indentStrategy a function that provides the ident to use based on an
     *                       indentation level
     */
    public IndentingWriter(Function<Integer, String> indentStrategy) {
        this.indentStrategy = indentStrategy;
    }

    public IndentingWriter(Function<Integer, String> indentStrategy, int level) {
        this.indentStrategy = indentStrategy;
        this.level = level;
        refreshIndent();
    }

    public String getIndent() {
        return indent;
    }

    /**
     * Write the specified text.
     *
     * @param string the content to write
     */
    public void print(String string) {
        write(string);
    }

    /**
     * Write the specified text and append a new line.
     *
     * @param string the content to write
     */
    public void println(String string) {
        write(string);
        println();
    }

    /**
     * Write a new line.
     */
    public void println() {
        buffer.append(System.lineSeparator());
        this.prependIndent = true;
    }

    /**
     * Increase the indentation level and execute the {@link Runnable}. Decrease the
     * indentation level on completion.
     *
     * @param runnable the code to execute withing an extra indentation level
     */
    public void indented(Runnable runnable) {
        indent();
        runnable.run();
        outdent();
    }

    public String render() {
        return buffer.toString();
    }

    /**
     * Increase the indentation level.
     */
    private void indent() {
        this.level++;
        refreshIndent();
    }

    /**
     * Decrease the indentation level.
     */
    private void outdent() {
        this.level--;
        refreshIndent();
    }

    private void refreshIndent() {
        this.indent = this.indentStrategy.apply(this.level);
    }

    private void write(String string) {
        if (this.prependIndent) {
            writeWithIndent(string);
            this.prependIndent = false;
        } else {
            buffer.append(string);
        }
    }

    private void writeWithIndent(String string) {
        if (string.contains(System.lineSeparator())) {
            buffer.append(//
                    Arrays.stream(string.split(System.lineSeparator()))//
                            .map(line -> this.indent + line)//
                            .collect(Collectors.joining(System.lineSeparator())));
            if (string.endsWith(System.lineSeparator()))
                buffer.append(this.indent).append(System.lineSeparator());
        } else
            buffer.append(this.indent).append(string);
    }

}
