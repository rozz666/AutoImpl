package org.autoimpl.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {

    @Test
    public void testGetters() {
        Position p = new Position(1, 2);
        assertEquals(1, p.row());
        assertEquals(2, p.column());
        p = new Position(6, 9);
        assertEquals(6, p.row());
        assertEquals(9, p.column());
    }

    @Test
    public void testEquals() {
        assertTrue(new Position(3, 6).equals(new Position(3, 6)));
        assertFalse(new Position(1, 6).equals(new Position(3, 6)));
        assertFalse(new Position(3, 2).equals(new Position(3, 6)));
    }
    
    @Test
    public void testToString() {
    	assertEquals("6:9", new Position(6, 9).toString());
    }

}
