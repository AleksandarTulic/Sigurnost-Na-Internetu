package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logger.MyLogger;
import service.FileService;
import service.OtherService;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@WebServlet("/FileController")
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FileController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			FileService fileService = new FileService();
			String file = request.getParameter("path");
			String path = "";
			if (file != null){
				path = fileService.getFileName(file);
			}
			
			if (fileService.checkIfFileExists(path)){
				String []sp = file.split("-");
				OtherService os = new OtherService();
				os.addUserAction(request.getRemoteUser(), "RETRIEVE", path);
				
				response.setContentType("APPLICATION/OCTET-STREAM");   
			    response.setHeader("Content-Disposition","attachment; filename=\"" + sp[sp.length-1] + "\"");
			    
			    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(FileService.SAVE_PATH + File.separator + path);
			    java.io.OutputStream outStream = response.getOutputStream();
	
			    byte[] buffer = new byte[4096];
			    int bytesRead = -1;
	
			    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
			        outStream.write(buffer, 0, bytesRead);
			    }
	
			    fileInputStream.close();
			}
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
