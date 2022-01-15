package graphgeneration.service;

import programanalysis.model.*;

public interface ProgramPartVisitor<T> {

	default T visit(ProgramPart programPart) {
		return programPart.accept(this);
	}

	T visitClass(Clazz clazz);

	T visitInterface(Interface interfacee);

	T visitMethod(Method method);

	T visitField(Field field);


}