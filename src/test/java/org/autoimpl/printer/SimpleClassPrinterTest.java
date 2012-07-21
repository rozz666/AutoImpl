package org.autoimpl.printer;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.autoimpl.ast.ClassDefinition;
import org.junit.Test;

public class SimpleClassPrinterTest {

	ByteArrayOutputStream output = new ByteArrayOutputStream();
	SimpleClassPrinter classPrinter = new SimpleClassPrinter(output);

	@Test
	public void shouldPrintClassAndNameAndEndForAnEmptyClass()
			throws UnsupportedEncodingException {
		classPrinter.printClass(new ClassDefinition("a_class_name"));
		assertEquals("class a_class_name\nend\n", output.toString("UTF-8"));
	}

}
