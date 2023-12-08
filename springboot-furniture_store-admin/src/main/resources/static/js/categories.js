$(document).ready(function(){
	
	$('.table #updateButton').on('click', function(event){
		event.preventDefault();
		
		var href= $(this).attr('href');
		
		$.get(href, function(category, status){
			$('#idUpdate').val(category.id);
			$('#nameUpdate').val(category.name);
			$('#activatedUpdate').val(category.activated);
			$('#hiddenUpdate').val(category.hidden);
		});
		
		$('#updateModal').modal('show');
	});
	
	
	$('.table #deleteButton').on('click', function(event){
		event.preventDefault();
		
		var href= $(this).attr('href');		
		$('#deleteConfirmButton').attr('href', href);		
		$('#deleteModal').modal('show');
	});
	
	
	$('.table #activatedButton').on('click', function(event){
		event.preventDefault();
		
		var href= $(this).attr('href');		
		$('#activatedConfirmButton').attr('href', href);		
		$('#activatedModal').modal('show');
	});
	
	
	$('.table #hiddenButton').on('click', function(event){
		event.preventDefault();
		
		var href= $(this).attr('href');		
		$('#hiddenConfirmButton').attr('href', href);		
		$('#hiddenModal').modal('show');
	});
});