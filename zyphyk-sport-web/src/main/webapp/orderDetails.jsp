<%@ page language="java" contentType="text/html; charset=utf-8"
	import="javax.sql.DataSource,it.unisa.zyphyksport.gestioneVendite.DAO.OrdersDAO,it.unisa.zyphyksport.gestioneVendite.interfaceDS.OrdersInterf,it.unisa.zyphyksport.gestioneVendite.bean.OrdersBean,it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean,it.unisa.zyphyksport.gestioneVendite.DAO.OrdersContainsProdsDAO,it.unisa.zyphyksport.gestioneVendite.interfaceDS.OrdersContainsProdsInterf,it.unisa.zyphyksport.gestioneVendite.bean.OrdersContainsProdsBean,it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO,it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf,it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean,
	
	java.time.format.DateTimeFormatter,	java.time.LocalDateTime, java.util.*"
	pageEncoding="utf-8"%>
<!DOCTYPE html>

<%
	int idOrder = Integer.parseInt(request.getParameter("idOrder"));
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	OrdersInterf ordersDS = new OrdersDAO(ds);
	OrdersBean ordersBean = ordersDS.doRetrieveByKey(idOrder);
	
	ClientiBean cliente = (ClientiBean) session.getAttribute("utente");
		
	OrdersContainsProdsInterf ordContProdDS = new OrdersContainsProdsDAO(ds);
	Set<OrdersContainsProdsBean> prods = ordContProdDS.doRetrieveAllProds(ordersBean.getId(), null);
	
	ProductsInterf prodDS = new ProductsDAO(ds);
		
%>

<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dettagli ordine</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32"
	href="img/icon/favicon.png">
<link href="style/style.css" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
</head>
<body>

<script src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>


	<script src="script/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<%@ include file="../fragments/header.jsp"%>

	<div class="container">
		<div class="card-body">
			<div class="container mb-5 mt-3">
				<div class="row d-flex align-items-baseline">
					<div class="col-xl-9">
						<p style="color: #000000; font-size: 20px;">
							Ordine >> <strong>ID: <%= ordersBean.getId()  %></strong>
						</p>
						
						<%
							if(ordersBean.isSent()){		
						%>
						<p style="color: #000000; font-size: 20px;">
							stato >> <strong>SPEDITO </strong>
						</p>
						<%
							}else{
						%>
						<p style="color: #000000; font-size: 20px;">
							stato >> <strong>NON SPEDITO </strong>
						</p>
						<%
							}
						%>
					</div>
					<hr>
				</div>

				<div class="container">

					<div class="row">
						<div class="col-xl-8">
							<ul class="list-unstyled">
								<li class="text-muted "><b>Intestata a:</b> <span
									style="color: #0D6EFD;"> <%=cliente.getName()%> <%=cliente.getSurname() %>
								</span></li>
								<li class="text-muted"><b>Indirizzo di fatturazione:</b> <%=ordersBean.getShippingAddress() %></li>
								<li class="text-muted"><b>Metodo di pagamento:</b> <%=ordersBean.getPaymentMethod().substring(0,4) %>**** <%=ordersBean.getPaymentMethod().substring(17,ordersBean.getPaymentMethod().length()) %></li>
							</ul>
						</div>
						<div class="col-xl-4">
							<ul class="list-unstyled">
								<li class="text-muted"><i class="fas fa-circle"
									style="color: #0D6EFD;"></i> <span class="fw-bold">ID: </span><%= ordersBean.getId()%>
								</li>
								<li class="text-muted"><i class="fas fa-circle"
									style="color: #0D6EFD;"></i> <span class="fw-bold">Data
										e ora: </span> <%= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(ordersBean.getDateTime())%>
								</li>

							</ul>
						</div>
					</div>

					<div class="row my-2 mx-1 justify-content-center">
						<table class="table table-striped table-borderless">
							<thead style="background-color: #0D6EFD;" class="text-white">
								<tr>
									
									<th scope="col">Nome</th>
									<th scope="col">Taglia</th>
									<th scope="col">Brand</th>
									<th scope="col">Costo</th>
									<th scope="col">Quantità</th>
								</tr>
							</thead>
							<tbody>
								
	            				<% for(OrdersContainsProdsBean prod : prods){
		    							ProductsBean product = prodDS.doRetrieveByKey(prod.getProductId());
		    							
		    					%>
								<tr>
									
									<td><%= product.getName() %></td>
									<td><%= prod.getSize() %> </td>
									<td><%= product.getBrand() %></td>
									<td>&euro; <%= prod.getPrice() %></td>
									<td><%= prod.getQuantity() %></td>
								</tr>
								<%
	            					}
	            				%>
	            				
							</tbody>
						</table>
					</div>

					<div class="row">
						<div class="col-xl-3">

							<p class="text-black float-start">
								<span class="text-black me-3">Totale</span><span
									style="font-size: 25px;">&euro; <%=ordersBean.getAmount()%></span>
							</p>
						</div>
					</div>
					<hr>
				</div>
			</div>
		</div>
	</div>



<div><%@ include file="../fragments/footer.jsp"%></div>
	
</body>
</html>