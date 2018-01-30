$(function(){
	
	setDatepicker();

}) 
function setDatepicker(){
	$('.datepicker').datepicker({
		dateFormat: 'yy-mm-dd',
		dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
		dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
		monthNames: [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ]
		});	
}