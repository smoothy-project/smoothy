package solutions.thex.smoothy.code.formatting;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.Builder;
import lombok.Builder.Default;

/**
 * A factory for {@link IndentingWriter} that provides customizations according
 * to the chosen content.
 */
@Builder
public final class IndentingWriterFactory {

	private final Function<Integer, String> defaultIndentingStrategy;

	@Default
	private final Map<String, Function<Integer, String>> indentingStrategies = new HashMap<>();

	/**
	 * Create an {@link IndentingWriter} for the specified content and output.
	 *
	 * @param contentId the identifier of the content
	 * @param out       the output to use
	 * @return a configured {@link IndentingWriter}
	 */
	public IndentingWriter createIndentingWriter(String contentId, Writer out) {
		Function<Integer, String> indentingStrategy = this.indentingStrategies.getOrDefault(contentId,
				this.defaultIndentingStrategy);
		return new IndentingWriter(out, indentingStrategy);
	}
}
