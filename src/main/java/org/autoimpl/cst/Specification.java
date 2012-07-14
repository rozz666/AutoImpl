package org.autoimpl.cst;

import java.util.List;


public class Specification {

	private Identifier name;
	private List<MethodInvocation> statements;

	public Specification(Identifier name, List<MethodInvocation> statements) {
		this.name = name;
		this.statements = statements;
	}

	public Identifier name() {
		return name;
	}

	public List<MethodInvocation> statements() {
		return statements;
	}

}
