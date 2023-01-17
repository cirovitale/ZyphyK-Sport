<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%

	String roles = (String) session.getAttribute("roles");
	
	if(roles == null){
		
	} else {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	
%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registrazione</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
</head>

<script>
	
	
	function checkUsername(inputtxt) {
		var regex = /[a-zA-Z0-9]{5,15}/;
		console.log(inputtxt.value)
		if (inputtxt.value.match(regex))
			return true;

		return false;
	}
	
	
	function checkEmail(inputtxt) {
		var regex = /[a-zA-Z][a-zA-Z0-9\.]*@([a-zA-Z]+)\.[a-zA-Z]+/;
		console.log(inputtxt.value)
		if (inputtxt.value.match(regex))
			return true;

		return false;
	}
	
	
	function checkName(inputtxt) {
		var regex = /^[A-Za-z]{3,}(\s[A-Za-z]{3,})*$/;
		console.log(inputtxt.value)
		if (inputtxt.value.match(regex))
			return true;

		return false;
	}
	
	
	function checkSurname(inputtxt) {
		var regex = /[a-zA-Z]{3,40}/;
		console.log(inputtxt.value)
		if (inputtxt.value.match(regex))
			return true;

		return false;
	}
	
	
	function checkDataDiNascita(inputtxt) {
		var regex = /^(0[1-9]|[12][0-9]|3[01])[\/](0[1-9]|1[012])[\/](19|20)\d\d$/;
		console.log(inputtxt.value)
		if (inputtxt.value.match(regex))
			return true;

		return false;
	}
	
	
	function checkPassword(inputtxt, conferma) {
		var regex = /^.{8,40}$/;
		console.log(inputtxt.value)
		if (inputtxt.value.match(regex) && inputtxt.equals(conferma))
			return true;

		return false;
	}
	
	
	function checkConfPassword(inputtxt) {
		var regex = /^.{8,40}$/;
		console.log(inputtxt.value)
		if (inputtxt.value.match(regex))
			return true;

		return false;
	}
	
	
	
	function validate(obj) {
		var valid = true;
		
		var name = document.getElementsByName("nome")[0];
			if (!checkName(name)) {
				valid = false;
				console.log("Nome non valido");
				name.focus();
			} else {
				console.log("nome valido");
			}

		var surname = document.getElementsByName("cognome")[0];
			if(!checkSurname(surname)) {
				valid = false;
				console.log("Cognome non valido");
				surname.focus();
			} else {
				console.log("cognome valido");
			}
			
		var username = document.getElementsByName("username")[0];
			if (!checkUsername(username)) {
				valid = false;
				console.log("Username non valido");
				name.focus();
			} else {
				console.log("Username valido");
			}
		
		var data = document.getElementsByName("data")[0];
	        if(!checkDataDiNascita(data)) {
	            valid = false;
	            console.log("Data di nascita non valida");
	            pwd.focus();
       		 } else {
 				console.log("data di nascita valida");
 			}
			
		var email = document.getElementsByName("email")[0];
			if(!checkEmail(email)) {
				valid = false;
				console.log("Email non valida");
				email.focus();
			} else {
				console.log("email valida");
			}
		
		
	        
	    var pwd = document.getElementsByName("password")[0];
	        if(!checkPassword(pwd)) {
	            valid = false;
	            console.log("Password non valida oppure non coincide con 'conferma password'");
	            pwd.focus();
       		 } else {
 				console.log("Password valida");
 			}
	        
	    var pwdConf = document.getElementsByName("conferma")[0];
	        if(!checkConfPassword(pwdConf)) {
	            valid = false;
	            console.log("Conferma Password non valida");
	            pwd.focus();
       		 } else {
 				console.log("Conferma Password valida");
 			}

		return valid;

	}
</script>

<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<%@ include file="../fragments/header.jsp" %>

<div class="col fixed-center d-flex justify-content-center align-items-center page">
	<form action="SignUpServlet" method="post" onsubmit="return validate(this)"> 
			
			<h2 class="mb-3">Registrazione</h2>    
			<br/>
			<div class='row'>
				<div class='col-md-6 mb-3'>		
					<label for="nome">Nome*</label> 
					<input id="nome" type="text" name="nome" class="form-control" placeholder="nome" required autofocus>
				</div>
				<div class='col-md-6 mb-3'>	
					<label for="cognome">Cognome*</label> 
					<input id="cognome" type="text" name="cognome" class="form-control" placeholder="cognome" required>
			    </div>
			</div>
			
			<div class='row'>
				<div class='col-md-6 mb-3'>		
			     	<label for="username">Username*</label>
			     	<input id="username" type="text" name="username" class="form-control" placeholder="username" required>    
				</div>
				<div class='col-md-6 mb-3'>	
			     	<label for="data">Data*</label>
			     	<input id="data" type="date" name="data" class="form-control" placeholder="data" required> 
			    </div>
	   	   </div>
	   	   
			<div class='row'>
				<div class='col-md-6 mb-3'>		
			     	<label for="email">Email*</label>
			     	<input id="email" type="email" name="email" class="form-control" placeholder="email" required>   
				</div>
				<div class='col-md-6 mb-3'>	
			     	<label for="password">Password*</label>
			     	<input id="password" type="password" name="password" class="form-control" placeholder="password" required> 
			    </div>
	   	   </div>
	   	   
	   	  <div class='row'>
				<div class='col-md-6 mb-3'>		
			     	<label for="email">Conferma Password*</label>
			     	<input id="conferma" type="password" name="conferma" class="form-control" placeholder="conferma password" required>   
				</div>
	   	   </div>
	  	       	   
	   	   
	     <input type="submit" value="Registrati" class="btn btn-primary btn-block"/>
	     <input type="reset" value="Reset" class="btn btn-danger btn-block"/>
		
		
	</form>
</div>


	<%@ include file="../fragments/footer.jsp" %>


</body>
</html>