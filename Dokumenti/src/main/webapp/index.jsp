<% 
	HttpSession se = request.getSession();
	Object aa = se.getAttribute("flagLogin");
	if (aa == null){
		response.sendRedirect("tokenLogin.jsp");
	}

%>
<%@ include file="../WEB-INF/checkTypeUser.jsp" %>