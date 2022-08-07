package logger;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import service.FileService;

public class MyLogger {
	public final static Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
	private static final String LOG_PATH = FileService.SAVE_PATH + File.separator + ".." + File.separator + 
			"logger" + File.separator + System.currentTimeMillis() + "_Dokumenti.log";
	
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
