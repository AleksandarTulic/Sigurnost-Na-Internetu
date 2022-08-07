<%@ include file="index_adminS.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>

<%

	List<UserAction> arr = otherService.getClientAction();
%>

<div class="main" style="padding: 0px;">
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				&nbsp;
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-1">
				&nbsp;
			</div>
			
			<div class="col-sm-10">
				<table class="table table-striped adminS hideAll">
					<thead>
				      <tr>
				        <th>Username</th>
				        <th>Date</th>
				        <th>Time</th>
				        <th>Type</th>
				        <th>Document</th>
				      </tr>
				    </thead>
				    <tbody>
				    
				    	<%
				    	
				    		for (UserAction i : arr){
				    			out.println("<tr>");
				    			out.println("<td>" + i.getUsername() + "</td>");
				    			out.println("<td>" + i.getDateAction() + "</td>");
				    			out.println("<td>" + i.getTimeAction() + "</td>");
				    			out.println("<td>" + i.getTypeAction() + "</td>");
				    			out.println("<td>" + i.getDocumentName() + "</td>");
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