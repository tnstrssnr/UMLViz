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
			"\tfontname = \"Bitstream Vera Sans\"\n"
			+ "\tfontsize = 8\n"
			+ "\n"
			+ "\tnode [\n"
				+ "\t\tfontname = \"Bitstream Vera Sans\"\n"
				+ "\t\tfontsize = 8\n"
				+ "\t\tshape = \"record\"\n"
			+ "\t]\n"
			+ "\n"
			+ "\tedge [\n"
			+ "\t\tfontname = \"Bitstream Vera Sans\"\n"
			+ "\t\tfontsize = 8\n"
			+ "\t]\n";


	private GraphGenerator testedObj;
	private GraphSettings settings;
	private StringBuilder builder;
	private Program program;
	private ClazzNodeGenerator clazzNodeGenerator;

	@BeforeEach void setUp() {
		builder = new StringBuilder();
		clazzNodeGenerator = new ClazzNodeGenerator();
		settings = new GraphSettings("\"Bitstream Vera Sans\"", 8);
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
		testedObj.buildFontNameAndFontSize("\"Comic Sans\"", 8, 2);
		String res = builder.toString();
		String expected = "\t\tfontname = \"Comic Sans\"\n\t\tfontsize = 8\n";
		Assertions.assertEquals(expected, res);
	}

	@Test void buildAssignment() {
		testedObj.buildAssignment("var", "val", 2);
		String res = builder.toString();
		Assertions.assertEquals("\t\tvar = val\n", res);
	}

	@Test void buildGraph_withClass() {
		Method m = new Method("bark", Visibility.PUBLIC, false, false, Type.VOID, false, Collections.emptyList());
		Field f = new Field("age", Visibility.PRIVATE, false, false, Type.INT, null);
		Clazz dog = new Clazz("Dog", Visibility.PUBLIC, false, false, false, List.of(f), List.of(m), null, Collections.emptyList());
		program.getClasses().add(dog);
		System.out.println(testedObj.buildGraph(program));
	}
}