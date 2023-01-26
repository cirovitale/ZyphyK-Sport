<%@ page import="javax.sql.DataSource,it.unisa.zyphyksport.model.bean.ClientiBean,it.unisa.zyphyksport.model.bean.GestoriOrdiniBean,it.unisa.zyphyksport.model.bean.GestoriCatalogoBean, 
	java.util.*" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
	String roles = (String) session.getAttribute("roles");
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	 
	ClientiBean clBean = null;
	GestoriOrdiniBean gestOrdBean = null;
	GestoriCatalogoBean gestCatBean = null;
	if(roles == null){
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	} else if (roles.equals("gestOrd")) {
		gestOrdBean = (GestoriOrdiniBean) session.getAttribute("utente");
	} else if (roles.equals("gestCat")) {
		gestCatBean = (GestoriCatalogoBean) session.getAttribute("utente");
	} else if (roles.equals("cliente")) {
		clBean = (ClientiBean) session.getAttribute("utente");
	}
	
	
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Il mio profilo</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
<link href="style/style.css" rel="stylesheet">
<script src="script/jquery-3.6.0.min.js"></script>
</head>
<body>
	<script>
		
		
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<%@ include file="../fragments/header.jsp" %>
	<div class="container page">
		<h2>Il mio profilo</h2>
		<br/>
		<%
			if(roles == null){
		%>
		<%
			} else if(roles.equals("cliente")){
		%>
			<div class="row">
				<h4>Riepilogo dati personali</h4>
				<div class="col-md-6 mb-3"> Nome: <%=clBean.getName()%></div>
				<div class="col-md-6 mb-3"> Cognome: <%=clBean.getSurname()%></div>
				<div class="col-md-6 mb-3"> Username: <%=clBean.getUsername() %> </div>
				<div class="col-md-6 mb-3"> Data Nascita: <%=clBean.getBirthDate() %> </div>
				<div class="col-md-6 mb-3"> Email: <%=clBean.getEmail() %> </div>
				
				<div class="row">
					<div>
						<a href='myOrders.jsp' class='btn btn-primary'>Visualizza tutti gli ordini</a> <br/><br/>
					</div>
				</div>
				
			</div>
		<%
			} else if(roles.equals("gestOrd")) {
		%>
			<div class="row">
				<h4>Riepilogo dati personali</h4>
				<div class="col-md-6 mb-3"> Nome: <%=gestOrdBean.getName()%></div>
				<div class="col-md-6 mb-3"> Cognome: <%=gestOrdBean.getSurname()%></div>
				<div class="col-md-6 mb-3"> Username: <%=gestOrdBean.getUsername()%> </div>
				<div class="col-md-6 mb-3"> Retribuzione annuale: <%=gestOrdBean.getRal()%> </div>
				<div class="col-md-6 mb-3"> Email: <%=gestOrdBean.getEmail() %> </div>
				
				<div class="row">
					
					<div class="col-md-6 mb-3" >
						<a href='gestTick.jsp' class='btn btn-primary'>Gestisci ordini</a> <br/><br/>
					</div>
					
				</div>
			</div>
		<%
			} else if(roles.equals("gestCat")) {
		%>
			<div class="row">
				<h4>Riepilogo dati personali</h4>
				<div class="col-md-6 mb-3"> Nome: <%=gestCatBean.getName()%></div>
				<div class="col-md-6 mb-3"> Cognome: <%=gestCatBean.getSurname()%></div>
				<div class="col-md-6 mb-3"> Username: <%=gestCatBean.getUsername() %> </div>
				<div class="col-md-6 mb-3"> Retribuzione annuale: <%=gestCatBean.getRal() %> </div>
				<div class="col-md-6 mb-3"> Email: <%=gestCatBean.getEmail() %> </div>
				
				<div class="row">
					<div class="col-md-6 mb-3" >
						<a href='catalogManage.jsp' class='btn btn-primary'>Gestisci il catalogo</a>
					</div>
				</div>
			</div>
		<%
			}
		%>
	
	</div>
	
	
	<%@ include file="../fragments/footer.jsp" %>
</body>
</html>