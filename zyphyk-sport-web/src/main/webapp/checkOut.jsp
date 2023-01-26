<%@ page language="java"
	import="javax.sql.DataSource,it.unisa.zyphyksport.model.bean.ClientiBean,it.unisa.zyphyksport.model.bean.CartsBean,it.unisa.zyphyksport.model.bean.ProductsBean,it.unisa.zyphyksport.model.DAO.CartsContainsProdsDAO,it.unisa.zyphyksport.model.interfaceDS.CartsContainsProdsInterf,it.unisa.zyphyksport.model.bean.CartsContainsProdsBean,it.unisa.zyphyksport.model.DAO.ProductsDAO,it.unisa.zyphyksport.model.interfaceDS.ProductsInterf,it.unisa.zyphyksport.model.bean.ProductsBean, java.util.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%
ClientiBean cliente = (ClientiBean) session.getAttribute("utente");
CartsBean carrello = (CartsBean) session.getAttribute("carrello");

DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
CartsContainsProdsInterf cartContProdsDAO = new CartsContainsProdsDAO(ds);
Set<CartsContainsProdsBean> cartContProds = cartContProdsDAO.doRetrieveAllByCartId(carrello.getId(), null);
ProductsInterf prodDS = new ProductsDAO(ds);
%>


<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CheckOut</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32"
	href="img/icon/favicon.png">
