package programanalysis.model;

import org.jetbrains.annotations.NotNull;

public class Parameter {

	@NotNull
	private final String name;
	@NotNull
	private final Type type;

	public Parameter(@NotNull String name, @NotNull Type type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}
}