package org.autoimpl.printer;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.autoimpl.ast.ModuleDefinition;
import org.junit.Test;

public class ModulePrinterTest {

	@Test
	public void shouldPrintNothingForAnEmptyModule()
			throws UnsupportedEncodingException {
		ModuleDefinition def = new ModuleDefinition();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ModulePrinter printer = new ModulePrinter(os);
		printer.printModule(def);
		assertEquals("", os.toString("UTF-8"));
	}

}
