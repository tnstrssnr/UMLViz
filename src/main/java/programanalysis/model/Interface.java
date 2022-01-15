package programanalysis.model;

import org.jetbrains.annotations.NotNull;
import graphgeneration.service.ProgramPartVisitor;

import java.util.List;

public class Interface extends ProgramPart {

	@NotNull
	private final List<Method> methods;

	public Interface(@NotNull String name, @NotNull Visibility visibility, boolean isFinal, boolean isStatic,
			@NotNull List<Method> methods) {
		super(name, visibility, isFinal, isStatic);
		this.methods = methods;
	}

	public List<Method> getMethods() {
		return methods;
	}

	@Override public <T> T accept(ProgramPartVisitor<T> visitor) {
		return visitor.visitInterface(this);
	}
}