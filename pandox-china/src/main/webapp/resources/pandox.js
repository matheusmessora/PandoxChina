$(document).ready(function() {
	onlyNumbers("#ddd");
	onlyNumbers("#p");
	$("#btnUser").click(validateUserForm);
	$("#btnPage").click(validatePageForm);

	clearStatusField("name");
	clearStatusField("email");
	clearStatusField("password");
	clearStatusField("phone");
});

function clearStatusField(field) {
	$("#" + field).focus(function() {
		$("#" + field + "Div").removeClass("error");
		$("#" + field + "Help").html("");
	});
}
function onlyNumbers(id) {
	$(id).keypress(function(event) {
		if (event.which < 47 || event.which > 59) {
			event.preventDefault();
		}
	});
}

// Validador de Form Usuario
function validateUserForm() {
	var isValid = true;
	var value = $("#name").val();
	if (!value) {
		$("#nameDiv").addClass("error");
		$("#nameHelp").html("Nome inválido.");
		isValid = false;
	}

	value = $("#email").val();
	if (!value) {
		$("#emailDiv").addClass("error");
		$("#emailHelp").html("E-mail inválido.");
		isValid = false;
	}

	value = $("#password").val();
	if (!value) {
		$("#passwordDiv").addClass("error");
		$("#passwordHelp").html("Password inválido.");
		isValid = false;
	}

	if (false) {
		var ddd = $("#ddd").val();
		var phone = $("#phone").val();
		if (!ddd || !phone) {
			$("#phoneDiv").addClass("error");
			$("#phoneHelp").html("Telefone inválido.");
			isValid = false;
		}
	}

	if (isValid) {
		$("#userForm").submit();
	}
}

// Validador de Form Page
function validatePageForm() {
	var isValid = true;
	var value = $("#url").val();
	if (!value) {
		$("#urlDiv").addClass("error");
		$("#urlHelp").html("Informe o nome da sua página.");
		isValid = false;
	}

	value = $("#email").val();
	if (!value) {
		$("#emailDiv").addClass("error");
		$("#emailHelp").html("Informe e-mail de contato.");
		isValid = false;
	}

	value = $("#mainColor").val();
	if (!value) {
		$("#mainColorDiv").addClass("error");
		$("#mainColorHelp").html("Informe a cor.");
		isValid = false;
	}

	var ddd = $("#ddd").val();
	var phone = $("#phone").val();
	if (!ddd || !phone) {
		$("#phoneDiv").addClass("error");
		$("#phoneHelp").html("Telefone inválido.");
		isValid = false;
	}

	if (isValid) {
		$("#pageForm").submit();
	}
}
