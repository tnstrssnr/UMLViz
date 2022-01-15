package programanalysis.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import graphgeneration.service.ProgramPartVisitor;

public class Field extends ProgramPart {

	@NotNull
	private final Type type;
	@Nullable
	private final String defaultValue;

	public Field(@NotNull String name, @NotNull Visibility visibility, boolean isFinal, boolean isStatic,
			@NotNull Type type, @Nullable String defaultValue) {
		super(name, visibility, isFinal, isStatic);
		this.type = type;
		this.defaultValue = defaultValue;
	}

	@NotNull
	public Type getType() {
		return type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	@Override public <T> T accept(ProgramPartVisitor<T> visitor) {
		return visitor.visitField(this);
	}
}