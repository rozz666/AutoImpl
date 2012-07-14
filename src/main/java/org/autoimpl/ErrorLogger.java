package org.autoimpl;

import org.autoimpl.cst.Position;

public interface ErrorLogger {

	void logError(Position position, String string);
}
