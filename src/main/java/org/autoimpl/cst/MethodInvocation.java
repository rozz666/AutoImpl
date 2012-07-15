package org.autoimpl.cst;


public class MethodInvocation {

	private Identifier object, method;
	
	public MethodInvocation(Identifier object, Identifier method) {
		this.object = object;
		this.method = method;
	}

	public Identifier object() {
		return object;
	}

	public Identifier method() {
		return method;
	}

}
