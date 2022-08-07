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
				<h2>Update File</h2>
				<form action="../UpdateController" method="post" enctype = "multipart/form-data">
					<div class="form-group">
						<%
						
							out.println("<label for=\"optUpdate\"> Which File: </label>");
							out.println("<select class=\"form-control\" id=\"optUpdate\" name=\"optUpdate\">");
							for (CustomFile i : arr){
								if ("file".equals(i.getType())){
									out.println("<option value=\"" + i.getPath() + "\">" + i.getFile() + "</option>");
								}
							}
							out.println("</select>");
						%>
					</div>
					
					<div class="form-group">
						<input type = "file" name = "file" size = "50" class="btn" style="width: 50%;margin-left: 25%;margin-right: 25%;color: white;background-color: #0000ff;">
					</div>

					<input type="submit" value="Update File" 
					class="btn" style="width: 50%;margin-left: 25%;margin-right: 25%;color: white;background-color: #0000ff;">
				
				</form>
			</div>
		</div>
	</div>
</div>