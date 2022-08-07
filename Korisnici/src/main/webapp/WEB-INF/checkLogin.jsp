<%

	HttpSession se = request.getSession();
	Boolean value = (Boolean)se.getAttribute("flagLogin");
	
	if (value == null){
		response.sendRedirect("tokenLogin.jsp");
	}

%>