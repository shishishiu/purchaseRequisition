function searchUser(){
	document.getElementById("searchForm").submit();
}

function deleteUser(userId){
	$("#userId").val(userId);
	document.getElementById("searchForm").action="delete";
	document.getElementById("searchForm").submit();
	
}

function clearCondition(){
	$("#keyword").val("");

}
function edit(){
	$("#userId").val("");
	document.getElementById("searchForm").action="edit";
	document.getElementById("searchForm").submit();
	
}