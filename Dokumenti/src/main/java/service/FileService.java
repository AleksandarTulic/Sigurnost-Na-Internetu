package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;

import bean.CustomFile;
import logger.MyLogger;

public class FileService implements Serializable{
	public static final String SAVE_PATH = System.getProperty("catalina.home") + File.separator + 
			"MY_DATA" + File.separator + "repos";
	private static final long serialVersionUID = 1;
	
	public List<CustomFile> getFileRepositorium(String path) {
		try {
			List<String> aa = listF(new File(SAVE_PATH + File.separator + path));
			
			List<CustomFile> arr = new ArrayList<>();
			for (String i : aa) {
				File f = new File(i);
				String sub = i.substring(SAVE_PATH.length() + 1);
				String []l = sub.split("\\\\");
				String res = "";
				
				for (int j=0;j<l.length;j++) {
					res+= l[j];
					
					if (j != l.length - 1) {
						res+="-";
					}
				}
	
				//System.out.println(res);
				CustomFile c = new CustomFile(res, i.substring(SAVE_PATH.length() + 1), f.isDirectory() ? "folder" : "file");
				arr.add(c);
			}
	
			return arr;
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
		return new ArrayList<CustomFile>();
	}
	
	private List<String> listF(File f) throws Exception {
		List<String> arr = new ArrayList<String>();
		
		File []l = f.listFiles();
		for (File i : l) {
			if (i.isDirectory()) {
				arr.add(i.getPath());
				List<String> arrDFS = listF(i);
				
				arr.addAll(arrDFS);
			}else {
				arr.add(i.getPath());
			}
		}
		
		return arr;
	}
	
	public boolean checkIfFileExists(String file) {
		File f = new File(SAVE_PATH + File.separator + file);

		return f.exists();
	}
	
	public boolean isFileEmpty(String file) {
		try {
			File f = new File(SAVE_PATH + File.separator + file);
			if (f.listFiles().length == 0)
				return true;
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
		return false;
	}
	
	public boolean addFolder(String where, String folder) {
		try {
			File f = new File(SAVE_PATH + File.separator +
					("-".equals(where) ? folder : (getFileName(where) + File.separator + folder)));
			Path pathFolder = Paths.get(f.getPath());
			Files.createDirectories(pathFolder);
			
			return f.exists();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
		return false;
	}
	
	public boolean deleteFile(String file) {
		File f = new File(SAVE_PATH + File.separator + getFileName(file));
		
		if (f.exists()) {
			if (f.isDirectory()) {
				File []ff = f.listFiles();
				
				if (ff.length == 0) {
					return f.delete();
				}else {
					for (File i : ff) {
						i.delete();
					}
					
					return f.delete();
				}
			}else {
				return f.delete();
			}
		}
		
		return false;
	}
	
	public String getFileName(String file) {
		String []sp = file.split("-");
		String res = "";
		
		for (int i=0;i<sp.length;i++) {
			res += sp[i];
			
			if (i != sp.length - 1) {
				res += File.separator;
			}
		}
		
		return res;
	}
	
	public synchronized boolean tranasferDirectory(String from, String to) {
		try {
			File f1 = new File(SAVE_PATH + File.separator + getFileName(from));
			File f2 = new File(SAVE_PATH + File.separator + getFileName(to));
			
			if (f1.exists() && f2.exists()) {
				File []f = f1.listFiles();
				
				for (File i : f) {
					if (i.isFile()) {
						copy(i.getPath(), f2.getPath() + File.separator + i.getName());
					}
				}

				return true;
			}
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
		return false;
	}
	
	private void copy(String place1, String place2) throws Exception {
		InputStream in = new FileInputStream(place1);
		OutputStream out = new FileOutputStream(place2);
		
		byte []buffer = new byte[4096];
		while (in.read(buffer) != -1) {
			out.write(buffer);
		}
		
		in.close();
		out.flush();
		out.close();
	}
}
