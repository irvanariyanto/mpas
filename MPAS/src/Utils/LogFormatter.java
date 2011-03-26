package Utils;


import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author Liron Katav
 */
public class LogFormatter extends Formatter {

    /** LogFormater constructor */
    public LogFormatter() {
        super();
    }

    /**
     * The logger messages format with log level and time
     * @param record - log recored
     * @return string message include log level and time
     */
    @Override
    public String format(LogRecord record) {
        Date date = new Date();
        return date.toString() + "  " + record.getMessage() + " " + System.getProperty("line.separator");
    }
}

