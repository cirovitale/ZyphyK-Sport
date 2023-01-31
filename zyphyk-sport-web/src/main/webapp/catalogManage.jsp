<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="javax.sql.DataSource,it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean,it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf,it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO, java.util.*" %>
    
 <%
	String roles = (String) session.getAttribute("roles");

	if(roles == null){
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
	
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	ProductsInterf productsDAO = new ProductsDAO(ds);
	Set<ProductsBean> colProd = productsDAO.doRetrieveAllExists(null);
	
%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
<title>Gestione Catalogo</title>
</head>
<body>
<script>
	
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

	<div id="pagina">
		<%@ include file="../fragments/header.jsp" %>
		<div class="container" id="cont">
			<div class="card-header my-3">
				<h2>Gestione Catalogo</h2>
				<br/>
			</div>
			<div style="text-align:center">
				<a href="/zyphyk-sport-web/addInCatalog.jsp" class="btn btn-primary border-blue">
					<img src="img\icon\add.svg" alt="add-to-cart" class="icona"> Aggiungi Scarpe	
				</a>
			</div>
			
			<div class="row">
			<%
				if(!colProd.isEmpty()){
					for(ProductsBean prod: colProd){
			%>
			
				<div class="col-md-3 my-3">
					<div class="card text-center w-100" style="width: 18rem;">
						<form>
							<input type="hidden" name="userId" value="<%=prod.getId()%>">
							<a href="products.jsp?id=<%=prod.getId()%>"> <img class="card-img-top" src="ImageServlet?immagine=<%=prod.getId()%>_1.jpg" alt="Card image"> </a>
						</form>
							<!-- imagine -->
	
						<div class="card-body">
							<h5 class="card-title"><%=prod.getName()%></h5>
							<h6 class="price"> &euro; <%=prod.getPrice()%></h6>
						</div>
					</div>
				</div>
			<%	
					}
				}
			%>
			</div>
		</div>
		<%@ include file="../fragments/footer.jsp" %>
	</div>

</body>
</html>