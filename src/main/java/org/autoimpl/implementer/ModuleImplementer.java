package org.autoimpl.implementer;

import org.autoimpl.ast.ModuleDefinition;
import org.autoimpl.cst.MethodInvocation;
import org.autoimpl.cst.Specification;

public class ModuleImplementer {
	private ClassImplementer classImpl;

	public ModuleImplementer(ClassImplementer classImpl) {
		this.classImpl = classImpl;
	}

	public ModuleDefinition implement(Specification spec) {
		ModuleDefinition def = new ModuleDefinition();
		MethodInvocation methodInvocation = spec.statements().get(0);
		def.addClass(classImpl.createClassWithInitializer(methodInvocation));
		return def;
	}
}
