$(document).ready(function(){
		
	$('#debit').on('change', function() {
		document.getElementById('bankId').style.display = 'none';
	    $('#debit').prop('checked', true);
	    $('#paymentMethodId').val('Tiền mặt');
	});
	
	$('#credit').on('change', function() {
		document.getElementById('bankId').style.display = 'block';
	    $('#credit').prop('checked', true);
	    $('#paymentMethodId').val('Thẻ ngân hàng');
	});

});