</head>
<script>
	function checkVia(inputtxt) {
		var via = /^[a-zA-Z ']{5,30}/;
		if (inputtxt.value.match(via))
			return true;
	
		return false;
	}
	
	function checkNumCivico(inputtxt) {
		var numCivico = /^[0-9]{1,3}/;
		if (inputtxt.value.match(numCivico))
			return true;
	
		return false;
	}
	
	function checkCittà(inputtxt) {
		var città = /^[a-zA-Z ']{3,30}/;
		if (inputtxt.value.match(città))
			return true;
	
		return false;
	}
	
	function checkProvincia(inputtxt) {
		var provincia = /^[a-zA-Z]{2}/;
		if (inputtxt.value.match(provincia))
			return true;
	
		return false;
	}
	
	function checkTitolCarta(inputtxt) {
		var titolCarta = /^[A-Za-z]{3,20}(\s[A-Za-z]{3,20})(\s[A-Za-z]{3,20})*$/;
		if (inputtxt.value.match(titolCarta))
			return true;
	
		return false;
	}
	
	function checkNumCarta(inputtxt) {
		var numCarta = /^[A-Z0-9]{16}/;
		if (inputtxt.value.match(numCarta))
			return true;
	
		return false;
	}
	
	function checkCvv(inputtxt) {
		var cvv = /^[0-9']{3}/;
		if (inputtxt.value.match(cvv))
			return true;
	
		return false;
	}
	
	function checkDataScadenza(inputtxt) {
		var dataScad = /^\d{4}-\d{2}$/;
		var dateNow = Date.now();
		var pickedDate = Date.parse(inputtxt.value);
		
		if (inputtxt.value.match(dataScad) && pickedDate > dateNow)
			return true;
	
		return false;
	}
	
	
	
	function validate(obj) {
		var valid = true;
		
		var via = document.getElementsByName("via")[0];
			if (!checkVia(via)) {
				valid = false;
				alert("Via non valida");
				via.focus();
			}
	
		var numCivico = document.getElementsByName("numCivico")[0];
			if(!checkNumCivico(numCivico)) {
				valid = false;
				alert("Numero civico non valido");
				numCivico.focus();
			}
		
		var città = document.getElementsByName("città")[0];
			if(!checkCittà(città)) {
				valid = false;
				alert("Città non valida");
				città.focus();
			}
		
		var provincia = document.getElementsByName("provincia")[0];
			if(!checkProvincia(provincia)) {
				valid = false;
				alert("Provincia non valida");
				provincia.focus();
			}			
			
		var titolCarta = document.getElementsByName("cc-name")[0];
			if(!checkTitolCarta(titolCarta)) {
				valid = false;
				alert("Titolare carta non valido");
				titolCarta.focus();
			}
			
		var numCarta = document.getElementsByName("cc-number")[0];
			if(!checkNumCarta(numCarta)) {
				valid = false;
				alert("Numero carta non valido");
				numCarta.focus();
			}
			
		var cvv = document.getElementsByName("cc-cvv")[0];
			if(!checkCvv(cvv)) {
				valid = false;
				alert("CVV non valido");
				cvv.focus();
			}
			
		var dataScadenza = document.getElementsByName("cc-expiration")[0];
			if(!checkDataScadenza(dataScadenza)) {
				valid = false;
				alert("Data scadenza carta non valida");
				dataScadenza.focus();
			}
			
		
	
		return valid;
	}
</script>
<body>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>


	<%@ include file="../fragments/header.jsp"%>
	<div id="cont" class="container">
		<h2>CheckOut</h2>

		<br />
		<br />

		<form action="CheckOutServlet" method="post" onsubmit="return validate(this)">
			<div class="row">
				<div class="col-md-4 order-md-2 mb-4">
					<h4 class="d-flex justify-content-between align-items-center mb-3">
						<span class="text-muted">Il tuo carrello</span> <span
							class="badge badge-secondary badge-pill">3</span>
					</h4>
					<ul class="list-group mb-3">




						<%
						for (CartsContainsProdsBean prod : cartContProds) {
							ProductsBean product = prodDS.doRetrieveByKey(prod.getProductId());
						%>

						<li
							class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0"><%=product.getName()%></h6>
								<small class="text-muted"></small>
							</div> <span class="text-muted">&euro; <%=product.getPrice()%> x
								<%=prod.getQuantity()%></span>
						</li>
						<%
						}
						%>

						<li class="list-group-item d-flex justify-content-between"><span>Totale
						</span> <strong>&euro; <%=carrello.getAmount()%>
						</strong></li>
					</ul>

				</div>

				<div class="col-md-8 order-md-1">
					<h4 class="mb-3">Indirizzo di spedizione</h4>

					<div class="row">
						<div class="col-md-6 mb-3">
							Nome:
							<%=cliente.getName()%></div>
						<div class="col-md-6 mb-3">
							Cognome:
							<%=cliente.getSurname()%></div>
					</div>


					<div class="row">
						<div class="form-outline mb-3 col">
							<label class="form-label" for="via">Via</label> <input id="via"
								type="text" name="via" class="form-control" placeholder="via"
								autofocus>
						</div>
						<div class="form-outline mb-3 col-md-auto">
							<label class="form-label" for="numCivico">Numero Civico</label> <input
								id="numCivico" type="text" name="numCivico" class="form-control"
								placeholder="numero civico" maxlength="3" autofocus>
						</div>
					</div>

					<div class="row">
						<div class="form-outline mb-3 col">
							<label class="form-label" for="città">Città</label> <input
								id="città" type="text" name="città" class="form-control"
								placeholder="città" autofocus>
						</div>
						<div class="form-outline mb-3 col">
							<label class="form-label" for="provincia">Provincia</label> <input
								id="provincia" type="text" name="provincia" class="form-control"
								placeholder="provincia" maxlength="2" autofocus>
						</div>
					</div>


					<hr class="mb-4">

					<h4 class="mb-3">Pagamento</h4>

					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="cc-name">Nome titolare della carta&#42;</label> <input
								id="cc-name" type="text" name="cc-name" class="form-control"
								placeholder="" required>
						</div>
						<div class="col-md-6 mb-3">
							<label for="cc-number">Numero della carta di credito&#42;</label>
							<input type="text" class="form-control" id="cc-number"
								name="cc-number" placeholder="" maxlength="16" required>
						</div>
					</div>

					<div class="row">
						<div class="col-md-3 mb-3">
							<label for="cc-expiration">Data di scadenza&#42;</label> <input
								type="month" class="form-control" id="cc-expiration"
								name="cc-expiration" placeholder="" value="2023-01" required>
						</div>
						<div class="col-md-3 mb-3">
							<label for="cc-cvv">CVV&#42;</label> <input type="text"
								class="form-control" id="cc-cvv" name="cc-cvv" placeholder=""
								maxlength="3" required>
						</div>
					</div>
					<hr class="mb-4">
				</div>
			</div>
			<button class="btn btn-primary btn-block" type="submit">Procedi
				al pagamento</button>
		</form>
	</div>
	<%@ include file="../fragments/footer.jsp"%>

</body>
</html>