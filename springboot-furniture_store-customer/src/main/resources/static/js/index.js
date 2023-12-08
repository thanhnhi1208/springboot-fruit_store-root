$(document).ready(function() {


	$('#formId #buttonSubbmit').on('click', function(event) {
		$.get("/shop/checkAuthentication", function(checkAuthentication) {
				if(checkAuthentication!= null && checkAuthentication === 'no'){
					window.location.href="/shop/login";
					return;
				}
			});
		

		$('#exampleModal').modal('show');

		setTimeout(function() {
			$('#exampleModal').modal('hide');
		}, 2000);

		$('#exampleModal').on('shown.bs.modal', function(e) {
			// Gọi hàm chỉ khi modal đã được ẩn
			$.get("/shop/getTotalItems", function(totalItem) {
				console.log(totalItem)
				if (totalItem != null && totalItem !== undefined) {
					$('.badge').text(totalItem);
				}

			});
		});		
	});

});