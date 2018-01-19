$(function(){
	setDatepicker();
})
function save(){
	document.getElementById("editHeaderForm").submit();

}
function back(){
	document.getElementById("editHeaderForm").action = "back"
	document.getElementById("editHeaderForm").submit();
}