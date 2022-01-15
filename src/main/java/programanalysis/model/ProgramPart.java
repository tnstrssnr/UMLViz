package programanalysis.model;

import org.jetbrains.annotations.NotNull;
import graphgeneration.service.ProgramPartVisitor;

public abstract class ProgramPart {

	@NotNull
	protected final String name;
	@NotNull
	protected final Visibility visibility;
	protected final boolean isFinal;
	protected final boolean isStatic;

	protected ProgramPart(@NotNull String name, @NotNull Visibility visibility, boolean isFinal, boolean isStatic) {
		this.name = name;
		this.visibility = visibility;
		this.isFinal = isFinal;
		this.isStatic = isStatic;
	}

	public String getName() {
		return name;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public abstract <T> T accept(ProgramPartVisitor<T> visitor);
}