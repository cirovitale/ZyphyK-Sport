<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%

	String roles = (String) session.getAttribute("roles");
	
	if(roles == null){
		
	} else {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	String exist = (String) request.getAttribute("message");
	request.removeAttribute("message");
	
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
	
	
	function checkName(inputtxt) {
		var nome = /^[A-Za-z]{3,}(\s[A-Za-z]{3,})*$/;
		if (inputtxt.value.match(nome))
			return true;
	
		return false;
	}
	
	function checkSurname(inputtxt) {
		var surname = /^[A-Za-z]{3,}(\s[A-Za-z]{3,})*$/;
		if (inputtxt.value.match(surname))
			return true;

		return false;
	}
	
	function checkUsername(inputtxt) {
		var username = /^[a-zA-Z0-9]{5,15}$/;
		if (inputtxt.value.match(username))
			return true;

		return false;
	}
	
	
	function checkEmail(inputtxt) {
		var email = /[a-zA-Z][a-zA-Z0-9\.]*@([a-zA-Z]+)\.[a-zA-Z]+/;
		if (inputtxt.value.match(email))
			return true;

		return false;
	}
	
	function checkDataDiNascita(inputtxt) {
		var data = /^\d{4}-\d{2}-\d{2}$/;
		if (inputtxt.value.match(data))
			return true;

		return false;
	}
	
	
	function checkPassword(inputtxt,conf) {
		var passw = /^.*(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%&? "]).*$/;
		if (inputtxt.value.match(passw) && inputtxt.value.match(conf.value))
			return true;

		return false;
	}
	
	
	function checkConfPassword(inputtxt) {
		var confPass = /^.*(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%&? "]).*$/;
		if (inputtxt.value.match(confPass))
			return true;

		return false;
	}
	
	
	
	function validate(obj) {
		var valid = true;
		
		var name = document.getElementsByName("nome")[0];
			if (!checkName(name)) {
				valid = false;
				alert("Nome non valido");
				name.focus();
			}

		var surname = document.getElementsByName("cognome")[0];
			if(!checkSurname(surname)) {
				valid = false;
				alert("Cognome non valido");
				surname.focus();
			}
			
		var username = document.getElementsByName("username")[0];
			if (!checkUsername(username)) {
				valid = false;
				alert("Username non valido");
				username.focus();
			}
		
		var data = document.getElementsByName("data")[0];
	        if(!checkDataDiNascita(data)) {
	            valid = false;
	            alert("Data di nascita non valida");
	            data.focus();
       		 }
			
		var email = document.getElementsByName("email")[0];
			if(!checkEmail(email)) {
				valid = false;
				alert("Email non valida");
				email.focus();
			}
		
		var flag = false;
		var pwdConf = document.getElementsByName("conferma")[0];
	    var pwd = document.getElementsByName("password")[0];
		    if(!checkConfPassword(pwdConf)) {
	            valid = false;
	            alert("Conferma Password non valida");
	            pwdConf.focus();
	            flag = true;
	   		 }
	        if(!checkPassword(pwd,pwdConf) && flag == false) {
	            valid = false;
	            alert("Password non valida oppure non coincide con 'conferma password'");
	            pwd.focus();
       		 }
	        
	        

		return valid;

	}
	
	
	function execute(flag){
		if(flag.match(true)){
			window.location.href="signUp.jsp";
			alert("username già esistente");
			
		}
	}
	
</script>

<body onload = "execute('<%=exist%>')">
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
			     	<label for="data">Data di nascita*</label>
			     	<input id="data" type="date" name="data" class="form-control" placeholder="data"> 
			    </div>
	   	   </div>
	   	   
			<div class='row'>
				<div class='col-md-6 mb-3'>		
			     	<label for="email">Email*</label>
			     	<input id="email" type="email" name="email" class="form-control" placeholder="email">   
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
	  	       	   
	   	   
	     <input type="submit" value="Registrati" id="submit" class="btn btn-primary btn-block"/>
	     <input type="reset" value="Reset" id="reset" class="btn btn-danger btn-block"/>
		
		
	</form>
</div>


	<%@ include file="../fragments/footer.jsp" %>


</body>
</html>