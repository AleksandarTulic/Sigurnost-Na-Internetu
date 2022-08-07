<jsp:useBean id="fileService" class="service.FileService" scope='application'></jsp:useBean>
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>

<%

	List<CustomFile> arr = fileService.getFileRepositorium("");

%>

<%@ include file="index_adminS.jsp" %>

<div class="main">
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				&nbsp;
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-1">&nbsp;</div>
			
			<div class="col-sm-10">
				<h2>File Hierarchy</h2>
				<br>
				<%
					
					String before = "";
					long inside = 1;
					out.println("<ul>");
					int br = 0;
					
					for (CustomFile i : arr){
						String []sp = i.getFile().split("\\\\");
						//System.out.println(sp.length + " " + inside + " " + i.getFile());
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
			
			<div class="col-sm-1">&nbsp;</div>
		</div>
	</div>
</div>