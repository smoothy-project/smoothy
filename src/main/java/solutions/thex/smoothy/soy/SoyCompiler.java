package solutions.thex.smoothy.soy;

import com.google.template.soy.AbstractSoyCompiler;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.jbcsrc.api.SoySauce;
import lombok.Getter;

@Getter
public class SoyCompiler extends AbstractSoyCompiler {

    private SoySauce soySauce;

    public SoyCompiler() {
        //
    }

    @Override
    protected void compile(SoyFileSet.Builder soyFileSetBuilder) {
        soySauce = soyFileSetBuilder.build().compileTemplates();
    }

}
