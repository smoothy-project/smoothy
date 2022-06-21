package solutions.thex.smoothy.core.declaration.formatting;

import lombok.Builder;
import lombok.Builder.Default;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A factory for {@link IndentingWriter} that provides customizations according
 * to the chosen content.
 */
@Builder
public final class IndentingWriterFactory {

	@Default
	private final Map<String, Function<Integer, String>> indentingStrategies = new HashMap<>();
    private final Function<Integer, String> defaultIndentingStrategy;

    /**
     * Create an {@link IndentingWriter} for the specified content and output.
     *
     * @param contentId the identifier of the content
     * @return a configured {@link IndentingWriter}
     */
    public IndentingWriter createIndentingWriter(String contentId) {
        Function<Integer, String> indentingStrategy = this.indentingStrategies.getOrDefault(contentId,//
                this.defaultIndentingStrategy);

        return new IndentingWriter(indentingStrategy);
    }

}
