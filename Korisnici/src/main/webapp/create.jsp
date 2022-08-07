<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id='adminSService' class='service.AdminSService' scope='application'></jsp:useBean> 
<jsp:useBean id='adminDService' class='service.AdminDService' scope='application'></jsp:useBean> 
<jsp:useBean id='clientService' class='service.ClientService' scope='application'></jsp:useBean>  
<jsp:useBean id='otherService' class='service.OtherService' scope='application'></jsp:useBean>   
<jsp:useBean id="qr" class="help.QR" scope='application'></jsp:useBean>
<jsp:useBean id="crypt" class="help.Crypt" scope='request'></jsp:useBean>

<%
	boolean flag = true;
	boolean flagOp = false;
	if (request.getParameter("type") == null || (!request.getParameter("type").equals("adminS") && 
			!request.getParameter("type").equals("adminD") &&
			!request.getParameter("type").equals("client")))
		response.sendRedirect("index.jsp");
	
	if (request.getParameter("type") != null && request.getParameter("action") != null && request.getParameter("action").equals("do")){
		if (request.getParameter("type").equals("adminS")){
			flag = false;
			flagOp = adminSService.addUser(request.getParameter("username"), crypt.sha256(request.getParameter("pwd")));
		}else if (request.getParameter("type").equals("adminD")){
			flagOp = adminDService.addUser(request.getParameter("username"), crypt.sha256(request.getParameter("pwd")), request.getParameter("dir"));
			flag = false;
		}else if (request.getParameter("type").equals("client")){
			boolean flagCreate = request.getParameter("create") == null ? false : true;
			boolean flagRetrieve = request.getParameter("retrieve") == null ? false : true;
			boolean flagUpdate = request.getParameter("update") == null ? false : true;
			boolean flagDelete = request.getParameter("delete") == null ? false : true;
			flagOp = clientService.addUser(request.getParameter("username"), crypt.sha256(request.getParameter("pwd")), request.getParameter("dir"),
					request.getParameter("ip"), flagCreate, flagRetrieve, flagUpdate, flagDelete);
			flag = false;
		}else{
			response.sendRedirect("index.jsp");
		}
	}
	
	if (flagOp){
		String key = qr.generateQR(request.getParameter("username"));
		otherService.updateKey(request.getParameter("username"), key);
	}

%>
<%@ include file="index.jsp" %>

<div class="main">
	<div class="container">
	<div class="row">
	<div class="col-sm-2 bg bg-red">
	</div>
	<div class="col-sm-8">
	  <h2>Create User</h2>
	  <form action=<%="create.jsp?type="+request.getParameter("type") + "&action=do" %> method="post">
	    <div class="form-group">
	      		<label for="username">Username</label>
	      		<input type="text" class="form-control" maxlength="45" id="username" placeholder="Enter username" name="username" pattern="^[a-zA-Z0-9_]{2,45}$">
	    </div>
	    <div class="form-group">
	      		<label for="pwd">Password</label>
	      		<input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd" maxlength="45" pattern=".{6,45}">
	    </div>
	    
	    <%
	    	if (request.getParameter("type") != null){
		    	if (request.getParameter("type").equals("adminD")){
		    		out.println("<div class=\"form-group\">");
		    		out.println("<label for=\"dir\">Root Directory</label>");
		    		out.println("<input type=\"text\" maxlength=\"200\" pattern=\"[a-zA-Z0-9_\\\\]{1,}[a-zA-Z0-9_]$\" class=\"form-control\" id=\"dir\" placeholder=\"Enter path\" name=\"dir\">");
		    		out.println("</div>");
		    	}else if (request.getParameter("type").equals("client")){
		    		out.println("<div class=\"form-group\">");
		    		out.println("<label for=\"dir\">Root Directory</label>");
		    		out.println("<input type=\"text\" maxlength=\"200\" pattern=\"[a-zA-Z0-9_\\\\]{1,}[a-zA-Z0-9_]$\" class=\"form-control\" id=\"dir\" placeholder=\"Enter path\" name=\"dir\">");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"form-group\">");
		    		out.println("<label for=\"ip\">Ip Address</label>");
		    		out.println("<input type=\"text\" maxlength=\"45\" class=\"form-control\" id=\"ip\" placeholder=\"Enter ip\" name=\"ip\" pattern=\"(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))\">");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"form-check\">");
		    		out.println("<div class=\"col-sm-3\">");
		    		out.println("<input class=\"form-check-input\" type=\"checkbox\" id=\"create\" name=\"create\" value=\"true\" checked>");
		    		out.println("<label class=\"form-check-label\" for=\"create\">Create</label>");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"col-sm-3\">");
		    		out.println("<input class=\"form-check-input\" type=\"checkbox\" id=\"retrieve\" name=\"retrieve\" value=\"true\" checked>");
		    		out.println("<label class=\"form-check-label\" for=\"retrieve\">Retrieve</label>");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"col-sm-3\">");
		    		out.println("<input class=\"form-check-input\" type=\"checkbox\" id=\"update\" name=\"update\" value=\"true\" checked>");
		    		out.println("<label class=\"form-check-label\" for=\"update\">Update</label>");
		    		out.println("</div>");
		    		
		    		out.println("<div class=\"col-sm-3\">");
		    		out.println("<input class=\"form-check-input\" type=\"checkbox\" id=\"delete\" name=\"delete\" value=\"true\" checked>");
		    		out.println("<label class=\"form-check-label\" for=\"delete\">Delete</label>");
		    		out.println("</div>");
		    		out.println("</div>");
		    	}
	    	}
	    
	    %>
		
	    <br>
	    <button type="submit" class="btn btn-default">Create</button>
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
	
	<%
	
		if (flagOp){
			out.println("<div class=\"row\">");
			out.println("<div class=\"col-sm-2>\">");
			out.println("</div>");
			out.println("<div class=\"col-sm-8\">");
			out.println("<button class=\"btn btn-default\" style=\"min-width: 100px;\" data-toggle=\"collapse\" data-target=\"#demo\">See QR</button>");
			out.println("<div id=\"demo\" class=\"collapse\">");
			out.println("<img class=\"img-responsive\" src=\"FileController?path=" + request.getParameter("username") + "\" alt=\"None\" width=\"400\" height=\"400\">");
			out.println("</div>");
			out.println("</div>");
			out.println("<div class=\"col-sm-2>\">");
			out.println("</div>");
			out.println("</div>");
		}
	
	%>
	
	<div class="row">
		<div class="col-sm-12">
			&nbsp;
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-2">
		</div>
		
		<div class="col-sm-8">
			<%

				if (!flag && !flagOp){
					out.println("<div class=\"alert alert-danger\">");
					out.println("<strong>Problem!</strong> Problem with input data.");
					out.println("</div>");
				}
			
			%>
		</div>
		
		<div class="col-sm-2">
		</div>
	</div>
	</div>
</div>