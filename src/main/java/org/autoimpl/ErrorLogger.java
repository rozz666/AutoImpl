package org.autoimpl;

import org.autoimpl.parser.Position;

public interface ErrorLogger {

	void logError(Position position, String string);
}
