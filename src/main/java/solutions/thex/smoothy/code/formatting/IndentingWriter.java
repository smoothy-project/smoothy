package solutions.thex.smoothy.code.formatting;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import java.util.zip.ZipOutputStream;

/**
 * A {@link Writer} with support for indenting.
 */
public class IndentingWriter extends ZipOutputStream {

    private final Function<Integer, String> indentStrategy;

    private int level = 0;

    private String indent = "";

    private boolean prependIndent = false;

    /**
     * Create a new instance with the specified {@linkplain Writer writer} using a default
     * indent strategy of 4 spaces.
     *
     * @param out the writer to use
     */
    public IndentingWriter(OutputStream out) {
        this(out, new SimpleIndentStrategy("    "));
    }

    /**
     * Create a new instance with the specified {@linkplain Writer writer} and indent
     * strategy.
     *
     * @param out            the writer to use
     * @param indentStrategy a function that provides the ident to use based on a
     *                       indentation level
     */
    public IndentingWriter(OutputStream out, Function<Integer, String> indentStrategy) {
        super(out);
        this.indentStrategy = indentStrategy;
    }

    /**
     * Write the specified text.
     *
     * @param string the content to write
     */
    public void print(String string) {
        write(string.getBytes(StandardCharsets.UTF_8), 0, string.length());
    }

    /**
     * Write the specified text and append a new line.
     *
     * @param string the content to write
     */
    public void println(String string) {
        write(string.getBytes(StandardCharsets.UTF_8), 0, string.length());
        println();
    }

    /**
     * Write a new line.
     */
    public void println() {
        String separator = System.lineSeparator();
        try {
            super.write(separator.getBytes(StandardCharsets.UTF_8), 0, separator.length());
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
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

    @Override
    public void write(byte[] bytes, int offset, int length) {
        try {
            if (this.prependIndent) {
                super.write(this.indent.getBytes(StandardCharsets.UTF_8), 0, this.indent.length());
                this.prependIndent = false;
            }
            super.write(bytes, offset, length);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

}
