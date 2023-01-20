<%@ page language="java" import="javax.sql.DataSource, it.unisa.zyphyksport.model.bean.OrdersBean, it.unisa.zyphyksport.model.interfaceDS.OrdersInterf, 
it.unisa.zyphyksport.model.DAO.OrdersDAO, java.util.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8" %>
<!DOCTYPE html>

<%
	String roles = (String) session.getAttribute("roles");
	
	if(roles == null){
		response.sendRedirect(request.getContextPath() + "/login-form.jsp");
	} else if (roles.equals("admin") || roles.equals("supVid") || roles.equals("cliente")) {
	response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
	}
	
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	OrdersInterf ordersDAO = new OrdersDAO(ds);
	Set<OrdersBean> ordsArr = ordersDAO.doRetrieveAll(null);
%>



<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
<title>Gestione Ordini</title>
</head>
<body>
<script src="script/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<script>
	function setSent(id){
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(){
			if (this.readyState == 4 && this.status == 200){
				document.getElementById("pagina").innerHTML = this.responseText;
			}
		};
		xhttp.open("GET","OrderManageServlet?id=" + id,true);
		xhttp.send();
	}
</script>


<div id="pagina">
<%@ include file="../fragments/header.jsp" %>
<div id = "cont" class="container page">
	<h2>Gestione Ordini</h2>
		
		<br/>
		<%
			if(ordsArr.isEmpty()){
		%>		
			<p>Non ci sono ordini da gestire.</p>	
		<% 
			}else{
		%>
		<table class="table table-light align-middle">
			<thead>
				<tr>
					<th scope="col">Id</th>
					<th scope="col">Cliente</th>
					<th scope="col">Data</th>
					<th scope="col">Costo</th>
					<th scope="col">Stato</th>
					<th scope="col">Modifica Stato</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<% 
					for(OrdersBean ordBean : ordsArr){
				%>
				
					<tr id = "<%= ordBean.getId() %>">
					
						<td>#<%= ordBean.getId() %></td>
						<td><%= ordBean.getClienteUsername() %></td>
						<td><%= ordBean.getDateTime() %></td>
						<td><%= ordBean.getAmount() %></td>
						<td><%= ordBean.isSent() %></td>
						<td>
							<button type="button" class="btn btn-primary" onclick='setSent("<%= ordBean.getId() %>")' <%if (ordBean.isSent() == true) %> disabled
							>Modifica stato</button>
						</td>
						
					</tr>
				<%
					}
				}
				%>
			
			</tbody>
		</table>
	</div>
	<%@ include file="../fragments/footer.jsp" %>
	</div>
</body>
</html>