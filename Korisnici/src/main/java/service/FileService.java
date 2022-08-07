package service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import logger.MyLogger;

public class FileService {
	public static final String SAVE_PATH = System.getProperty("catalina.home") + File.separator + 
			"MY_DATA" + File.separator + "repos";
	
	public boolean checkIfFileExists(String file) {
		File f = new File(SAVE_PATH + File.separator + file);

		return f.exists();
	}
	
	public boolean addFolder(String folder) {
		try {
			File f = new File(SAVE_PATH + File.separator + folder);
			Path pathFolder = Paths.get(f.getPath());
			Files.createDirectories(pathFolder);
			
			return f.exists();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
		return false;
	}
	
	//NOT USING FOR NOW
	public boolean deleteFolder(String path) {
		return deleteFile(SAVE_PATH + File.separator + path);
	}
	
	//NOT USING FOR NOW
	private boolean deleteFile(String folder) {
		File f = new File(folder);
		
		if (f.exists()) {
			if (f.isDirectory()) {
				File []ff = f.listFiles();
				
				for (File i : ff) {
					deleteFile(i.getPath());
				}
				
				return f.delete();
			}else {
				f.delete();
			}
		}
		
		return false;
	}
}
