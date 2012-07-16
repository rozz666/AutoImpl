package org.autoimpl.ast;

import java.util.ArrayList;
import java.util.List;

public class ClassDefinition {

	private String name;
	private List<InitializerDefinition> initializers = new ArrayList<InitializerDefinition>();

	public ClassDefinition(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}
	
	public List<InitializerDefinition> initializers() {
		return initializers;
	}
}
