package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;


@Getter
public record JavaDeclarationStatement(int modifiers, String name, String type, boolean initialized,
                                       JavaExpression expression) implements JavaStatement {

    @Builder
    public JavaDeclarationStatement {
    }

    @Override
    public void render(IndentingWriter writer) {
        JavaSourceCodeWriter.writeModifiers(writer, JavaSourceCodeWriter.FIELD_MODIFIERS, this.modifiers);
        writer.print(JavaSourceCodeWriter.getUnqualifiedName(getType()));
        writer.print(" ");
        writer.print(getName());
        if (isInitialized()) {
            writer.print(" = ");
            expression.render(writer);
        }
        writer.println(";");
    }

}
