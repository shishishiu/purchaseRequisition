$(function(){
	
	setApplicatedAt();
	setDeliveryDate();

}) 

function setDeliveryDate(){
	$('#deliveryDateFrom').datepicker({
		
		onSelect: function(dateStr) {
			var min = $(this).datepicker('getDate') || new Date();
	        $('#deliveryDateTo').datepicker('option', {minDate: min});

	    }
	});
	$('#deliveryDateTo').datepicker({
		onSelect: function(dateStr) {
			var max = $(this).datepicker('getDate') || new Date();
	        $('#deliveryDateFrom').datepicker('option', {maxDate: max});

	    }
	});
	
	$('#deliveryDateFrom').change(function() {
		var txt = $("#deliveryDateFrom").val();
		if(txt == ""){
			$('#deliveryDateTo').datepicker('option', {minDate: null});	
		}
	})
	$('#deliveryDateTo').change(function() {
		var txt = $("#deliveryDateTo").val();
		if(txt == ""){
			$('#deliveryDateFrom').datepicker('option', {maxDate: '0'});	
		}
	})

}
function setApplicatedAt(){
	$('#applicatedAtFrom').datepicker({
		onSelect: function(dateStr) {
			var min = $(this).datepicker('getDate') || new Date();
	        $('#applicatedAtTo').datepicker('option', {minDate: min});
	    }
	});
	
	$('#applicatedAtFrom').change(function() {
		var txt = $("#applicatedAtFrom").val();
		if(txt == ""){
			$('#applicatedAtTo').datepicker('option', {minDate: null});	
		}
	})
	$('#applicatedAtTo').change(function() {
		var txt = $("#applicatedAtTo").val();
		if(txt == ""){
			$('#applicatedAtFrom').datepicker('option', {maxDate: '0'});	
		}
	})
	
}
function searchPurchase(){
	document.getElementById("searchForm").submit();
}
function clearCondition(){
	$(".condition").val("");
	$(".condition")[0].focus();
	
	$( "input[name='checkIsDelivered']" ).prop("checked",false);
	$( "input[name='checkIsDelivered']" )[0].checked = true;

}