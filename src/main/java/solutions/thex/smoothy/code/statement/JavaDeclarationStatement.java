package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import lombok.Getter;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;


public record JavaDeclarationStatement(@Getter int modifiers, @Getter String name, @Getter String type,//
                                       @Getter boolean initialized,
                                       @Getter JavaExpression expression) implements JavaStatement {

    @Builder
    public JavaDeclarationStatement {
    }

    @Override
    public void render(IndentingWriter writer) {
        JavaSourceCodeWriter.writeModifiers(writer, JavaSourceCodeWriter.FIELD_MODIFIERS, this.modifiers);
        writer.print(JavaSourceCodeWriter.getUnqualifiedName(this.type));
        writer.print(" ");
        writer.print(this.name);
        if (this.initialized) {
            writer.print(" = ");
            expression.render(writer);
        }
        writer.println(";");
    }

}
