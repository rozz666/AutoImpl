package org.autoimpl.implementer;

import org.autoimpl.ast.ClassDefinition;
import org.autoimpl.ast.InitializerDefinition;
import org.autoimpl.cst.MethodInvocation;

public class ClassImplementer {

	public ClassDefinition createClassWithInitializer(
			MethodInvocation methodInvocation) {
		ClassDefinition def = new ClassDefinition(methodInvocation.object()
				.name());
		def.initializers().add(new InitializerDefinition(methodInvocation
				.method().name()));
		return def;
	}

}
