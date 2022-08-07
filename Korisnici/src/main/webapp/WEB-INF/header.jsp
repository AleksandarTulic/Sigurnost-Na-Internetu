
		<div class="sidenav">
	  		<button class="dropdown-btn">Admin System 
		    	<i class="fa fa-caret-down"></i>
		  	</button>
		  	
		  	<div class="dropdown-container">
		    	<a href="create.jsp?type=adminS">Create</a>
		    	<a href="view.jsp">View</a>
		  	</div>
 			<button class="dropdown-btn">Admin Documents 
		    	<i class="fa fa-caret-down"></i>
		  	</button>
		  	
		  	<div class="dropdown-container">
		    	<a href="create.jsp?type=adminD">Create</a>
		    	<a href="view.jsp">View</a>
		  	</div>
		  	<button class="dropdown-btn">Client 
		    	<i class="fa fa-caret-down"></i>
		  	</button>
		  	
		  	<div class="dropdown-container">
		    	<a href="create.jsp?type=client">Create</a>
		    	<a href="view.jsp">View</a>
		  	</div>
		  	
		  	<a href="logout.jsp">Log out</a>
		</div>
		
		<script>
/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
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