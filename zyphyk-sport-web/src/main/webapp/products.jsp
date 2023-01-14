<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="javax.sql.DataSource, it.unisa.zyphyksport.model.bean.ProductsBean, it.unisa.zyphyksport.model.bean.SizesBean,
    it.unisa.zyphyksport.model.interfaceDS.ProductsInterf, it.unisa.zyphyksport.model.interfaceDS.SizesInterf, it.unisa.zyphyksport.model.interfaceDS.ClientiInterf,
    it.unisa.zyphyksport.model.DAO.ProductsDAO, it.unisa.zyphyksport.model.DAO.SizesDAO, it.unisa.zyphyksport.model.DAO.ClientiDAO, java.util.*" %>
    
 <%
	String roles = (String) session.getAttribute("roles");

	if(roles == null){
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
	
	String id = (String) request.getParameter("id");
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	
	ProductsInterf productsDAO = new ProductsDAO(ds);
	SizesInterf sizesDAO = new SizesDAO(ds);
	ClientiInterf clientiDAO = new ClientiDAO(ds);
	
	ProductsBean prodBean = productsDAO.doRetrieveByKey(id);
	
	Collection<ProductsBean> colProd = productsDAO.doRetrieveAllExists(null);
	Collection<SizesBean> collSizes = sizesDAO.doRetrieveByProductId(id, null);	
%> 
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><%=prodBean.getName()%></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700|Open+Sans:400,700">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link href="style/style.css" rel="stylesheet">

<script src="script/jquery-3.6.0.min.js"></script>

</head>
<body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<h2 class="text-center"><%=prodBean.getName()%></h2>
	</div>

	<div class="container mt-5 mb-5" id="product-section"> <!-- container principale --> 
	
	<!-- per quantit� e size mettere quantity e size -->
		<div class="row text-center">
					<!-- immagine -->
			<div class="col-md-6">
				<h3>Scarpe</h3>
				<br/>
				<h5>Prezzo: &euro;<%= prodBean.getPrice()%></h5>
				<div class="mt-5"> <span class="fw-bold">Caratteristiche:</span>
                   <div>
                       <ul style="list-style-type:none">
							<li class="text-center">Categoria sportiva: <%= prodBean.getSport() %></li>
							<li class="text-center">Brand: <%= prodBean.getName() %></li>
						</ul>
						<div id="sizeSel">
							<label for="sizeSelect">Scegli tra le taglie disponibili:</label>
							<select name="size" id="size"
								class=' col-md-6 mb-3 form-control'>
								<%
								if (!collSizes.isEmpty()) {
									for (SizesBean size : collSizes) {
								%>
								<option value="<%=size.getValue()%>"><%=size.getValue()%></option>
								<%
									}
								}
								%>
							</select>
						</div>

					</div>
					
               </div>
               
			</div>

               <br/>
               <a href="AddToCartServlet?id=<%=prodBean.getId()%>" class="btn border-dark"> 
               		<img src="img\icon\shopping-cart.svg" alt="add-to-cart" class="icona"> Aggiungi al carrello
			   </a>

		</div><!-- end row -->	
	
	
	
	
	
	
	
	
	
	
	
	</div> <!-- chiusura container principale --> 




</body>
</html>