<%@ page language="java" import="it.unisa.zyphyksport.model.bean.CartsBean, it.unisa.zyphyksport.model.bean.CartsContainsProdsBean, it.unisa.zyphyksport.model.bean.ProductsBean, java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>

<%	
	Boolean flag = false;
	Boolean responseCart = (Boolean) session.getAttribute("responseCart");
	Collection<ProductsBean> products = new ArrayList<ProductsBean>();
	Collection<CartsContainsProdsBean> prodsContainsCart = new ArrayList<CartsContainsProdsBean>();
	CartsBean carrello = (CartsBean) session.getAttribute("carrello");
	String roles = (String) session.getAttribute("roles");
	
	if(roles == null) {
		flag = false;
	} else if (roles.equals("gestCat") || roles.equals("gestOrd")){
		response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
	} else if (roles.equals("cliente")){
		flag = true;
		products = (Set<ProductsBean>) session.getAttribute("prodsCart");
		prodsContainsCart = (Set<CartsContainsProdsBean>) session.getAttribute("prodsContainsCart");
	}
%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Carrello</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
</head>
<script src="script/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
	function remOggetto(id, size){
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(){
			if (this.readyState == 4 && this.status == 200){
				document.getElementById("pagina").innerHTML = this.responseText;
			}
		};
		xhttp.open("GET","RemFromCartServlet?id=" + id + "&size=" + size, true);
		xhttp.send();
	}
	
	function checkTot(tot){
		if(tot == "0"){
			alert("Per procedere all'acquisto devi contenere almeno un oggetto nel carrello.");
		} else {
			window.location.href="checkOut.jsp";
		}
	}
	
	function modQuantity(id, size, offset){
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(){
			if (this.readyState == 4 && this.status == 200){
				document.getElementById("pagina").innerHTML = this.responseText;
			}
		};
		xhttp.open("GET","ModQuantityInCartServlet?id=" + id + "&size=" + size + "&offset=" + offset, true);
		xhttp.send();
	}
	
</script>
<body>
<div id= "pagina">
<%@ include file="fragments/header.jsp"%>
<%
	if(responseCart != null && responseCart == false) {
%>
		<jsp:forward page="/GetProdsFromCartServlet"/>
		
<%
	}
%>
<%
	if(flag == true){
%>
	
	<div id = "cont" class="container page">
	<h2>Carrello</h2>
		<div class="d-flex py-3"><h3>Prezzo totale: &euro; <%= carrello.getAmount() %></h3><button class="mx-3 btn btn-primary" onclick='checkTot("<%= carrello.getAmount() %>")'>Compra ora</button></div>

		<%
			if(products.size() == 0){
		%>		
			<p>Il carrello è vuoto.</p>	
		<% 
			}else{
				
			
		%>
		<table class="table table-light align-middle">
			<thead>
				<tr>
					<th scope="col">Nome</th>
					<th scope="col">Taglia</th>
					<th scope="col">Brand</th>
					<th scope="col">Costo</th>
					<th scope="col">Quantità</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>			
				<% 
					for(int i = 0; i < products.size(); i++){
						ProductsBean prod = (ProductsBean) products.toArray()[i];
						CartsContainsProdsBean prodCont = (CartsContainsProdsBean) prodsContainsCart.toArray()[i];
				%>
				
				<tr id = "<%=prod.getId()%>">
					<td><%=prod.getName()%></td>
					<td><%=prodCont.getSize() %></td>
					<td><%= prod.getBrand()%></td>
					<td>&euro; <%= prod.getPrice()%></td>
					<td>
						<button type="button" class="btn btn-sm btn-danger" onclick='modQuantity("<%= prod.getId() %>", "<%= prodCont.getSize() %>", "-1")'>-</button>
						<%=prodCont.getQuantity() %>
						<button type="button" class="btn btn-sm btn-danger" onclick='modQuantity("<%= prod.getId() %>", "<%= prodCont.getSize() %>", "1")'>+</button>
					</td>
					<td><button type="button" class="btn btn-sm btn-danger" onclick='remOggetto("<%= prod.getId() %>", "<%= prodCont.getSize() %>")'>Rimuovi</button></td>
				</tr>
				<%
					}
				%>
				
			</tbody>
		
		</table>
		<%
			}
		%>

	</div>			
<% 
	}else{
%>		
		<div id = "cont" class="container page">
			<h2>Carrello</h2>
			<br/>
			<p>La funzionalità carrello non è disponibile se non sei autenticato, effettua il <a href = "login.jsp">login</a></p>
		</div>
<%	
	}

%>
<%@ include file="fragments/footer.jsp" %>

</div>

</body>
</html>