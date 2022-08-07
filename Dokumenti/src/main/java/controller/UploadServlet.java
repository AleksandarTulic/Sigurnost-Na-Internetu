package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import logger.MyLogger;
import service.*;
import java.io.*;
import java.util.logging.Level;

import bean.*;

@WebServlet("/UploadServlet")
@MultipartConfig(maxFileSize=1024*1024*5)
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_PATH = System.getProperty("catalina.home") + File.separator + "MY_DATA" + 
	File.separator + "repos";
	
    public UploadServlet() {
        super();
    }
	
	private String getFileName(Part part) throws Exception {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
		}
		
		return "default.file";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String where = "index.jsp";
		try {
			FileService fs = new FileService();
			String value = request.getParameter("optCreate");
			String filePath = "";
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			boolean flag = true;
			if ("client".equals(user.getRole())) {
				Client client = (Client)session.getAttribute("user");
				if (!client.isCreate()) {
					flag = false;
					where = "errorOperation.jsp";
				}
			}
			
			if (flag) {
				if (value == null) {
					filePath = (String)session.getAttribute("path");
				}else {
					filePath = ".".equals(value) ? (String)session.getAttribute("path") : fs.getFileName(value);
				}
				
				File f = new File(UPLOAD_PATH + File.separator + filePath);
				if (f.exists()) {
					for (Part part : request.getParts()) {
						String fileName = getFileName(part);
						if (!"default.file".equals(fileName)) {
							part.write(UPLOAD_PATH + File.separator + filePath + File.separator + fileName);
							OtherService os = new OtherService();
							os.addUserAction(request.getRemoteUser(), "CREATE", filePath + File.separator + fileName);
						}
					}
				}
			}
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
		response.sendRedirect(where);
	}

}
