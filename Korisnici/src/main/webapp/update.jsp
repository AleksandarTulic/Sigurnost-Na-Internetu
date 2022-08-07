<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="bean.*" %>
<jsp:useBean id='adminSService' class='service.AdminSService' scope='application'></jsp:useBean> 
<jsp:useBean id='adminDService' class='service.AdminDService' scope='application'></jsp:useBean> 
<jsp:useBean id='clientService' class='service.ClientService' scope='application'></jsp:useBean> 
<jsp:useBean id="crypt" class="help.Crypt" scope='request'></jsp:useBean>

<%
	AdminS adminS = null;
	AdminD adminD = null;
	Client client = null;
	boolean flag = false;
	
	if (request.getParameter("type") != null && request.getParameter("username") != null && request.getParameter("action") != null){
		if ("adminS".equals(request.getParameter("type"))){
			if ("do".equals(request.getParameter("action")) && adminSService.checkIfExists(request.getParameter("oldUsername"))){
				String oldUsername = request.getParameter("oldUsername");
				String newUsername = request.getParameter("username");
				String password = crypt.sha256(request.getParameter("pwd"));
				
				adminS = adminSService.updateUser(oldUsername, newUsername, password);
				
				if (adminS == null)
					response.sendRedirect("errorOperation.jsp");
			}else {
				response.sendRedirect("view.jsp");
			}
		}else if ("adminD".equals(request.getParameter("type"))){
			if ("do".equals(request.getParameter("action")) && adminDService.checkIfExists(request.getParameter("oldUsername"))){
				String oldUsername = request.getParameter("oldUsername");
				String newUsername = request.getParameter("username");
				String password = crypt.sha256(request.getParameter("pwd"));
				String path = request.getParameter("dir");

				adminD = adminDService.updateUser(oldUsername, newUsername, password, path);
				
				if (adminD == null)
					response.sendRedirect("errorOperation.jsp");
			}else {
				response.sendRedirect("view.jsp");
			}
		}else if ("client".equals(request.getParameter("type"))){
			if ("do".equals(request.getParameter("action")) && clientService.checkIfExists(request.getParameter("oldUsername"))){
				String oldUsername = request.getParameter("oldUsername");
				String newUsername = request.getParameter("username");
				String password = crypt.sha256(request.getParameter("pwd"));
				String path = request.getParameter("dir");
				String domen = request.getParameter("ip");
				boolean flagCreate = request.getParameter("create") == null ? false : true;
				boolean flagRetrieve = request.getParameter("retrieve") == null ? false : true;
				boolean flagUpdate = request.getParameter("update") == null ? false : true;
				boolean flagDelete = request.getParameter("delete") == null ? false : true;

				client = clientService.updateUser(oldUsername, newUsername, password, path, domen, flagCreate, 
						flagRetrieve, flagUpdate, flagDelete);
				
				if (client == null)
					response.sendRedirect("errorOperation.jsp");
			}else{
				response.sendRedirect("view.jsp");
			}
		}else{
			response.sendRedirect("view.jsp");
		}
	}else if (request.getParameter("type") != null && request.getParameter("username") != null){
		//no need for adminS because we only puy username - old
		if ("adminS".equals(request.getParameter("type"))){
			adminS = adminSService.getUser(request.getParameter("username"));
		}else if ("adminD".equals(request.getParameter("type"))){
			adminD = adminDService.getUser(request.getParameter("username"));
		}else if ("client".equals(request.getParameter("type"))){
			client = clientService.getUser(request.getParameter("username"));
		}else{
			response.sendRedirect("view.jsp");
		}

		if (adminS == null && adminD == null && client == null){
			response.sendRedirect("view.jsp");
		}
	}else{
		response.sendRedirect("view.jsp");
	}

%>

<%@ include file="index.jsp" %>  

