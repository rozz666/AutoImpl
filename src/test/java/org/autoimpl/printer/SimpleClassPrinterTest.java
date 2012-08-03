package org.autoimpl.printer;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.autoimpl.ast.ClassDefinition;
import org.autoimpl.ast.InitializerDefinition;
import org.junit.Test;

public class SimpleClassPrinterTest {

	ByteArrayOutputStream output = new ByteArrayOutputStream();
	SimpleClassPrinter classPrinter = new SimpleClassPrinter(output);

	private void checkOutput(String text) throws UnsupportedEncodingException {
		assertEquals(text, output.toString("UTF-8"));
	}

	@Test
	public void shouldPrintClassAndNameAndEndForAnEmptyClass()
			throws UnsupportedEncodingException {
		classPrinter.printClass(new ClassDefinition("a_class_name"));
		checkOutput("class a_class_name\nend\n");
	}

	@Test
	public void shouldPrintInitializers() throws UnsupportedEncodingException
	{
		ClassDefinition def = new ClassDefinition("X");
		def.initializers().add(new InitializerDefinition("abc"));
		def.initializers().add(new InitializerDefinition("xyz"));
		classPrinter.printClass(def);
		checkOutput("class X\n    init abc\n    end\n    init xyz\n    end\nend\n");
	}

}
