<jsp:useBean id='otherService' class='service.OtherService' scope='application'></jsp:useBean> 
<%@ page import="bean.*" %>

<%

	HttpSession see = request.getSession();
	if (see.getAttribute("user") != null){
		User user = (User)see.getAttribute("user");
		
		if ("adminD".equals(user.getRole())){
			response.sendRedirect("../adminDocuments/index_adminD.jsp");
		}else if ("client".equals(user.getRole())){
			response.sendRedirect("../admin/index_client.jsp");
		}
	}

%>