<div class="sidenav">
	<a href="index_adminD.jsp">Home</a>
	<button class="dropdown-btn">File Operations
    	<i class="fa fa-caret-down"></i>
  	</button>
  	
  	<div class="dropdown-container">
    	<a href="../adminDocuments/create.jsp">Create</a>
    	<a href="../adminDocuments/retrieve.jsp">Retrieve</a>
    	<a href="../adminDocuments/update.jsp">Update</a>
    	<a href="../adminDocuments/delete.jsp">Delete</a>
  	</div>
	<a href="repo.jsp">File Repositorium</a>
	<a href="../logout.jsp">Log out</a>
</div>

<script>
	var dropdown = document.getElementsByClassName("dropdown-btn");
	var i;
	
	for (i = 0; i < dropdown.length; i++) {
	  dropdown[i].addEventListener("click", function() {
	    this.classList.toggle("active");
	    var dropdownContent = this.nextElementSibling;
	    if (dropdownContent.style.display === "block") {
	      dropdownContent.style.display = "none";
	    } else {
	      dropdownContent.style.display = "block";
	    }
	  });
	}
</script>