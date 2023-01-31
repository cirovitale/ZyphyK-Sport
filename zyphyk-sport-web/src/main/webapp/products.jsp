<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="javax.sql.DataSource,it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean,it.unisa.zyphyksport.gestioneCatalogo.bean.SizesBean,it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf,it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.SizesInterf,it.unisa.zyphyksport.gestioneUtente.interfaceDS.ClientiInterf,it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO,it.unisa.zyphyksport.gestioneCatalogo.DAO.SizesDAO,it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO, java.util.*" %>
    
 <%
	String roles = (String) session.getAttribute("roles");
 	Boolean responseCart = null;

	if(roles == null){
		
	} else if (roles.equals("gestOrd")) {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}else{
		responseCart = (Boolean) session.getAttribute("responseCart");
	}
	
	String id = (String) request.getParameter("id");
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	
	ProductsInterf productsDAO = new ProductsDAO(ds);
	SizesInterf sizesDAO = new SizesDAO(ds);
	ClientiInterf clientiDAO = new ClientiDAO(ds);
	
	ProductsBean prodBean = productsDAO.doRetrieveByKey(id);
	
	Set<ProductsBean> colProd = productsDAO.doRetrieveAllExists(null);
	Set<SizesBean> collSizes = sizesDAO.doRetrieveByProductId(id, null);	
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

<script>




	function remOggetto(productId){
		/*
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(){
			if (this.readyState == 4 && this.status == 200){
				document.getElementById("pagina").innerHTML = this.responseText;
			}
		};
		xhttp.open("GET","RemFromCatalogServlet?productId=" + productId,true);
		xhttp.send();
		*/
		document.location.href ="RemFromCatalogServlet?productId=" + productId;
	}
	
	function getLocation(id){
	
			var e = document.getElementById("size");
			var value = e.value;
			console.log(value);
			var text = e.options[e.selectedIndex].text;
			console.log(id);
			console.log(text);
			/*
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function(){
				if (this.readyState == 4 && this.status == 200){
					document.getElementById("pagina").innerHTML = this.responseText;
				}
			};
			xhttp.open("GET","AddToCartServlet?id=" + id + "&size=" + text, true);
			xhttp.send();
			*/
			document.location.href="AddToCartServlet?id=" + id + "&size=" + value;
			
		}





</script>

</head>
<body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<h2 class="text-center"><%=prodBean.getName()%></h2>
	</div>

	<div class="container mt-5 mb-5" id="product-section"> <!-- container principale --> 
	
	<!-- per quantità e size mettere quantity e size -->
		<div class="row text-center">
			<div class="col-md-6 border-end">
				<img class="image-responsive" width="60%" src="ImageServlet?immagine=<%=prodBean.getId()%>_1.jpg" alt="image">
			</div>
					<!-- immagine -->
			<div class="col-md-6">
				<h3>Scarpe</h3>
				<br/>
				<h5>Prezzo: &euro;<%= prodBean.getPrice()%></h5>
				<div class="mt-5"> <span class="fw-bold">Caratteristiche:</span>
                   <div>
                       <ul style="list-style-type:none">
							<li class="text-center">Categoria sportiva: <%= prodBean.getSport() %></li>
							<li class="text-center">Brand: <%= prodBean.getBrand() %></li>
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
								}else{
								%>
								<option value="0">Prodotto non disponibile</option>
								<%
								}
								%>
							</select>
						</div>

					</div>
					
               </div>
               
			

               <br/>
               <%
               		if(roles == null || roles.equals("cliente")){
               %>
               		<div class= "col md-6 mb-3">
               			<button type="button" class="btn btn-sm btn-primary" onclick='getLocation("<%=prodBean.getId()%>", "<%=roles%>")'><img src="img\icon\shopping-cart.svg" alt="add-to-cart" class="icona"> Aggiungi al carrello</button>
               		</div>
               <%
               		}else{
               %>		
               		<div class="col-md-6">
	               		<button type="button" class="btn btn-danger" onclick='remOggetto("<%=prodBean.getId()%>")'>
								<img src="img\icon\trash.svg" alt="rem-product" class="icona">Rimuovi Scarpa</button>
					
					
						<a href="/zyphyk-sport-web/modInCatalog.jsp?id=<%=prodBean.getId()%>" class="btn btn-primary"> 	
							<img src="img\icon\pencil.svg" alt="mod-abb" class="icona">	Modifica Scarpa
						</a>
					</div>
					
			   <%
			   		}
			   %>
			</div>

		</div><!-- end row -->	
	
	
	
	
	
	
	
	
	
	
	
	</div> <!-- chiusura container principale --> 




</body>
</html>