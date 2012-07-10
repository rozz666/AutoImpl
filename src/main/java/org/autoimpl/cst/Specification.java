package org.autoimpl.cst;

import org.autoimpl.parser.Identifier;

public class Specification {

	private Identifier name;

	public Identifier name() {
		return name;
	}

	public void setName(Identifier name) {
		this.name = name;
	}

}
