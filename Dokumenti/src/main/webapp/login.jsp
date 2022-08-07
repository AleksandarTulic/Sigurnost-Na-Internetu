<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%

HttpSession se = request.getSession();
boolean flagLog = Boolean.valueOf((String)se.getAttribute("flagLogin"));
if (flagLog){
	response.sendRedirect("client/index_client.jsp");
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
					<h2>Login Korisnici</h2>
					<form name="loginForm" method="POST" action="j_security_check">
						<div class="form-group">
							<p>Username: <input type="text" name="j_username" class="form-control"/></p>
						</div>
						
						<div class="form-group">
							<p>Password: <input type="password" class="form-control" name="j_password"/></p>
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