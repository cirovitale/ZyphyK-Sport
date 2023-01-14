<%@ page language="java" import="javax.sql.DataSource, it.unisa.zyphyksport.model.DAO.OrdersDAO, it.unisa.zyphyksport.model.interfaceDS.OrdersInterf,
	java.util.*, it.unisa.zyphyksport.model.bean.OrdersBean" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8"%>
  
<%
	int id = (int) session.getAttribute("orderId");
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	OrdersInterf ordDAO = new OrdersDAO(ds);
	OrdersBean order = ordDAO.doRetrieveByKey(id);
%> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Acquisto Effettuato</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
</head>
<body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>


	<%@ include file="../fragments/header.jsp" %>
 	<div class="container page">
 		<h2>Grazie per aver effettuato l'acquisto.</h2>
 		<br/>
 		<p>Id acquisto: <%= id %> </p>
 		<p>Id acquisto: <%= order.getId() %> </p>
 		<p><a href="myOrders.jsp">Visualizza tutti gli ordinhi</a> oppure <a href="index.jsp">torna alla home page</a></p>
 	</div>
 	<%@ include file="../fragments/footer.jsp" %>
</body>
</html>