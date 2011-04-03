package Utils;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * this class based on the Singlton design pattern, This design pattern
 * ensures that only one instance of the TapuachLogger class is instantiated.
 *
 * to use the Logger use the line:
 * MyLogger.getInstance().level("message you want to log");
 * where level is the word "fine" , "severe" "warning" "info"
 *
 * @author Liron Katav
 *
 */
	public class MyLogger {

	    private Logger _logger;
	    private static MyLogger _instance = null;

	    /**
	     * constructor for the Singleton design pattern
	     */
	    protected MyLogger() {
	        _logger = Logger.getLogger("MyLogger");
	        try {
	            FileHandler fh = new FileHandler("MyLogger.log");
	            fh.setFormatter(new LogFormatter());
	            _logger.addHandler(fh);
	            _logger.setLevel(Level.FINE);
	        } catch (IOException e) {
	            System.out.println("I/O Error:" + e);
	        }
	    }

	    public static MyLogger getInstance() {
	        if (_instance == null) {
	            _instance = new MyLogger();
	        }
	        return _instance;
	    }

	    /**
	     * Log a INFO message
	     * Use INFO to log users activities
	     * @param msg - string message to log
	     */
	    public void info(String msg) {
	        _logger.info("INFO:  " + msg);
	    }

	    /**
	     * Log a FINE message
	     * Use FINE for debugging information
	     * @param msg - string message to log
	     */
	    public void fine(String msg) {
	        _logger.fine("FINE   :  " + msg);
	    }

	    /**
	     * Log a SEVERE message
	     * Use SEVERE when bugs or critical conditions occur
	     * @param msg - string message to log
	     */
	    public void severe(String msg) {
	        _logger.severe("SEVERE:  " + msg);
	    }

	    /**
	     * Log a WARNING message
	     * Use WARNING when warning needed to be shown
	     * @param msg - string message to log
	     */
	    public void warning(String msg) {
	        _logger.warning("WARNING:  " + msg);
	    }
	}

