package org.autoimpl.printer;

import org.autoimpl.ast.ClassDefinition;
import org.autoimpl.ast.ModuleDefinition;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class SimpleModulePrinterTest {

	Mockery context = new JUnit4Mockery();
	ClassPrinter classPrinter = context.mock(ClassPrinter.class);
	private ModuleDefinition moduleDef = new ModuleDefinition();
	SimpleModulePrinter printer = new SimpleModulePrinter(classPrinter);
	Sequence seq = context.sequence("classes");

	@Test
	public void shouldPrintNothingForAnEmptyModule() {
		printer.printModule(moduleDef);
	}

	@Test
	public void shouldPrintAllClassesInOrder() {
		final ClassDefinition classA = new ClassDefinition("a");
		final ClassDefinition classB = new ClassDefinition("b");
		final ClassDefinition classC = new ClassDefinition("c");
		moduleDef.addClass(classA);
		moduleDef.addClass(classB);
		moduleDef.addClass(classC);
		context.checking(new Expectations() {{
			oneOf(classPrinter).printClass(classA);	inSequence(seq);
			oneOf(classPrinter).printClass(classB);	inSequence(seq);
			oneOf(classPrinter).printClass(classC);	inSequence(seq);
		}});
		printer.printModule(moduleDef);
	}
}
