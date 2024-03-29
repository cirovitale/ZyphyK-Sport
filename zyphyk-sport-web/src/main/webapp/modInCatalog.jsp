<%@ page language="java" import="javax.sql.DataSource,it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean,it.unisa.zyphyksport.gestioneCatalogo.bean.SizesBean,it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf,it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.SizesInterf,it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO,it.unisa.zyphyksport.gestioneCatalogo.DAO.SizesDAO, java.util.*"
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%
	String roles = (String) session.getAttribute("roles");
 	String id = (String) request.getParameter("id");

	if(roles == null){
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
	
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

	
	ProductsInterf productsDAO = new ProductsDAO(ds);
	ProductsBean prodBean = productsDAO.doRetrieveByKey(id);
	
	Set<ProductsBean> collProd = productsDAO.doRetrieveAllExists(null);

	String exist = (String) request.getAttribute("message");
	request.removeAttribute("message");
%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Modifica in Catalogo</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
<link href="style/style.css" rel="stylesheet">
<script src="script/jquery-3.6.0.min.js"></script>
</head>
<body onload = "execute('<%=exist%>')">
	<script>
	
	function checkSizes() {
		var checkboxes = document.querySelectorAll('input[name^="sizesValue"]');
			var atLeastOneChecked = false;
			for (var i = 0; i < checkboxes.length; i++) {
				if (checkboxes[i].checked) {
					atLeastOneChecked = true;
					break;
				}
			}
			if (!atLeastOneChecked) {
				return false;
			}
			return true;
		}

		function checkName(inputtxt) {
			var name = /^[A-Za-z]{3,30}(\s[A-Za-z]{3,30})*$/;
			if (inputtxt.value.match(name))
				return true;

			return false;
		}

		function checkPrice(inputtxt) {
			var price = /^[0-9]{2,3}$/;
			if (inputtxt.value.match(price))
				return true;

			return false;
		}

		function validate(obj) {
			var valid = true;

			var name = document.getElementsByName("nomeProd")[0];
			if (!checkName(name)) {
				valid = false;
				alert("Nome scarpa non valido");
				name.focus();
			}
			
			if(!checkSizes()){
				valid = false;
				alert("Selezionare almeno una taglia da inserire");
			}

			var price = document.getElementsByName("price")[0];
			if (!checkPrice(price)) {
				valid = false;
				alert("Costo non valido");
				price.focus();
			}

			return valid;
		}
		
		function execute(flag){
			if(flag.match(true)){
				window.location.href="modInCatalog.jsp";
				alert("prodotto non presente nel catalogo");
				
			}
		}
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<%@ include file="../fragments/header.jsp"%>
	<div id ="cont" class="container page">
		<h2>Modifica in Catalogo</h2>
		<br/>
		
			<form action="ModInCatalogServlet?prodSelect=<%=prodBean.getId()%>" method="post" enctype="multipart/form-data" onsubmit="return validate(this)">

				<br/>

				<div id="prodSel">
					<label for="prodSel">Scarpa scelta: <%=prodBean.getName() %></label> 
				</div>
				<br/>
			<!-- pagina -->
			<div class='row'>
				<div class='col-md-6 mb-3'>
					<label for='nomeProd'>Nome nuovo: </label> <input type='text' class='form-control' id='nomeProd' name='nomeProd' required>
				</div>

				<div class='col-md-6 mb-3'>
					<label for='sizesValue'>Sizes: </label> <br /> 
					<input type="checkbox" id="sizesValue" name="sizesValue36" value="36"><label for="sizesValue36">&nbsp36</label> 	
					
					<input type="checkbox" id="sizesValue" name="sizesValue37" value="37"> <label for="sizesValue37">&nbsp37</label> 
					 
					<input type="checkbox" id="sizesValue" name="sizesValue38" value="38"> <label for="sizesValue38">&nbsp38</label> 
					 
					<input type="checkbox" id="sizesValue" name="sizesValue39" value="39"> <label for="sizesValue39">&nbsp39</label> 
					 
					<input type="checkbox" id="sizesValue" name="sizesValue40" value="40"><label for="sizesValue40">&nbsp40</label>  
					
					<input type="checkbox" id="sizesValue" name="sizesValue41" value="41"><label for="sizesValue41">&nbsp41</label> 
					 
					<input type="checkbox" id="sizesValue" name="sizesValue42" value="42"><label for="sizesValue42">&nbsp42</label> 
					</div>

				<div class='col-md-6 mb-3'>
					<label for='sport'>Categoria sportiva: </label> <select
						name="sport" id="sport" class=' col-md-6 mb-3 form-control '>
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
					<label for='cod-vid'>Brand: </label> <select name="brand"
						id="brand" class=' col-md-6 mb-3 form-control '>
						<option value="nike">nike</option>
						<option value="adidas">adidas</option>
						<option value="puma">puma</option>
						<option value="diadora">diadora</option>
						<option value="reebook">reebok</option>
						<option value="asics">asics</option>
					</select>
				</div>
				<div class='col-md-6 mb-3'>
					<label for='cod-vid'>Price: </label> <input type='text'
						class='form-control' id='price' name='price' maxlength='8'
						required>
				</div>
			</div>
			<input id="submit" type="submit" value="Modifica"
				class="btn btn-primary btn-block">

		</form>
	</div>
</body>
</html>