<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
	import = "javax.sql.DataSource,it.unisa.zyphyksport.model.bean.ClientiBean, it.unisa.zyphyksport.model.bean.OrdersBean, 
	it.unisa.zyphyksport.model.interfaceDS.OrdersInterf, it.unisa.zyphyksport.model.DAO.OrdersDAO,
	java.time.format.DateTimeFormatter, java.time.LocalDateTime, java.util.*"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%
	String roles = (String) session.getAttribute("roles");
	ClientiBean cliente = (ClientiBean) session.getAttribute("utente");
	
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	OrdersDAO ordiniDS = new OrdersDAO(ds);
	Collection<OrdersBean> colOrd= ordiniDS.doRetrieveAllCliente(cliente.getUsername(), "date_time");

	
	
	final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
%>

<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>I miei ordini</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
<link href="style/style.css" rel="stylesheet">

</head>
<body>
	
	<script src="script/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<div id="pagina">
		<%@ include file="../fragments/header.jsp" %>
		<div class="container page">
			<h2>I miei ordini</h2>
			<br/>
			<table class="table table-light align-middle" id="tabellaOrdini">
				<thead>
					<tr>
						<th scope="col">Id</th>
						<th scope="col">Data e Ora</th>
						<th scope="col">Costo</th>
						<th scope="col">Stato</th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<%
						for(OrdersBean order: colOrd){
					%>
					
					<tr id = "<%= order.getId() %>">
						
						<td><%= order.getId() %></td>
						<td><%= order.getDateTime().format(dtf)%></td>
						<td>&euro;<%= order.getAmount()%></td>
						<%
							if(order.isSent()){ 
						%>
						<td>SPEDITO</td>
						<%
							}else{
								
							
						%>
						<td>NON SPEDITO</td>
						<%
							}
						%>
						<td><a href="orderDetails.jsp?idOrder=<%=order.getId()%>" class="btn btn-primary"> Visualizza dettagli </a></td>
					
					</tr>
					
					<%
						}
					%>
				</tbody>
				
			
			</table>  
			
		</div>
	
		<%@ include file="../fragments/footer.jsp" %>
	</div>


	
</body>
</html>