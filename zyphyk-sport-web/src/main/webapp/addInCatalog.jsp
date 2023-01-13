<%@ page language="java" import="javax.sql.DataSource, it.unisa.zyphyksport.model.bean.ProductsBean,
	it.unisa.zyphyksport.model.interfaceDS.ProductsInterf, it.unisa.zyphyksport.model.DAO.ProductsDAO, java.util.*"
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%
	String roles = (String) session.getAttribute("roles");
/*
	if(roles == null){
		response.sendRedirect(request.getContextPath() + "/login-form.jsp");
	} else if (roles.equals("admin") || roles.equals("assCl") || roles.equals("cliente")) {
		response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
	}
*/
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

	ProductsInterf productsDAO = new ProductsDAO(ds);
	Collection<ProductsBean> collVid = productsDAO.doRetrieveAll(null);
	
%> 
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Aggiungi in Catalogo</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
<link href="style/style.css" rel="stylesheet">
<script src="script/jquery-3.6.0.min.js"></script>
</head>


<body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<%@ include file="../fragments/header.jsp"%>
	<div id ="cont" class="container page">
		<h2>Aggiungi in Catalogo</h2>
		<br/>
		
			<form action="AddInCatalogServlet" method="post" enctype="multipart/form-data">
				<!--  
				<div>
					<label for="tipo">Tipologia:</label>
					<select name="tipo" id="tipo" class=' col-md-6 mb-3 form-control '>
						<option value="null" selected>-</option>
						<option value="prodotto">Prodotto</option>
					</select>
				</div>
				-->
				<br/>
				
				<!-- pagina -->
				<div class='row'>
					<div class='col-md-6 mb-3'>
						<label for='cod-vid'>Cod. Scarpa: </label>
						<input type='text'class='form-control' id='productId' name='productId' maxlength='8'required>
					</div>
								
					<div class='col-md-6 mb-3'>
						<label for='nome-vid'>Nome: </label>
						<input type='text'class='form-control' id='nomeProd' name='nomeProd' required>			
					</div>
					
					<div class='col-md-6 mb-3'>
					<label for='nome-vid'>Sizes: </label>	
					<br/>		
					  <input type="checkbox" id="sizesValue" name="sizesValue36" value="36">
					  <label for="sizesValue36"> 36</label>
					  <input type="checkbox" id="sizesValue" name="sizesValue37" value="37">
					  <label for="sizesValue37"> 37</label>
					  <input type="checkbox" id="sizesValue" name="sizesValue38" value="38">
					  <label for="sizesValue38"> 38</label>
					  <input type="checkbox" id="sizesValue" name="sizesValue39" value="39">
					  <label for="sizesValue39"> 39</label>
					  <input type="checkbox" id="sizesValue" name="sizesValue40" value="40">
					  <label for="sizesValue40"> 40</label>
					  <input type="checkbox" id="sizesValue" name="sizesValue41" value="41">
					  <label for="sizesValue41"> 41</label>
					  <input type="checkbox" id="sizesValue" name="sizesValue42" value="42">
					  <label for="sizesValue42"> 42</label>					  					  					  
					</div>					
									
					<div class='col-md-6 mb-3'>
						<label for='sport'>Categoria sportiva: </label>	
						<select name="sport" id="sport" class=' col-md-6 mb-3 form-control '>
						<option value="Calcio">Calcio</option>
						<option value="Basket">Basket</option>
						<option value="Calcetto">Calcetto</option>
						<option value="Palestra">Palestra</option>
						<option value="Pallavolo">Pallavolo</option>
						<option value="Boxe">Boxe</option>
						<option value="Jogging">Jogging</option>
						<option value="Running">Running</option>						
					</select>
						
					</div>
					<div class='col-md-6 mb-3'>
						<label for='cod-vid'>Brand: </label>
						<select name="brand" id="brand" class=' col-md-6 mb-3 form-control '>
						<option value="nike">nike</option>
						<option value="adidas">adidas</option>
						<option value="puma">puma</option>
						<option value="diadora">diadora</option>
						<option value="reebook">reebook</option>
						<option value="asics">asics</option>					
					</select>						
					</div>
					<div class='col-md-6 mb-3'>
						<label for='cod-vid'>Price: </label>
						<input type='text'class='form-control' id='price' name='price' maxlength='8'required>
					</div>
				</div>
				<input id="submit" type="submit" value="Aggiungi" class="btn btn-primary btn-block">
				

			</form>
	</div>
	
</body>
</html>