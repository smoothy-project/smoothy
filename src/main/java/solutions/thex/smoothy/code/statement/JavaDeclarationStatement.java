package solutions.thex.smoothy.code.statement;

import lombok.Builder;
import solutions.thex.smoothy.code.JavaExpression;
import solutions.thex.smoothy.code.JavaSourceCodeWriter;
import solutions.thex.smoothy.code.JavaStatement;
import solutions.thex.smoothy.code.formatting.IndentingWriter;


public record JavaDeclarationStatement(int modifiers, String name, String type,//
                                       boolean initialized,
                                       JavaExpression expression) implements JavaStatement {

    @Builder
    public JavaDeclarationStatement {
    }

    @Override
    public JavaExpression getExpression() {
        return expression;
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
