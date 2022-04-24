package solutions.thex.smoothy.code.java.statement;

import lombok.Builder;
import solutions.thex.smoothy.code.Expression;
import solutions.thex.smoothy.code.Statement;
import solutions.thex.smoothy.code.java.JavaSourceCodeWriter;


public record JavaDeclarationStatement(int modifiers, String name, String type,//
                                       boolean initialized,
                                       Expression expression) implements Statement {

    @Builder
    public JavaDeclarationStatement {
    }

    @Override
    public String render() {
        StringBuilder str = new StringBuilder();
        str.append(JavaSourceCodeWriter.writeModifiers(JavaSourceCodeWriter.FIELD_MODIFIERS, this.modifiers));
        str.append(JavaSourceCodeWriter.getUnqualifiedName(this.type)).append(" ").append(this.name);
        if (this.initialized) str.append(" = ").append(this.expression.render());
        str.append(";");
        return str.toString();
    }

}
