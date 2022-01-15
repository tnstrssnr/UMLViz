package graphgeneration.service;

import graphgeneration.model.GraphConstants;
import graphgeneration.model.GraphSettings;
import org.jetbrains.annotations.NotNull;
import programanalysis.model.Clazz;
import programanalysis.model.Program;

public class GraphGenerator {

	@NotNull
	private final GraphSettings settings;
	@NotNull
	private StringBuilder builder;
	@NotNull
	private final ClazzNodeGenerator clazzNodeGenerator;

	public GraphGenerator(@NotNull GraphSettings settings, @NotNull StringBuilder builder,
			@NotNull ClazzNodeGenerator clazzNodeGenerator) {
		this.settings = settings;
		this.builder = builder;
		this.clazzNodeGenerator = clazzNodeGenerator;
	}

	@NotNull
	public String buildGraph(@NotNull Program program) {
		builder = new StringBuilder();

		builder.append(GraphConstants.GRAPH_TYPE)
				.append(" ")
				.append(program.getName());

		wrapString(() -> {
			this.buildPreamble();
			for (Clazz clazz: program.getClasses()) {
				buildClassNode(clazz);
			}
		}, " {\n", "}");

		return builder.toString();
	}

	protected void buildPreamble() {
		buildFontNameAndFontSize(settings.getFont(), settings.getFontSize());
		builder.append("\n").append("node ");

		wrapString(() -> {
			buildFontNameAndFontSize(settings.getFont(), settings.getFontSize());
			buildAssignment("shape", "\"record\"");
		}, "[\n", "]\n");

		builder.append("\n").append("edge ");
		wrapString(() -> buildFontNameAndFontSize(settings.getFont(), settings.getFontSize()), "[\n", "]\n");

	}

	protected void buildFontNameAndFontSize(@NotNull String fontName, int fontSize) {
		buildAssignment(GraphConstants.FONT_NAME, fontName);
		buildAssignment(GraphConstants.FONT_SIZE, String.valueOf(fontSize));
	}

	protected void buildAssignment(@NotNull String variable, @NotNull String value) {
		builder.append(variable)
				.append(GraphConstants.ASSIGN)
				.append(value)
				.append("\n");
	}

	protected void buildClassNode(@NotNull Clazz clazz) {
		builder.append(clazzNodeGenerator.visitClass(clazz));
	}

	protected void wrapString(@NotNull StringMethod stringMethod, @NotNull String prefix, @NotNull String suffix) {
		builder.append(prefix);
		stringMethod.appendString();
		builder.append(suffix);
	}

	@FunctionalInterface
	public interface StringMethod {

		void appendString();

	}
}