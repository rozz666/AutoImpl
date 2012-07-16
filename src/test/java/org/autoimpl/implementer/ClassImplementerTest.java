package org.autoimpl.implementer;

import static org.junit.Assert.*;

import org.autoimpl.ast.ClassDefinition;
import org.autoimpl.cst.Identifier;
import org.autoimpl.cst.MethodInvocation;
import org.junit.Test;

public class ClassImplementerTest {

	private ClassImplementer impl = new ClassImplementer();

	@Test
	public void shouldCreateAClassWithAnInitializer() {
		String className = "A_class";
		String initName = "a_method";
		MethodInvocation methodInvocation = createMethodInvocation(className,
				initName);
		ClassDefinition def = impl.createClassWithInitializer(methodInvocation);
		assertEquals(className, def.name());
		assertEquals(1, def.initializers().size());
		assertEquals(initName, def.initializers().get(0).name());
	}

	private MethodInvocation createMethodInvocation(String className,
			String initName) {
		return new MethodInvocation(new Identifier(className, null),
				new Identifier(initName, null));
	}
}