<div class="main">
	<div class="container">
	<div class="row">
	<div class="col-sm-2 bg bg-red">
	</div>
	<div class="col-sm-8">
	  <h2>Update User</h2>
	  <form action=<%="update.jsp?type="+request.getParameter("type") + "&action=do&oldUsername=" + request.getParameter("username") %> method="post">
	    <div class="form-group">
	      		<label for="username">Username</label>
	      		<input type="text" class="form-control" pattern="^[a-zA-Z0-9_]{2,45}$" maxlength="45" id="username" placeholder="Enter username" value=<%=request.getParameter("username") %> name="username">
	    </div>
	    <div class="form-group">
	      		<label for="pwd">Password</label>
	      		<input type="password" maxlength="45" pattern=".{6,45}" class="form-control" id="pwd" placeholder="Enter password" name="pwd">
	    </div>
	    
	    <%
	    	if (request.getParameter("type") != null){
		    	if (adminD != null){
		    		out.println("<div class=\"form-group\">");
		    		out.println("<label for=\"dir\">Root Directory</label>");
		    		out.println("<input type=\"text\" class=\"form-control\" id=\"dir\" maxlength=\"200\" pattern=\"[a-zA-Z0-9_\\\\]{1,}[a-zA-Z0-9_]$\" placeholder=\"Enter path\" value=\"" + adminD.getRoot() + "\" name=\"dir\">");
		    		out.println("</div>");
		    	}else if (client != null){
		    		out.println("<div class=\"form-group\">");
		    		out.println("<label for=\"dir\">Root Directory</label>");
		    		out.println("<input type=\"text\" class=\"form-control\" id=\"dir\" maxlength=\"200\" pattern=\"[a-zA-Z0-9_\\\\]{1,}[a-zA-Z0-9_]$\" placeholder=\"Enter path\" value=\"" + client.getRoot() + "\" name=\"dir\">");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"form-group\">");
		    		out.println("<label for=\"ip\">Ip Address</label>");
		    		out.println("<input type=\"text\" class=\"form-control\" maxlength=\"45\" id=\"ip\" placeholder=\"Enter ip\" name=\"ip\" value=\"" + client.getDomen() + "\" pattern=\"[a-fA-F0-9:]*\">");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"form-check\">");
		    		out.println("<div class=\"col-sm-3\">");
		    		out.println("<input class=\"form-check-input\" type=\"checkbox\" id=\"create\" name=\"create\" value=\"true\" " + (true == client.isCreate() ? "checked" : "") + ">");
		    		out.println("<label class=\"form-check-label\" for=\"create\">Create</label>");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"col-sm-3\">");
		    		out.println("<input class=\"form-check-input\" type=\"checkbox\" id=\"retrieve\" name=\"retrieve\" value=\"true\" " + (true == client.isRetrieve() ? "checked" : "") + ">");
		    		out.println("<label class=\"form-check-label\" for=\"retrieve\">Retrieve</label>");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"col-sm-3\">");
		    		out.println("<input class=\"form-check-input\" type=\"checkbox\" id=\"update\" name=\"update\" value=\"true\" " + (true == client.isUpdate() ? "checked" : "") + ">");
		    		out.println("<label class=\"form-check-label\" for=\"update\">Update</label>");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"col-sm-3\">");
		    		out.println("<input class=\"form-check-input\" type=\"checkbox\" id=\"delete\" name=\"delete\" value=\"true\" " + (true == client.isDelete() ? "checked" : "") + ">");
		    		out.println("<label class=\"form-check-label\" for=\"delete\">Delete</label>");
		    		out.println("</div>");
		    		out.println("</div>");
		    	}
	    	}
	    
	    %>
		
	    <br>
	    <button type="submit" class="btn btn-default">Update</button>
	  </form>
	  </div>
	  <div class="col-sm-2 bg bg-green">
	  </div>
	</div>
	
	<div class="row">
		<div class="col-sm-12">
			&nbsp;
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-2">
			&nbsp;
		</div>
		
		<div class="col-sm-8">
			<%

				if ("do".equals(request.getParameter("action"))){
					out.println("<div class=\"alert alert-success\">");
					out.println("<strong>Success!</strong> Operation Successful.");
					out.println("</div>");
				}
			
			%>
		</div>
		
		<div class="col-sm-2">
			&nbsp;
		</div>
	</div>
	</div>
</div>