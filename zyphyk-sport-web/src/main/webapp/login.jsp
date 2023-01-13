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
<title>Login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
</head>
<body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<%@ include file="../fragments/header.jsp" %> 

<div class="col fixed-center d-flex justify-content-center align-items-center page">
	
	<form action="LoginServlet" method="post"> 
		<h2>Accedi</h2><br/>
		<div class="form-outline mb-3">
			 <label class="form-label" for="username">Username</label>
		     <input id="username" type="text" name="username" class="form-control" placeholder="username" autofocus>
		     
		</div> 
		<div class="form-outline mb-3">
			 <label class="form-label" for="password">Password</label> 
		     <input id="password" type="password" name="password" class="form-control" placeholder="password">
		    
		</div>
		<input type="submit" class="btn btn-primary btn-block mb-4" value="Login"/>
	    <input type="reset" class="btn btn-danger btn-block mb-4" value="Reset"/>
		
		
		
		<div class="text-center">
	   		 <p>Non sei registrato? <a href="/gamePlatformSite/signup-form.jsp">Registrati</a></p>
	    </div>
	     
	   
	</form>

</div>

		<%@ include file="../fragments/footer.jsp" %>


</body>
</html>
