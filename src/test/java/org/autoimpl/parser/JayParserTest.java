package org.autoimpl.parser;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.autoimpl.cst.Specification;
import org.junit.Test;

public class JayParserTest {
	JayParser parser = new JayParser();
	Specification spec;

	private void parse(String source) {
		spec = parser.parseFile(new StringReader(source));
	}

	@Test
	public void shouldParseAnEmptySpecification() {
		parse("specification spec_name\nend");
		assertEquals(new Identifier("spec_name", new Position(1, 15)),
				spec.name());
	}

	@Test
	public void shouldFailWhenParsingAnEmptyString() {
		parse("");
		assertNull(spec);
	}

}
