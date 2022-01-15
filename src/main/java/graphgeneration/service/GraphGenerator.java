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
		buildFontNameAndFontSize(settings.getFont(), settings.getFontSize(), 1);
		builder.append("\n");
		builder.append(indentString(1));
		builder.append("node ");

		wrapString(() -> {
			buildFontNameAndFontSize(settings.getFont(), settings.getFontSize(), 2);
			buildAssignment("shape", "\"record\"", 2);
		}, "[\n", indentString(1) + "]\n");


		builder.append("\n");
		builder.append(indentString(1));
		builder.append("edge ");

		wrapString(() -> {
			buildFontNameAndFontSize(settings.getFont(), settings.getFontSize(), 2);
		}, "[\n", indentString(1) + "]\n");

	}

	protected void buildFontNameAndFontSize(@NotNull String fontName, int fontSize, int indent) {
		buildAssignment(GraphConstants.FONT_NAME, fontName, indent);
		buildAssignment(GraphConstants.FONT_SIZE, String.valueOf(fontSize), indent);
	}

	protected void buildAssignment(@NotNull String variable, @NotNull String value, int indent) {
		builder.append(indentString(indent))
				.append(variable)
				.append(GraphConstants.EQUALS)
				.append(value)
				.append("\n");
	}

	protected void buildClassNode(@NotNull Clazz clazz) {
		builder.append(clazzNodeGenerator.visitClass(clazz));
	}

	private void appendLine(String line, int indent) {
		builder.append(indentString(indent))
				.append(line)
				.append("\n");
	}

	protected void wrapString(@NotNull StringMethod stringMethod, @NotNull String prefix, @NotNull String suffix) {
		builder.append(prefix);
		stringMethod.appendString();
		builder.append(suffix);
	}

	@NotNull
	private String indentString(int indent) {
		return "\t".repeat(indent);
	}

	@FunctionalInterface
	public interface StringMethod {

		void appendString();

	}
}