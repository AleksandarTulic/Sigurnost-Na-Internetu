<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="qr" class="help.QR" scope='application'></jsp:useBean>
<jsp:useBean id='otherService' class='service.OtherService' scope='application'></jsp:useBean> 

<%
	
	HttpSession se = request.getSession();
	boolean flagLog = Boolean.valueOf((String)se.getAttribute("flagLogin"));
	if (flagLog){
		response.sendRedirect("index.jsp");
	}else if (otherService.checkIfActive(request.getRemoteUser())){
		se.setAttribute("flagLogin", true);
		
		response.sendRedirect("index.jsp");
	}
	
	if (request.getParameter("tokenValue") != null ){
		String key = otherService.getKey(request.getRemoteUser());
		if (qr.getTOTPCode(key).equals(request.getParameter("tokenValue"))){
			otherService.updateActive(request.getRemoteUser());
			response.sendRedirect("index.jsp");
		}else{
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
	}

%>

<!DOCTYPE html>
<html>
  <head>
  		<title>Korisnici</title>
  		
		<meta charset="ISO-8859-1">
  		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  </head>
  <body>
  		<div class="container">
			<div class="row">
				<div class="col-sm-2">
				</div>
				<div class="col-sm-8">
					<h2>Token verification</h2>
					<form name="loginForm" method="POST" action="tokenLogin.jsp">
						<div class="form-group">
							<p>Token: <input type="password" class="form-control" name="tokenValue"/></p>
						</div>
						<p>  <input style="width: 50%;margin-right: 25%;margin-left: 25%;" type="submit" value="Login" class="btn btn-default"/></p>
					</form>
				</div>
				<div class="col-sm-2">
				</div>
			</div>
		</div>      
   </body>
</html>