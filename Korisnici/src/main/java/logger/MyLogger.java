package logger;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger {
	public final static Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
	private static final String LOG_PATH = System.getProperty("catalina.home") + File.separator + "MY_DATA" + File.separator + 
			"logger" + File.separator + System.currentTimeMillis() + "_Korisnici.log";
	
	static {
		LogManager.getLogManager().reset();
		logger.setLevel(Level.ALL);
		
		try {
			FileHandler fileHandler = new FileHandler(LOG_PATH);
			logger.addHandler(fileHandler);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
