function show(what){
	hideThemAll();
	
	var ele = document.querySelector("." + what);
	ele.hidden = false;
}

function hideThemAll(){
	var ele = document.getElementsByClassName("hideAll");
	
	for (var i=0;i<ele.length;i++){
		ele[i].hidden = true;
	}
}