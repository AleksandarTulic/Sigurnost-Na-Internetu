<jsp:useBean id='otherService' class='service.OtherService' scope='application'></jsp:useBean> 
<%@ page import="bean.*" %>

<%

	HttpSession see = request.getSession();
	if (see.getAttribute("user") != null){
		User user = (User)see.getAttribute("user");
		
		if ("adminS".equals(user.getRole())){
			response.sendRedirect("../admin/index_adminS.jsp");
		}else if ("adminD".equals(user.getRole())){
			response.sendRedirect("../adminDocuments/index_adminD.jsp");
		}
	}

%>