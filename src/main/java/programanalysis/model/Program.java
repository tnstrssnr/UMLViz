package programanalysis.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Program {

	@NotNull
	private final String name;
	@NotNull
	private final List<Clazz> classes;

	public Program(@NotNull String name, @NotNull List<Clazz> classes) {
		this.name = name;
		this.classes = classes;
	}

	public @NotNull String getName() {
		return name;
	}

	public @NotNull List<Clazz> getClasses() {
		return classes;
	}
}