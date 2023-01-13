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

	function checkNamesurname(inputtxt) {
		var name = /^[A-Za-z]+$/;
		if (inputtxt.value.match(name))
			return true;

		return false;
	}
	
	function checkEmail(inputtxt) {
		var email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if(inputtxt.value.match(email)) 
			return true;
		
		return false;	
	}
	
    function checkPassword(inputtxt) {
        var password = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/;
        if (inputtxt.value.match(password))
            return true;

        return false;
    }
	
	function validate(obj) {
		var valid = true;
		
		var name = document.getElementsByName("nome")[0];
			if (!checkNamesurname(name)) {
				valid = false;
				alert("Il nome può contenere solo lettere");
				name.focus();
			}

		var surname = document.getElementsByName("cognome")[0];
			if(!checkNamesurname(surname)) {
				valid = false;
				alert("Il cognome può contenere solo lettere");
				surname.focus();
			}
		
		var email = document.getElementsByName("email")[0];
			if(!checkEmail(email)) {
				valid = false;
				alert("Indirizzo e-mail errato o non valido.");
				email.focus();
			}
		
		var pwd = document.getElementsByName("password")[0];
	        if(!checkPassword(pwd)) {
	            valid = false;
	            alert("La password deve contenere almeno un numero, una lettera maiuscola, minuscola e almeno 8 o più caratteri");
	            pwd.focus();
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
			     	<input id="conferma" type="text" name="conferma" class="form-control" placeholder="conferma password" required>   
				</div>
	   	   </div>
	  	       	   
	   	   
	     <input type="submit" value="Registrati" class="btn btn-primary btn-block"/>
	     <input type="reset" value="Reset" class="btn btn-danger btn-block"/>
		
		
	</form>
</div>


	<%@ include file="../fragments/footer.jsp" %>


</body>
</html>