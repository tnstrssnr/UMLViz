package programanalysis.model;

import org.jetbrains.annotations.NotNull;

public class Type {

	public static final Type INT = new Type("int", true);
	public static final Type SHORT = new Type("short", true);
	public static final Type LONG = new Type("long", true);
	public static final Type FLOAT = new Type("float", true);
	public static final Type DOUBLE = new Type("double", true);
	public static final Type BOOLEAN = new Type("boolean", true);
	public static final Type CHAR = new Type("char", true);
	public static final Type BYTE = new Type("byte", true);
	public static final Type STRING = new Type("String", false);
	public static final Type VOID = new Type("void", true);

	@NotNull
	private final String name;
	private final boolean isPrimitive;

	public Type(@NotNull String name, boolean isPrimitive) {
		this.name = name;
		this.isPrimitive = isPrimitive;
	}

	public String getName() {
		return name;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}
}