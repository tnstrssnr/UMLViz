package graphgeneration.service;

import graphgeneration.model.GraphConstants;
import programanalysis.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class ClazzNodeGenerator implements ProgramPartVisitor<String> {

	@Override public String visitClass(Clazz clazz) {
		String prefix = clazz.getName() + " " + "[\n";
		String suffix = "\n]\n";

		List<String> fields = clazz.getFields().stream().map(this::visit).collect(Collectors.toList());
		List<String> methods = clazz.getMethods().stream().map(this::visit).collect(Collectors.toList());

		String content = GraphConstants.NODE_CONTENT + " = \"{"
				+ clazz.getName()
				+ "|"
				+ fields.stream().reduce("", (acc, s) -> (acc.isEmpty()) ? s : acc  + s + "\\l")
				+ "|"
				+ methods.stream().reduce("", (acc, s) -> (acc.isEmpty()) ? s : acc  + s + "\\l")
				+ "}\"";

		return prefix + content + suffix;
	}

	@Override public String visitInterface(Interface interfacee) {
		String prefix = interfacee.getName() + " " + "[\n";
		String suffix = "\n]\n";

		List<String> methods = interfacee.getMethods().stream().map(field -> this.visit(field)).collect(Collectors.toList());

		String content = GraphConstants.NODE_CONTENT + " = {"
				+ "<<interface>>\\l"
				+ interfacee.getName()
				+ "|"
				+ methods.stream().reduce("", (acc, s) -> (acc.isEmpty()) ? s : acc  + s + "\\l")
				+ "}";

		return prefix + content + suffix;
	}

	@Override public String visitMethod(Method method) {
		List<String> parameters = method.getParameters().stream().map(this::parameterString).collect(
				Collectors.toList());

		return visibilitySymbol(method.getVisibility())
				+ method.getName()
				+ "("
				+ parameters.stream().reduce("", (acc, s) -> (acc.isEmpty()) ? s : acc + ", " + s)
				+ ") : "
				+ method.getReturnType().getName();
	}

	private String parameterString(Parameter parameter) {
		return parameter.getName() + ": " + parameter.getType().getName();
	}

	@Override public String visitField(Field field) {
		return visibilitySymbol(field.getVisibility())
				+ field.getName()
				+ ": "
				+ field.getType().getName();
	}

	private String visibilitySymbol(Visibility modifier) {
		switch (modifier) {
		case PUBLIC:
			return "+";
		case PROTECTED:
			return "#";
		case PRIVATE:
			return "-";
		}
		return "";
	}
}