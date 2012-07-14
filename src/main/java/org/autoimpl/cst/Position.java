package org.autoimpl.cst;

public class Position {

	private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        Position position = (Position) obj;
        return row == position.row && column == position.column;
    }

    @Override
	public String toString() {
		return row + ":" + column;
	}
}
