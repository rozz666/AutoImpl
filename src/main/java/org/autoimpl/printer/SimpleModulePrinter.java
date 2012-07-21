package org.autoimpl.printer;

import org.autoimpl.ast.ClassDefinition;
import org.autoimpl.ast.ModuleDefinition;

public class SimpleModulePrinter {

	private ClassPrinter classPrinter;

	public SimpleModulePrinter(ClassPrinter classPrinter) {
		this.classPrinter = classPrinter;
	}

	public void printModule(ModuleDefinition def) {
		for (ClassDefinition classDef : def.classes()) {
			classPrinter.printClass(classDef);
		}
	}

}
