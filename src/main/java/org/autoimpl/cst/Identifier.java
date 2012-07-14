package org.autoimpl.cst;


public class Identifier {

    private String name;
    private Position position;

    public Identifier(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public String name() {
        return name;
    }

    public Position position() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        Identifier id = (Identifier) obj;
        return name.equals(id.name) && position.equals(id.position);
    }

    @Override
    public String toString() {
    	return name + " at " + position.toString();
    }
}
