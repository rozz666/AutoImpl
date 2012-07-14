package org.autoimpl.parser;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.StringReader;

import org.autoimpl.ErrorLogger;
import org.autoimpl.cst.Identifier;
import org.autoimpl.cst.MethodInvocation;
import org.autoimpl.cst.Position;
import org.autoimpl.cst.Specification;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Test;

public class JayParserTest {
	Mockery context = new Mockery();
	ErrorLogger logger = context.mock(ErrorLogger.class);
	JayParser parser = new JayParser(logger);
	Specification spec;

	private void parse(String source) {
		spec = parser.parseFile(new StringReader(source));
	}

	private void assertErrorWithSource(String source, final int row,
			final int column, final String message) {
		context.checking(new Expectations() {
			{
				oneOf(logger).logError(
						with(equalTo(new Position(row, column))),
						with(equalTo(message)));
			}
		});
		parse(source);
		assertNull(spec);
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void shouldParseAnEmptySpecification() {
		parse("specification spec_name\nend");
		assertEquals(new Identifier("spec_name", new Position(1, 15)),
				spec.name());
	}

	@Test
	public void shouldIgnoreEolsBeforeSpecification() {
		parse("\n\nspecification a\nend");
		assertNotNull(spec);
	}

	@Test
	public void shouldFailWhenParsingAnEmptyString() {
		assertErrorWithSource("", 1, 1, "missing 'specification'");
	}

	@Test
	public void shouldFailWhenMissingSpecificationName() {
		assertErrorWithSource("specification ", 1, 15,
				"missing specification name");
	}

	@Test
	public void shouldFailWhenMissingEnd() {
		assertErrorWithSource("specification x\n", 2, 1, "missing 'end'");
	}

	@Test
	public void shouldFailWhenMissingEolAfterSpecificationName() {
		assertErrorWithSource("specification x", 1, 16, "missing end-of-line");
	}
	
	@Test
	public void shouldParseSendingAMessageToAnObject() {
		parse("specification x\nAn_object new\nend");
		assertEquals(1, spec.statements().size());
		MethodInvocation m = (MethodInvocation)spec.statements().get(0);
		assertEquals(new Identifier("An_object", new Position(2, 1)), m.object());
		assertEquals(new Identifier("new", new Position(2, 11)), m.method());
	}

}
