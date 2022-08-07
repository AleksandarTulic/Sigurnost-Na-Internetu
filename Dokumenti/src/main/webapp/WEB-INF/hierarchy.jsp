<jsp:useBean id="fileService" class="service.FileService" scope='application'></jsp:useBean>
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>

<%
	String path = (String)request.getSession().getAttribute("path");
	Client cll = (Client)request.getSession().getAttribute("user");
	boolean flagRetrieve = true;
	if (cll != null)
		flagRetrieve = cll.isRetrieve();
	List<CustomFile> arr = fileService.getFileRepositorium(path == null ? "" : path);

%>

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
				if (flagRetrieve)
					out.println("<a href=\"../FileController?path=" + i.getPath() + "\">" + 
						sp[sp.length - 1] + "</a>");
				else
					out.println(sp[sp.length - 1]);
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
				if (flagRetrieve)
					out.println("<a href=\"../FileController?path=" + i.getPath() + "\">" + 
						sp[sp.length - 1] + "</a>");
				else
					out.println(sp[sp.length - 1]);
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