$(document).ready(function() {
	onlyNumbers("#ddd");
	onlyNumbers("#p");
	$("#btnUser").click(validateUserForm);
	
	clearStatusField("name");
	clearStatusField("email");
	clearStatusField("password");
	clearStatusField("phone");
});

function clearStatusField(field){
	$("#" + field).focus(function() {
		$("#" + field + "Div").removeClass("error");
		$("#" + field + "Help").html("");
	});
}
function onlyNumbers(id){
	$(id).keypress(function(event) {
	    if (event.which < 47 || event.which > 59) {
	        event.preventDefault();
	    }
	});
}

// Validador de Form Usuario
function validateUserForm(){
	var isValid = true;
	var name = $("#name").val();
	if(!name){
		$("#nameDiv").addClass("error");
		$("#nameHelp").html("Nome inválido.");
		isValid = false;
	}
	
	name = $("#email").val();
	if(!name){
		$("#emailDiv").addClass("error");
		$("#emailHelp").html("E-mail inválido.");
		isValid = false;
	}
	
	name = $("#password").val();
	if(!name){
		$("#passwordDiv").addClass("error");
		$("#passwordHelp").html("Password inválido.");
		isValid = false;
	}
	
	if(false){
		var ddd = $("#ddd").val();
		var phone = $("#phone").val();
		if(!ddd || !phone){
			$("#phoneDiv").addClass("error");
			$("#phoneHelp").html("Telefone inválido.");
			isValid = false;
		}
	}
	
	if(isValid){
		$("#userForm").submit();
	}
}
