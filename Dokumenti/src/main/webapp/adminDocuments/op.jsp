<jsp:useBean id="fileService" class="service.FileService" scope='application'></jsp:useBean>

<%

	if (request.getParameter("optDelete") != null){
		boolean res = fileService.deleteFile(request.getParameter("optDelete"));
		
		if (!res)
			response.sendRedirect("errorOperation.jsp");
		else
			response.sendRedirect("delete.jsp");
	}else{
		response.sendRedirect("index.jsp");
	}

%>