package org.autoimpl.ast;

import java.util.ArrayList;
import java.util.List;

public class ModuleDefinition {

	private ArrayList<ClassDefinition> classes = new ArrayList<ClassDefinition>();

	public void addClass(ClassDefinition def)
	{
		classes.add(def);
	}
	
	public List<ClassDefinition> classes() {
		return classes;
	}

}
