package org.autoimpl.cst;

import org.autoimpl.parser.Identifier;

public class MethodInvocation {

	private Identifier object, method;
	
	public MethodInvocation(Identifier object, Identifier method) {
		this.object = object;
		this.method = method;
	}

	public Object object() {
		return object;
	}

	public Object method() {
		return method;
	}

}
