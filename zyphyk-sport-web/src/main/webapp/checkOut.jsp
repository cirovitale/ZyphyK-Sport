<%@ page language="java"
	import="javax.sql.DataSource,it.unisa.zyphyksport.model.bean.ClientiBean, it.unisa.zyphyksport.model.bean.CartsBean, 
	it.unisa.zyphyksport.model.bean.ProductsBean, it.unisa.zyphyksport.model.DAO.CartsContainsProdsDAO, it.unisa.zyphyksport.model.interfaceDS.CartsContainsProdsInterf, it.unisa.zyphyksport.model.bean.CartsContainsProdsBean, 
	it.unisa.zyphyksport.model.DAO.ProductsDAO, it.unisa.zyphyksport.model.interfaceDS.ProductsInterf, it.unisa.zyphyksport.model.bean.ProductsBean, java.util.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%
	ClientiBean cliente = (ClientiBean) session.getAttribute("utente");
	CartsBean carrello = (CartsBean) session.getAttribute("carrello");
	

	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	CartsContainsProdsInterf cartContProdsDAO = new CartsContainsProdsDAO(ds);
	Collection<CartsContainsProdsBean> cartContProds =  cartContProdsDAO.doRetrieveAllByCartId(carrello.getId(), null);
	ProductsInterf prodDS = new ProductsDAO(ds);
	
	
%>


<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CheckOut</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
</head>
<body>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>


	<%@ include file="../fragments/header.jsp" %>
	<div id="cont" class="container">
		<h2>CheckOut</h2>

		<br/><br/>
		
		<form action="CheckOutServlet" method="post">
		<div class="row">
			<div class="col-md-4 order-md-2 mb-4">
				<h4 class="d-flex justify-content-between align-items-center mb-3">
					<span class="text-muted">Il tuo carrello</span> <span
						class="badge badge-secondary badge-pill">3</span>
				</h4>
				<ul class="list-group mb-3">
				
				
				
							
				<% 
					for(CartsContainsProdsBean prod : cartContProds){
						ProductsBean product = prodDS.doRetrieveByKey(prod.getProductId());
				%>

					<li	class="list-group-item d-flex justify-content-between lh-condensed">
						<div>
							<h6 class="my-0"><%=product.getName() %></h6>
							<small class="text-muted"></small>
						</div> <span class="text-muted">&euro; <%=product.getPrice()%> x <%= prod.getQuantity() %></span>
					</li>
				<%
					}
				%>
				
					<li class="list-group-item d-flex justify-content-between"><span>Totale
					</span> <strong>&euro; <%= carrello.getAmount() %>
					</strong></li>
				</ul>

			</div>
			
			<div class="col-md-8 order-md-1">
				<h4 class="mb-3">Indirizzo di spedizione</h4>
				
				<div class="row">
					<div class="col-md-6 mb-3"> Nome: <%=cliente.getName()%></div>
					<div class="col-md-6 mb-3"> Cognome: <%=cliente.getSurname()%></div>
				</div>
				

				<div class="row">
					<div class="form-outline mb-3 col">
			 			<label class="form-label" for="via">Via</label>
		     			<input id="via" type="text" name="via" class="form-control" placeholder="via" autofocus>
					</div> 
					<div class="form-outline mb-3 col-md-auto">
			 			<label class="form-label" for="numCivico">Numero Civico</label>
		     			<input id="numCivico" type="text" name="numCivico" class="form-control" placeholder="numero civico" autofocus>
					</div> 
				</div>
				
				<div class="row">
					<div class="form-outline mb-3 col">
			 			<label class="form-label" for="città">Città</label>
		     			<input id="città" type="text" name="città" class="form-control" placeholder="città" autofocus>
					</div> 
					<div class="form-outline mb-3 col">
			 			<label class="form-label" for="provincia">Provincia</label>
		     			<input id="provincia" type="text" name="provincia" class="form-control" placeholder="provincia" autofocus>
					</div> 
				</div>
				
				
				<hr class="mb-4">

				<h4 class="mb-3">Pagamento</h4>
					
				<div class="row">
					<div class="col-md-6 mb-3">
						<label for="cc-name">Nome titolare della carta&#42;</label> 
						<input id="cc-name" type="text" name="cc-name" class="form-control" placeholder="" required>
					</div>
					<div class="col-md-6 mb-3">
						<label for="cc-number">Numero della carta di credito&#42;</label> 
						<input type="text" class="form-control" id="cc-number" name="cc-number" placeholder="" maxlength="16" required>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-3 mb-3">
						<label for="cc-expiration">Data di scadenza&#42;</label> <input type="date" class="form-control" id="cc-expiration" name="cc-expiration" placeholder="" required>
					</div>
					<div class="col-md-3 mb-3">
						<label for="cc-cvv">CVV&#42;</label> <input type="text" class="form-control" id="cc-cvv" name="cc-cvv" placeholder="" maxlength="3" required>
					</div>
				</div>
				<hr class="mb-4">
			</div>	
		</div>
		<button class="btn btn-primary btn-block" type="submit">Procedi al pagamento</button>
	</form>				
	</div>
	<%@ include file="../fragments/footer.jsp" %>

</body>
</html>