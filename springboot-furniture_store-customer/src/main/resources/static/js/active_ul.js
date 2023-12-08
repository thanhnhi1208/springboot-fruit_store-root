$(document).ready(function() {
	
    $("ul li").on("click", function(event) {
		 var id = $(this).attr("id");
		
		/*loại bỏ tất cả các active trong li cùng cấp với #id*/
		$('#' + id).siblings().find(".active").removeClass("active");
		$('#' + id).addClass("active");
            localStorage.setItem("selectedolditem", id);
    }); 
    
     $(".login-box select").on("change", function(event) {
		/*var selectedOption = $(this).find("option:selected");
		 var id = $(this).attr("selected");
		 alert(id)
		 $(this).find("option").prop("selected", false);
		 
		  selectedOption.prop("selected", true);
		  
		  alert(selectedOption.text());
		  
		 $('#' + id).siblings().find(".active").removeClass("active");
		$('#' + id).addClass("active");
            localStorage.setItem("selectedolditem", id);
    
    alert(optionText);*/
    }); 
    
    $("button a").on("click", function(event) {
		 var id = $(this).attr("id");
		
		/*loại bỏ tất cả các active trong li cùng cấp với #id*/
		$('#' + id).siblings().find(".active").removeClass("active");
		$('#' + id).addClass("active");
            localStorage.setItem("selectedolditem", id);
    }); 
    
    var selectedolditem = localStorage.getItem('selectedolditem');
        if (selectedolditem != null) {
			$('#' + selectedolditem).siblings().find(".active").removeClass("active");
          	$('#' + selectedolditem).addClass("active");		
        }
});
