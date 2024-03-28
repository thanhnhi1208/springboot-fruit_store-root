$(document).ready(function(){
			
		var country_valueSelect = document.getElementById('countryId').value;
 	
 		var city_valueSelect = document.getElementById('cityId');
 		
 		var cityValue = city_valueSelect.value;
 		
		$.get("/shop/account/getCityById",{id : country_valueSelect} ,function(cityList){
			while(city_valueSelect.options.length >1 ){
				city_valueSelect.remove(1);
			};			
				
			cityList.forEach(function(city){
				var option = document.createElement('option');
				option.value= city.id;
				option.text = city.name;
				
				if(city.id == cityValue){
					option.selected = true;
				}
				
				city_valueSelect.add(option);
			});
		});

	
	
	
	document.getElementById('countryId').addEventListener('change', function(){
		
		var country_valueSelect = this.value;
 	
 		var city_valueSelect = document.getElementById('cityId');
		$.get("/shop/account/getCityById",{id : country_valueSelect} ,function(cityList){
			while(city_valueSelect.options.length >1){
				city_valueSelect.remove(1);
			};
			
			cityList.forEach(function(city){
				var option = document.createElement('option');
				option.value= city.id;
				option.text = city.name;
				city_valueSelect.add(option);
			});
		});
	});
	
	$('#phoneNumberId').on('input', function(){
		$(this).val($(this).val().replace(/[^0-9]/g, ''));
	});
	
	$('#debit').on('change', function() {
		console.log('2')
	    $('#debit').prop('checked', true);
	});
	
	$('#credit').on('change', function() {
		console.log('2')
	    $('#credit').prop('checked', true);
	});
	
	/*$('#credit').on('change', function(){
		console.log(1)
		if($(this).is(':checked')){
			document.getElementById('bankId').style.display = 'block';
		}else{
			document.getElementById('bankId').style.display = 'none';
		}
	});*/
	
	var image1= document.getElementById('image1');
	var image2= document.getElementById('image2');
	
	if(image2 && image2.src != ''){
		image2.style.display = 'block';
	}else{
		image1.style.display = 'block';
	}
	
	
	$('#customerImage').on('change', function(){
		if(image1 && image1.src != ''){
			const fileInput = document.getElementById('customerImage');	
			const previewImage = document.getElementById('image1');
			
			const fileURL = URL.createObjectURL(fileInput.files[0]);
			previewImage.src=fileURL
		}else{
			const fileInput = document.getElementById('customerImage');	
			const previewImage = document.getElementById('image2');
			
			const fileURL = URL.createObjectURL(fileInput.files[0]);
			previewImage.src=fileURL
		}
		
		
		
	})
});