package org.autoimpl.parser;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.autoimpl.cst.Specification;
import org.junit.Test;

public class JayParserTest {

	@Test
	public void shouldParseAnEmptySpecification() {
		JayParser parser = new JayParser();
		Specification s = parser.parseFile(new StringReader(
				"specification spec_name\nend"));
		assertEquals(new Identifier("spec_name", new Position(1, 15)), s.name());
	}

}
