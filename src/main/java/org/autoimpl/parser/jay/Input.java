package org.autoimpl.parser.jay;

import java.io.IOException;

public interface Input {

    boolean advance() throws IOException;

    int token();

    Object value();

    public abstract int column();

    public abstract int row();
}
