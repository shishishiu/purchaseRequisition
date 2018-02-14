$(function(){
	
	$('#quantity').change(function() {
		calcularTotalPrice();
	});
	$('#unitPrice').change(function() {
		calcularTotalPrice();
	});
}) 


function save(){
	document.getElementById("editItemForm").submit();
}
function editHeader(){
	document.getElementById("editItemForm").action="eheader";
	document.getElementById("editItemForm").submit();
	
}
function calcularTotalPrice(){
	
	var quantity = $("#quantity").val();
	var unitPrice = $("#unitPrice").val();
	
	var totalPrice = quantity * unitPrice;
	$("#totalPrice").val(totalPrice);
}