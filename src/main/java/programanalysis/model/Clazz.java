package programanalysis.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import graphgeneration.service.ProgramPartVisitor;

import java.util.List;

public class Clazz extends ProgramPart {

	private final boolean isAbstract;
	@NotNull
	private final List<Field> fields;
	@NotNull
	private final List<Method> methods;
	@Nullable
	private final Clazz superclass;
	@NotNull
	private final List<Interface> implementedInterfaces;

	public Clazz(
			@NotNull String name,
			@NotNull Visibility visibility,
			boolean isFinal,
			boolean isStatic,
			boolean isAbstract,
			@NotNull List<Field> fields,
			@NotNull List<Method> methods,
			@Nullable Clazz superclass,
			@NotNull List<Interface> implementedInterfaces) {
		super(name, visibility, isFinal, isStatic);
		this.isAbstract = isAbstract;
		this.fields = fields;
		this.methods = methods;
		this.superclass = superclass;
		this.implementedInterfaces = implementedInterfaces;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public List<Field> getFields() {
		return fields;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public Clazz getSuperclass() {
		return superclass;
	}

	public List<Interface> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	@Override public <T> T accept(ProgramPartVisitor<T> visitor) {
		return visitor.visitClass(this);
	}
}