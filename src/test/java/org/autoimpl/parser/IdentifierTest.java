package org.autoimpl.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class IdentifierTest {

    @Test
    public void testGetters() {
        Identifier id = new Identifier("text", new Position(5, 6));
        assertEquals("text", id.name());
        assertEquals(new Position(5, 6), id.position());
        id = new Identifier("other", new Position(7, 1));
        assertEquals("other", id.name());
        assertEquals(new Position(7, 1), id.position());
    }

    @Test
    public void testEquals() {
        assertTrue(new Identifier("abc", new Position(3, 4)).equals(new Identifier("abc", new Position(3, 4))));
        assertFalse(new Identifier("abc", new Position(3, 4)).equals(new Identifier("xyz", new Position(3, 4))));
        assertFalse(new Identifier("abc", new Position(3, 4)).equals(new Identifier("abc", new Position(5, 4))));
    }
    
    @Test
    public void testToString() {
    	Position p = new Position(7, 3);
    	assertEquals("xyz at " + p.toString(), new Identifier("xyz", p).toString());
    }
}
