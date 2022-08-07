<jsp:useBean id="fileService" class="service.FileService" scope='application'></jsp:useBean>
<jsp:useBean id="otherService" class="service.OtherService" scope='application'></jsp:useBean>
<%@ page import="bean.*" %>
<%

	Client cl = (Client)session.getAttribute("user");
	
	if (request.getParameter("optDelete") != null && cl.isDelete()){
		boolean res = fileService.deleteFile(request.getParameter("optDelete"));
		
		if (!res)
			response.sendRedirect("errorOperation.jsp");
		else{
			String fileName = fileService.getFileName(request.getParameter("optDelete"));
			otherService.addUserAction(request.getRemoteUser(), "DELETE", fileName);
			response.sendRedirect("delete.jsp");
		}
	}else if (!cl.isDelete()){
		response.sendRedirect("errorOperation.jsp");
	}else{
		response.sendRedirect("index.jsp");
	}

%>