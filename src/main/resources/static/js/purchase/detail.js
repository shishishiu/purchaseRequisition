function editHeader(){
	document.getElementById("detailForm").action="eheader";
	document.getElementById("detailForm").submit();
	
}
function editItem(purchaseItemId){
	$("#purchaseItemId").val(purchaseItemId);
	document.getElementById("detailForm").action="eitem";
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
function saveDeliverdDate(){
	document.getElementById("detailForm").action="saveDeliverdDate";
	document.getElementById("detailForm").submit();
}