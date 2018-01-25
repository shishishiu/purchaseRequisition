function back(){
	document.getElementById("detailForm").action = "back"
	document.getElementById("detailForm").submit();
}
function deleteItem(purchaseItemId){
	
	if($(".data").length<=1){

		if(!confirm("Quieres eliminar? Se elimina la requisición.")){
			return;
		}
		document.getElementById("detailForm").action = "delete"
		
	} else {
	
		if(!confirm("Quieres eliminar?")){
			return;
		}
		document.getElementById("detailForm").action = "deleteItem"

	}
	
	$("#purchaseItemId").val(purchaseItemId);
	document.getElementById("detailForm").submit();

}
function dl(){
	document.getElementById("detailForm").action="dl";
	document.getElementById("detailForm").submit();
}
