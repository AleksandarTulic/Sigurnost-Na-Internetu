<%@ include file="index_client.jsp" %>

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
			
			<%@ include file="../WEB-INF/hierarchyLinkDisabled.jsp" %>
			
			<div class="col-sm-4">
				<h2>Remove File</h2>
				<form action="op.jsp" method="post">
					<div class="form-group">
						<%
						
							out.println("<label for=\"optDelete\"> Which Directory: </label>");
							out.println("<select class=\"form-control\" id=\"optDelete\" name=\"optDelete\">");
							for (CustomFile i : arr){
								if ("file".equals(i.getType())){
									out.println("<option value=\"" + i.getPath() + "\">" + i.getFile() + "</option>");
								}
							}
							out.println("</select>");
						%>
					</div>
					
					<input type="submit" value="Delete" 
					class="btn" style="width: 50%;margin-left: 25%;margin-right: 25%;color: white;background-color: #0000ff;">
				</form>
			</div>
			
			<div class="col-sm-1">
				&nbsp;
			</div>
			
		</div>
	</div>
</div>