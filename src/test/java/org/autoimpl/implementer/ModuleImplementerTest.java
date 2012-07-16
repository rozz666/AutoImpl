package org.autoimpl.implementer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.autoimpl.ast.ClassDefinition;
import org.autoimpl.ast.ModuleDefinition;
import org.autoimpl.cst.MethodInvocation;
import org.autoimpl.cst.Specification;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

public class ModuleImplementerTest {
	Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	ClassImplementer classImpl = context.mock(ClassImplementer.class);
	ModuleImplementer impl = new ModuleImplementer(classImpl);
	private Object classDef = context.mock(ClassDefinition.class);

	@Test
	public void shouldImplementOneClass() {
		final Specification spec = createSpecificationWithMessageToClass();
		context.checking(new Expectations() {
			{
				oneOf(classImpl).createClassWithInitializer(
						(MethodInvocation) spec.statements().get(0));
				will(returnValue(classDef));
			}
		});
		ModuleDefinition moduleDef = impl.implement(spec);
		List<ClassDefinition> classes = moduleDef.classes();
		assertEquals(1, classes.size());
		assertEquals(classDef, classes.get(0));
	}

	private Specification createSpecificationWithMessageToClass() {
		List<MethodInvocation> statements = new ArrayList<MethodInvocation>();
		statements.add(new MethodInvocation(null, null));
		return new Specification(null, statements);
	}

}
