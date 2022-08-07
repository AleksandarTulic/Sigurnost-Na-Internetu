package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logger.MyLogger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;

import help.QR;

@WebServlet("/FileController")
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240;
	
    public FileController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	try {
	        String requestedFile = QR.SAVE_PATH + File.separator + request.getParameter("path") + ".png";
	
	        File file = new File(requestedFile);
	
	        String contentType = getServletContext().getMimeType(file.getName());
	
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }
	
	        response.reset();
	        response.setBufferSize(DEFAULT_BUFFER_SIZE);
	        response.setContentType(contentType);
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

	        BufferedInputStream input = null;
	        BufferedOutputStream output = null;
	
	        try {
	            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
	            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
	
	            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	            int length;
	            while ((length = input.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	        } finally {
	            close(output);
	            close(input);
	        }
    	}catch (Exception e) {
    		MyLogger.logger.log(Level.SEVERE, e.getMessage());
    	}
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
            	MyLogger.logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
