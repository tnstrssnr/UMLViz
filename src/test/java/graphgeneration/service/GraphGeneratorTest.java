package graphgeneration.service;

import graphgeneration.model.GraphSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programanalysis.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GraphGeneratorTest {

	private static final String PREAMBLE =
			"fontname = \"Bitstream Vera Sans\"\n"
			+ "fontsize = 8\n"
			+ "\n"
			+ "node [\n"
				+ "fontname = \"Bitstream Vera Sans\"\n"
				+ "fontsize = 8\n"
				+ "shape = \"record\"\n"
			+ "]\n"
			+ "\n"
			+ "edge [\n"
			+ "fontname = \"Bitstream Vera Sans\"\n"
			+ "fontsize = 8\n"
			+ "]\n";


	private GraphGenerator testedObj;
	private StringBuilder builder;
	private Program program;

	@BeforeEach void setUp() {
		builder = new StringBuilder();
		ClazzNodeGenerator clazzNodeGenerator = new ClazzNodeGenerator();
		GraphSettings settings = new GraphSettings("\"Bitstream Vera Sans\"", 8);
		testedObj = new GraphGenerator(settings, builder, clazzNodeGenerator);
		program = new Program("g", new ArrayList<>());
	}

	@Test void buildGraph() {
		String expected = "digraph g {\n" + PREAMBLE + "}";
		String res = testedObj.buildGraph(program);
		Assertions.assertEquals(res, expected);
	}

	@Test void buildPreamble() {
		testedObj.buildPreamble();
		String res = builder.toString();
		Assertions.assertEquals(PREAMBLE, res);
	}

	@Test void buildFontNameAndFontSize() {
		testedObj.buildFontNameAndFontSize("\"Comic Sans\"", 8);
		String res = builder.toString();
		String expected = "fontname = \"Comic Sans\"\nfontsize = 8\n";
		Assertions.assertEquals(expected, res);
	}

	@Test void buildAssignment() {
		testedObj.buildAssignment("var", "val");
		String res = builder.toString();
		Assertions.assertEquals("var = val\n", res);
	}

	@Test void buildGraph_withClass() {
		Method m = new Method("bark", Visibility.PUBLIC, false, false, Type.VOID, false, Collections.emptyList());
		Field f = new Field("age", Visibility.PRIVATE, false, false, Type.INT, null);
		Clazz dog = new Clazz("Dog", Visibility.PUBLIC, false, false, false, List.of(f), List.of(m), null, Collections.emptyList());
		program.getClasses().add(dog);
		String res = testedObj.buildGraph(program);
		Assertions.assertEquals("digraph g {\n" + "fontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8\n" + "\n"
				+ "node [\n" + "fontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8\n" + "shape = \"record\"\n"
				+ "]\n" + "\n" + "edge [\n" + "fontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8\n" + "]\n"
				+ "Dog [\n" + "label = \"{Dog|-age: int|+bark() : void}\"\n" + "]\n" + "}", res);
	}
}