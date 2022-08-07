<%@ include file="index.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>
<jsp:useBean id='adminSService' class='service.AdminSService' scope='application'></jsp:useBean> 
<jsp:useBean id='adminDService' class='service.AdminDService' scope='application'></jsp:useBean> 
<jsp:useBean id='clientService' class='service.ClientService' scope='application'></jsp:useBean> 

<%

	List<AdminS> arr1 = adminSService.getUsers();
	List<AdminD> arr2 = adminDService.getUsers();
	List<Client> arr3 = clientService.getUsers();
	
	if (request.getParameter("type") != null && request.getParameter("action") != null && request.getParameter("username") != null){
		if ("adminS".equals(request.getParameter("type"))){
			if ("update".equals(request.getParameter("action"))){
				
			}else if ("delete".equals(request.getParameter("action"))){
				boolean flag = adminSService.deleteUser(request.getParameter("username"));
				
				if (flag && request.getRemoteUser().equals(request.getParameter("username"))){
					session.invalidate();
				}
				response.sendRedirect("view.jsp");
			}else{
				response.sendRedirect("view.jsp");
			}
		}else if ("adminD".equals(request.getParameter("type"))){
			if ("update".equals(request.getParameter("action"))){
				
			}else if ("delete".equals(request.getParameter("action"))){
				adminDService.deleteUser(request.getParameter("username"));
				response.sendRedirect("view.jsp");
			}else{
				response.sendRedirect("view.jsp");
			}
		}else if ("client".equals(request.getParameter("type"))){
			if ("update".equals(request.getParameter("action"))){
				
			}else if ("delete".equals(request.getParameter("action"))){
				clientService.deleteUser(request.getParameter("username"));
				response.sendRedirect("view.jsp");
			}else{
				response.sendRedirect("view.jsp");
			}
		}else{
			response.sendRedirect("view.jsp");
		}
	}

%>	

<div class="main">
	<div class="container">
		<div class="row">
			<div class="col-sm-1">
				&nbsp;
			</div>
			
			<div class="col-sm-10">
				<h3>User Tabs</h3>
						<ul class="nav nav-tabs">
				    <li><a onclick="show('adminS')">Admin System</a></li>
				    <li><a onclick="show('adminD')">Admin Documents</a></li>
				    <li><a onclick="show('client')">Client</a></li>
				</ul>
				
				<table class="table table-striped adminS hideAll">
					<thead>
				      <tr>
				        <th>Username</th>
				        <th>Role</th>
				        <th>Update</th>
				        <th>Delete</th>
				      </tr>
				    </thead>
				    <tbody>
				    	<%
				    	
				    		for (AdminS i : arr1){
				    			out.println("<tr>");
				    			out.println("<td>" + i.getUsername() + "</td>");
				    			out.println("<td>" + i.getRole() + "</td>");
				    			out.println("<td><a href=\"update.jsp?type=adminS&username=" + i.getUsername() + "\" class=\"btn btn-warning\">Update</a></td>");
				    			out.println("<td><a href=\"view.jsp?action=delete&type=adminS&username=" + i.getUsername() + "\" class=\"btn btn-danger\">Delete</a></td>");
				    			out.println("</tr>");
				    		}
				    	
				    	%>
				    </tbody>
				</table>
				
				<table class="table table-striped adminD hideAll">
					<thead>
				      <tr>
				        <th>Username</th>
				        <th>Role</th>
				        <th>Update</th>
				        <th>Delete</th>
				      </tr>
				    </thead>
				    <tbody>
				    	<%
				    	
				    		for (AdminD i : arr2){
				    			out.println("<tr>");
				    			out.println("<td>" + i.getUsername() + "</td>");
				    			out.println("<td>" + i.getRole() + "</td>");
				    			out.println("<td><a href=\"update.jsp?type=adminD&username=" + i.getUsername() + "\" class=\"btn btn-warning\">Update</a></td>");
				    			out.println("<td><a href=\"view.jsp?action=delete&type=adminD&username=" + i.getUsername() + "\" class=\"btn btn-danger\">Delete</a></td>");
				    			out.println("</tr>");
				    		}
				    	
				    	%>
				    </tbody>
				</table>
				
				<table class="table table-striped client hideAll">
					<thead>
				      <tr>
				        <th>Username</th>
				        <th>Role</th>
				        <th>Update</th>
				        <th>Delete</th>
				      </tr>
				    </thead>
				    <tbody>
				    	<%
				    	
				    		for (Client i : arr3){
				    			out.println("<tr>");
				    			out.println("<td>" + i.getUsername() + "</td>");
				    			out.println("<td>" + i.getRole() + "</td>");
				    			out.println("<td><a href=\"update.jsp?type=client&username=" + i.getUsername() + "\" class=\"btn btn-warning\">Update</a></td>");
				    			out.println("<td><a href=\"view.jsp?action=delete&type=client&username=" + i.getUsername() + "\" class=\"btn btn-danger\">Delete</a></td>");
				    			out.println("</tr>");
				    		}
				    	
				    	%>
				    </tbody>
				</table>
			</div>
			
			<div class="col-sm-1">
				&nbsp;
			</div>
		</div>
	</div>
</div>