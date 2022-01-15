package programanalysis.model;

import org.jetbrains.annotations.NotNull;
import graphgeneration.service.ProgramPartVisitor;

import java.util.List;

public class Method extends ProgramPart {

	private Type returnType;
	private final boolean isAbstract;
	private final List<Parameter> parameters;

	public Method(@NotNull String name, @NotNull Visibility visibility, boolean isFinal, boolean isStatic,
			Type returnType, boolean isAbstract, List<Parameter> parameters) {
		super(name, visibility, isFinal, isStatic);
		this.returnType = returnType;
		this.isAbstract = isAbstract;
		this.parameters = parameters;
	}

	public Type getReturnType() {
		return returnType;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	@Override public <T> T accept(ProgramPartVisitor<T> visitor) {
		return visitor.visitMethod(this);
	}
}