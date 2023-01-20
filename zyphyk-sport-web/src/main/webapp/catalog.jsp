<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="javax.sql.DataSource, it.unisa.zyphyksport.model.bean.ProductsBean,	it.unisa.zyphyksport.model.interfaceDS.ProductsInterf, 
    it.unisa.zyphyksport.model.DAO.ProductsDAO, it.unisa.zyphyksport.model.interfaceDS.SizesInterf, it.unisa.zyphyksport.model.DAO.SizesDAO, it.unisa.zyphyksport.model.bean.SizesBean, java.util.*" %>
    
 <%
	String roles = (String) session.getAttribute("roles");

 	if(roles == null || roles.equals("cliente")){
		
	} else if(roles.equals("gestCat")){
		response.sendRedirect(request.getContextPath() + "/catalogManage.jsp");
	} else if (roles.equals("gestOrd")) {
		response.sendRedirect(request.getContextPath() + "/orderManage.jsp");
	}
	
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	ProductsInterf productsDAO = new ProductsDAO(ds);
	Set<ProductsBean> colProd = productsDAO.doRetrieveAllExists(null);
	
%> 


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32" href="img/icon/favicon.png">
<title>Catalogo Scarpe</title>
</head>
<body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<%@ include file="../fragments/header.jsp" %>
	<div class="container">
		<div class="card-header my-3">
			<h2>Catalogo Scarpe</h2>
			<br/>
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
						</form>
						<div class="card-body">
						
							<h5 class="card-title"><a href="products.jsp?id=<%=prod.getId()%>"><%=prod.getName()%></a></h5>
							<h6 class="price">
								&euro;
								<%=prod.getPrice()%></h6>
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
</body>
</html>