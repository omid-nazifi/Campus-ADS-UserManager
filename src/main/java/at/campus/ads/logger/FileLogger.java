package at.campus.ads.logger;

import java.io.IOException;
import java.util.logging.*;

public class FileLogger {

    static private FileHandler fileText;
    static private SimpleFormatter formatterText;

    static public void setup() throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileText = new FileHandler("full.log", true);

        // create a TXT formatter
        formatterText = new SimpleFormatter();
        fileText.setFormatter(formatterText);
        logger.addHandler(fileText);
    }
}
