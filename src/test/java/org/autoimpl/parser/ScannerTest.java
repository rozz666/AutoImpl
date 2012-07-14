package org.autoimpl.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

import org.autoimpl.cst.Identifier;
import org.autoimpl.cst.Position;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ScannerTest {

    private static final int IDENTIFIER = 7;

    Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    Scanner scanner;
    HashMap<String, Integer> keywords = new HashMap<String, Integer>();
    HashMap<Character, Integer> punctuation = new HashMap<Character, Integer>();

    private void initScanner(String source) {
        scanner = new Scanner(new StringReader(source), IDENTIFIER, keywords, punctuation);
    }

    private void advanceAndFailTwiceAt(int row, int column) throws IOException {
        advanceAndFailAt(row, column);
        advanceAndFailAt(row, column);
    }

    private void advanceAndFailAt(int row, int column) throws IOException {
        assertFalse(scanner.advance());
        assertAt(row, column);
        assertEquals(0, scanner.token());
        assertNull(scanner.value());
    }

    private void assertAt(int row, int column) {
        assertEquals(row, scanner.row());
        assertEquals(column, scanner.column());
    }

    private void advanceAndAssertNewlineAt(int row, int column) throws IOException {
        advanceAndAssertTokenAt('\n', row, column);
    }

    private void advanceAndAssertIdentifier(String id, int row, int column) throws IOException {
        assertTrue(scanner.advance());
        assertAt(row, column);
        assertEquals(IDENTIFIER, scanner.token());
        assertEquals(new Identifier(id, new Position(row, column)), scanner.value());
    }

    private void advanceAndAssertTokenAt(int token, int row, int column) throws IOException {
        assertTrue(scanner.advance());
        assertAt(row, column);
        assertEquals(token, scanner.token());
        assertEquals(new Position(row, column), scanner.value());
    }

    @Test
    public void testInitialState() {
        initScanner("");
        assertAt(1, 1);
        assertEquals(0, scanner.token());
        assertNull(scanner.value());
    }

    @Test
    public void testAdvanceWithEmptyInput() throws IOException {
        initScanner("");
        advanceAndFailTwiceAt(1, 1);
    }

    @Test
    public void testAdvanceWithSpaces() throws IOException {
        String source = "          ";
        initScanner(source);
        advanceAndFailTwiceAt(1, 1 + source.length());
    }

    @Test(expected = IOException.class)
    public void testAdvanceWithThrowingReader() throws IOException {
        final Reader reader = context.mock(Reader.class);
        scanner = new Scanner(reader, 0, keywords, punctuation);
        context.checking(new Expectations() {{
            oneOf(reader).read();
            will(throwException(new IOException()));
        }});
        scanner.advance();
    }

    @Test
    public void testLFs() throws IOException {
        initScanner("\n   \n\n  ");
        advanceAndAssertNewlineAt(1, 1);
        advanceAndAssertNewlineAt(2, 4);
        advanceAndAssertNewlineAt(3, 1);
        advanceAndFailTwiceAt(4, 3);
    }

    @Test
    public void testMixedCRsLFs() throws IOException {
        initScanner("\r\n   \r    \n\n  \n\r ");
        advanceAndAssertNewlineAt(1, 1);
        advanceAndAssertNewlineAt(2, 4);
        advanceAndAssertNewlineAt(3, 5);
        advanceAndAssertNewlineAt(4, 1);
        advanceAndAssertNewlineAt(5, 3);
        advanceAndFailTwiceAt(6, 2);
    }

    @Test
    public void testIdentifiers() throws IOException {
        initScanner(" asia kasia");
        advanceAndAssertIdentifier("asia", 1, 2);
        advanceAndAssertIdentifier("kasia", 1, 7);
        advanceAndFailTwiceAt(1, 12);
    }

    @Test
    public void testLFsBetweenIdentifiers() throws IOException {
        initScanner("xyz\nabc\rdef");
        advanceAndAssertIdentifier("xyz", 1, 1);
        advanceAndAssertNewlineAt(1, 4);
        advanceAndAssertIdentifier("abc", 2, 1);
        advanceAndAssertNewlineAt(2, 4);
        advanceAndAssertIdentifier("def", 3, 1);
    }

    @Test
    public void testKeywordTable() throws IOException {
        final int CLASS = 9;
        final int KEYWORD = 10;
        keywords.put("class", CLASS);
        keywords.put("keyword", KEYWORD);
        initScanner("class keyword");
        advanceAndAssertTokenAt(CLASS, 1, 1);
        advanceAndAssertTokenAt(KEYWORD, 1, 7);
    }

    @Test
    public void testBraces() throws IOException {
        final int OPENING_BRACE = 30;
        final int CLOSING_BRACE = 31;
        punctuation.put('{', OPENING_BRACE);
        punctuation.put('}', CLOSING_BRACE);
        initScanner("{ } xxa{aya}azz b{{b}}b z{{z}}z A{{A}}A B{{B}}B Y{}Y{}Y Z{}Z{}Z");
        advanceAndAssertTokenAt(OPENING_BRACE, 1, 1);
        advanceAndAssertTokenAt(CLOSING_BRACE, 1, 3);
        scanner.advance(); //xxa
        advanceAndAssertTokenAt(OPENING_BRACE, 1, 8);
        scanner.advance(); // aya
        advanceAndAssertTokenAt(CLOSING_BRACE, 1, 12);
        scanner.advance(); // azz
        for (int i = 0; i != 7 * 6; ++i)
            assertTrue(String.valueOf(i + 1), scanner.advance());

        assertFalse(scanner.advance());
    }
}
