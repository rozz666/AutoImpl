package org.autoimpl.printer;

import java.io.OutputStream;
import java.io.PrintStream;

import org.autoimpl.ast.ClassDefinition;

public class SimpleClassPrinter {

	private PrintStream out;

	public SimpleClassPrinter(OutputStream out) {
		this.out = new PrintStream(out);
	}

	public void printClass(ClassDefinition classDefinition) {
		out.println("class " + classDefinition.name());
		out.println("end");
	}

}
