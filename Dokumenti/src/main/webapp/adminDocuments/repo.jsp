<jsp:useBean id="fileService" class="service.FileService" scope='application'></jsp:useBean>
<jsp:useBean id="ootherService" class="service.OtherService" scope='application'></jsp:useBean>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="bean.*" %>

<%

	if (request.getParameter("opt1") != null && request.getParameter("opt2") != null){
		boolean flag = fileService.tranasferDirectory(request.getParameter("opt1"), request.getParameter("opt2"));
	
		if (flag){
			ootherService.addUserAction(request.getRemoteUser(), "Transfer Directory", "From: " + fileService.getFileName(request.getParameter("opt1")) + 
					", To: " + fileService.getFileName(request.getParameter("opt1")));
		}
	}else if (request.getParameter("opt3") != null && request.getParameter("opt4") != null){
		boolean flag = fileService.addFolder(request.getParameter("opt3"), request.getParameter("opt4"));
		
		if (flag){
			ootherService.addUserAction(request.getRemoteUser(), "CREATE FOLDER", fileService.getFileName(request.getParameter("op3")) + 
					File.separator + request.getParameter("opt4"));
		}
	}else if (request.getParameter("opt5") != null){
		boolean flag = fileService.deleteFile(request.getParameter("opt5"));
		
		if (flag){
			ootherService.addUserAction(request.getRemoteUser(), "DELETE FOLDER", fileService.getFileName(request.getParameter("op5")));
		}
	}

	AdminD adminD = (AdminD)request.getSession().getAttribute("user");
	List<CustomFile> arr = fileService.getFileRepositorium(adminD == null ? "" : adminD.getRoot());

%>

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
			
			<div class="col-sm-6">
				<h2>File Hierarchy</h2>
				<br>
				<%
					
					String before = "";
					long inside = 1;
					out.println("<ul>");
					int br = 0;
					
					for (CustomFile i : arr){
						String []sp = i.getFile().split("\\\\");
						if (sp.length >= inside && sp.length >= 1){
							if ("folder".equals(i.getType())){
								out.println("<li>");
								out.println("<span class=\"btn\" onclick=\"hideFolder(" + br + ")\" style=\"background-color: #0000ff; color:white;\" data-toggle=\"tooltip\" title=\"Folder\">" + 
								sp[sp.length - 1] + "</span>");
								out.println("<ul id=\"ID_" + br + "\">");
								
								if (fileService.isFileEmpty(i.getFile())){
									out.println("</ul>");
									out.println("</li>");
								}
							}else{
								out.println("<li>");
								out.println("<a href=\"../FileController?path=" + i.getPath() + "\">" + 
										sp[sp.length - 1] + "</a>");
								out.println("</li>");
							}
							
							inside = sp.length;
						}else if (inside > sp.length){
							while (inside != sp.length){
								out.println("</ul>");
								out.println("</li>");
								inside--;
							}
							
							if ("folder".equals(i.getType())){
								out.println("<li>");
								out.println("<span class=\"btn\" onclick=\"hideFolder(" + br + ")\" style=\"background-color: #0000ff; color:white;\" data-toggle=\"tooltip\" title=\"Folder\">" + 
								sp[sp.length - 1] + "</span>");
								out.println("<ul id=\"ID_" + br + "\">");
							}else{
								out.println("<li>");
								out.println("<a href=\"../FileController?path=" + i.getPath() + "\">" + 
										sp[sp.length - 1] + "</a>");
								out.println("</li>");
							}
						}
						

						before = i.getFile();
						br++;
					}
					
					out.println("</ul>");
				
				%>
				
				<script>
					$(document).ready(function(){
					  $('[data-toggle="tooltip"]').tooltip();   
					});
					
					function hideFolder(value){
						var ele = document.getElementById("ID_" + value);
						if (ele.style.display == "none"){
							ele.style.display = "block";
						}else{
							ele.style.display = "none";
						}
					}
				</script>
			</div>
			
			<div class="col-sm-4">
				<h2>Transfer Files</h2>
				<form action="repo.jsp" method="post">
					<div class="form-group">
						<%
						
							out.println("<label for=\"opt1\"> From: </label>");
							out.println("<select class=\"form-control\" id=\"opt1\" name=\"opt1\">");
							for (CustomFile i : arr){
								if ("folder".equals(i.getType())){
									out.println("<option value=\"" + i.getPath() + "\">" + i.getFile() + "</option>");
								}
							}
							out.println("</select>");
							
							out.println("<label for=\"opt2\"> To: </label>");
							out.println("<select class=\"form-control\" id=\"opt2\" name=\"opt2\">");
							for (CustomFile i : arr){
								if ("folder".equals(i.getType())){
									out.println("<option value=\"" + i.getPath() + "\">" + i.getFile() + "</option>");
								}
							}
							out.println("</select>");
						
						%>
					</div>
					
					<input type="submit" value="Transfer" 
					class="btn" style="width: 50%;margin-left: 25%;margin-right: 25%;color: white;background-color: #0000ff;">
				</form>
				
				<h2>Add Directory</h2>
				<form action="repo.jsp" method="post">
					<div class="form-group">
					<%
						
							out.println("<label for=\"opt3\"> Where To Put: </label>");
							out.println("<select class=\"form-control\" id=\"opt3\" name=\"opt3\">");
							for (CustomFile i : arr){
								if ("folder".equals(i.getType())){
									out.println("<option value=\"" + i.getPath() + "\">" + i.getFile() + "</option>");
								}
							}
							out.println("<option value=\"" + "-" + "\">" + "\\." + "</option>");
							out.println("</select>");
					%>
					
						<label for="opt4">Directory Name:</label>
						<input class="form-control" type="text" name="opt4" value="" maxlength="50" pattern="[a-zA-Z0-9_\\]*">
					</div>
					
					<input type="submit" value="Add" 
					class="btn" style="width: 50%;margin-left: 25%;margin-right: 25%;color: white;background-color: #0000ff;">
				</form>
				
				<h2>Remove Directory</h2>
				<form action="repo.jsp" method="post">
					<div class="form-group">
						<%
						
							out.println("<label for=\"opt5\"> Which Directory: </label>");
							out.println("<select class=\"form-control\" id=\"opt5\" name=\"opt5\">");
							for (CustomFile i : arr){
								if ("folder".equals(i.getType())){
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
		</div>
	</div>
</div>