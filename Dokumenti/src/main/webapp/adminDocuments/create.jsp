<%@ include file="index_adminD.jsp" %>

<div class="main" style="padding: 0px;">
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				&nbsp;
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-1">&nbsp;</div>
			
			<%@ include file="../WEB-INF/hierarchyLinkDisabled.jsp" %>
			
			<div class="col-sm-4">
				<h2>Upload File</h2>
				<form action="../UploadServlet" method="post" enctype="multipart/form-data">
					<div class="form-group">
						<%
						
							out.println("<label for=\"optCreate\"> Which Directory: </label>");
							out.println("<select class=\"form-control\" id=\"optCreate\" name=\"optCreate\">");
							for (CustomFile i : arr){
								if ("folder".equals(i.getType())){
									out.println("<option value=\"" + i.getPath() + "\">" + i.getFile() + "</option>");
								}
							}
							
							out.println("<option value=\".\">" + "." + "</option>");
							out.println("</select>");
						%>
					</div>
					
					<div class="form-group">
						<input type="file" name="file" class="btn" style="width: 50%;margin-left: 25%;margin-right: 25%;color: white;background-color: #0000ff;">
					</div>

					<input type="submit" value="Upload File" 
					class="btn" style="width: 50%;margin-left: 25%;margin-right: 25%;color: white;background-color: #0000ff;">
				</form>
			</div>
			
			<div class="col-sm-1">
				&nbsp;
			</div>
			
		</div>
	</div>
</div>