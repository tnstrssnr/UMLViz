package graphgeneration.service;

import graphgeneration.model.GraphConstants;
import programanalysis.model.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static graphgeneration.model.GraphConstants.ASSIGN;

public class ClazzNodeGenerator implements ProgramPartVisitor<String> {

	@Override public String visitClass(Clazz clazz) {
		String prefix = clazz.getName() + " " + "[\n";
		String suffix = "\n]\n";

		List<String> fields = clazz.getFields().stream()
				.sorted(Comparator.comparing(Field::isStatic))
				.map(this::visit).collect(Collectors.toList());
		List<String> methods = clazz.getMethods().stream()
				.sorted(Comparator.comparing(Method::isStatic))
				.map(this::visit).collect(Collectors.toList());

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

		List<String> methods = interfacee.getMethods().stream().map(this::visit).collect(Collectors.toList());

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

		String methodLine =  visibilitySymbol(method.getVisibility())
				+ method.getName()
				+ "("
				+ parameters.stream().reduce("", (acc, s) -> (acc.isEmpty()) ? s : acc + ", " + s)
				+ ") : "
				+ method.getReturnType().getName();

		if (method.isStatic()) {
			methodLine = "<u>" + methodLine + "</u>";
		}

		if (method.isAbstract()) {
			methodLine = "<i>" + methodLine + "</i>";
		}

		if (method.isFinal()) {
			methodLine = methodLine + " <<final>>";
		}

		return methodLine;
	}

	private String parameterString(Parameter parameter) {
		return parameter.getName() + ": " + parameter.getType().getName();
	}

	@Override public String visitField(Field field) {
		String fieldLine =  visibilitySymbol(field.getVisibility())
				+ field.getName()
				+ ": "
				+ field.getType().getName();

		if (field.getDefaultValue() != null) {
			fieldLine += ASSIGN + field.getDefaultValue();
		}

		if (field.isStatic()) {
			fieldLine = "<u>" + fieldLine + "</u>";
		}

		if (field.isFinal()) {
			fieldLine = fieldLine + " {readOnly}";
		}

		return fieldLine;
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