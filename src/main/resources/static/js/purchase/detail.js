function back(){
	document.getElementById("detailForm").action = "back"
	document.getElementById("detailForm").submit();
}
function deleteItem(purchaseItemId){
	
	if(!confirm("Quieres eliminar?")){
		return;
	}
	
	$("#purchaseItemId").val(purchaseItemId);
	document.getElementById("detailForm").action = "deleteItem"
	document.getElementById("detailForm").submit();

}