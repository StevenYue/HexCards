$(document).ready(function(){
	$(".form").submit(function(e){
		e.preventDefault();
	});
	
	$("#login-button").click(function(event){
	//	window.location.href = "gameplay.html?ID=" + $('input[name="ID"]').val();
		formSubmitted();
	});
});

function formSubmitted(){
	//get the form data and then Serialize that
	var dataString = $(".form").serialize();
	
	$.ajax({
		type : 'POST',// GET Or POST
		url  : "/SixCards/login",
		data:dataString,
		dataType: "json",
		 
		success: function(response, textStatus, jqHXR){
				//console.log(response);
				//console.log(response.action);
				var data = response.action;
				if(data == "reload page on success"){
					alert("Thank you for logging in!");
					window.location.href = "gameplay.html?ID=" + $('input[name="ID"]').val();
				}else{
					alert(data);
				}
		  },
		  
		  error: function(jqXHR, textStatus, errorThrown){
		      console.log(
		          "The following error occurred: "+
		          textStatus, errorThrown
		      );
		  }
	
	});
}
