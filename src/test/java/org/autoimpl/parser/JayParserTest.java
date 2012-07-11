package org.autoimpl.parser;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.StringReader;

import org.autoimpl.ErrorLogger;
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
	public void shouldFailWhenParsingAnEmptyString() {
		context.checking(new Expectations() {
			{
				oneOf(logger).logError(with(equalTo(new Position(1, 1))),
						with(equalTo("missing 'specification'")));
			}
		});
		parse("");
		assertNull(spec);
	}

}
