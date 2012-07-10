package org.autoimpl.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.autoimpl.parser.jay.Input;

public class Scanner implements Input {

    private LAReader reader;
    private final int identifierToken;
    private HashMap<String, Integer> keywords;
    private Map<Character, Integer> punctuation;
    private int currentColumn = 1;
    private int currentRow = 1;
    private int currentToken = 0;
    private Object currentValue = null;
    private int tokenLen = 0;

    static private class LAReader {
        private Reader reader;
        private int next = -1;

        public LAReader(Reader reader) {
            this.reader = reader;
        }

        public int lookAhead() throws IOException {
            prepareLookAhead();
            return next == '\r' ? '\n' : next;
        }

        private void prepareLookAhead() throws IOException {
            if (next == -1)
                next = reader.read();
        }

        public int read() throws IOException {
            prepareLookAhead();
            int ch = next;
            next = reader.read();
            if ((ch == '\r' && next == '\n') || (ch == '\n' && next == '\r'))
                next = reader.read();
            return ch == '\r' ? '\n' : ch;
        }
    }

    public Scanner(Reader reader, int identifierToken, Map<String, Integer> keywords,
                    Map<Character, Integer> punctuation) {
        this.reader = new LAReader(reader);
        this.identifierToken = identifierToken;
        this.keywords = new HashMap<String, Integer>(keywords);
        this.punctuation = new HashMap<Character, Integer>(punctuation);
    }

    @Override
    public boolean advance() throws IOException {
        movePastCurrentToken();
        currentToken = getToken();
        return currentToken != 0;
    }

    private void movePastCurrentToken() {
        if (currentToken == '\n') {
            currentColumn = 1;
            ++currentRow;
        } else {
            currentColumn += tokenLen;
        }
    }

    private int getToken() throws IOException {
        currentValue = null;
        skipWS();
        String id;
        switch (reader.lookAhead()) {
        case -1:
            tokenLen = 0;
            return 0;
        case '\n':
            tokenLen = 1;
            currentValue = new Position(currentRow, currentColumn);
            return reader.read();
        default:
            if (punctuation.containsKey((char) reader.lookAhead())) {
                char c = (char) reader.read();
                tokenLen = 1;
                currentValue = new Position(currentRow, currentColumn);
                return punctuation.get(c);
            }

            id = extractIdentifier();
            return getKeywordOrIdentifierToken(id);
        }
    }

    private int getKeywordOrIdentifierToken(String id) {
        Integer keywordToken = keywords.get(id);
        if (keywordToken != null) {
            currentValue = new Position(currentRow, currentColumn);
            return keywordToken;
        }
        return identifierToken;
    }

    private void skipWS() throws IOException {
        while (reader.lookAhead() == ' ') {
            reader.read();
            ++currentColumn;
        }
    }

    private String extractIdentifier() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append((char) reader.read());
        tokenLen = 1;
        while (reader.lookAhead() != -1 && reader.lookAhead() != ' ' && reader.lookAhead() != '\n'
                        &&  !punctuation.containsKey((char) reader.lookAhead())) {
            sb.append((char) reader.read());
            ++tokenLen;
        }

        String id = sb.toString();
        currentValue = new Identifier(id, new Position(currentRow, currentColumn));
        return id;
    }

    @Override
    public int token() {
        return currentToken;
    }

    @Override
    public Object value() {
        return currentValue;
    }

    @Override
    public int column() {
        return currentColumn;
    }

    @Override
    public int row() {
        return currentRow;
    }

}
