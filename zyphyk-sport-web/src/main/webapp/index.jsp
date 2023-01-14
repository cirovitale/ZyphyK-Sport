<%@page
	import="it.unisa.zyphyksport.model.interfaceDS.ProductsInterf, it.unisa.zyphyksport.model.DAO.ProductsDAO, java.util.*,
	javax.sql.DataSource, it.unisa.zyphyksport.model.bean.ProductsBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%
String roles = (String) session.getAttribute("roles");

if (roles.equals("gestCat")) {
	response.sendRedirect(request.getContextPath() + "/catalogManage.jsp");
} else if (roles.equals("gestOrd")) {
	response.sendRedirect(request.getContextPath() + "/orderManage.jsp");
}

DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
ProductsInterf prodDAO = new ProductsDAO(ds);

Collection<ProductsBean> prodsArr = prodDAO.doRetrieveAllExists(null);
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="style/style.css" rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32"
	href="img/icon/favicon.png">
<title>ZyphyK Sport</title>
</head>
<body>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>


	<%@ include file="../fragments/header.jsp"%>
	<div class="container page">
		<h3 class="text-center mb-4 pb-2">Zyphyk Sport</h3>
		<br />
		<p>slider prodotti.................</p>
		<div id="carouselExampleIndicators" class="carousel slide"
			data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#carouselExampleIndicators" data-slide-to="0"
					class="active"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img class="d-block w-100"src="..." alt="First slide">
				</div>
				<div class="carousel-item">
					<img class="d-block w-100" src="..." alt="Second slide">
				</div>
				<div class="carousel-item">
					<img class="d-block w-100" src="..." alt="Third slide">
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleIndicators"
				role="button" data-slide="prev"> <span
				class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="sr-only">Previous</span>
			</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
				role="button" data-slide="next"> <span
				class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="sr-only">Next</span>
			</a>
		</div>
		<hr class="mb-4">
		<div class="row">
			<h3 class="text-center mb-4 pb-2">Alcuni nostri prodotti...</h3>
			<%
			if (!prodsArr.isEmpty()) {
				int i = 0;
				for (ProductsBean prod: prodsArr) {
					if(i == 8) break;
					i++;
			%>

			<div class="col-md-3 my-3">
				<div class="card text-center w-100" style="width: 18rem;">
					<form>
						<input type="hidden" name="userId" value="<%=prod.getId()%>">
						<!-- imagine -->
					</form>
					<div class="card-body">
						<h5 class="card-title"><%=prod.getName()%></h5>
						<h6 class="price">
							&euro;
							<%=prod.getPrice()%></h6>
						<a href="AddToCartServlet?id=<%=prod.getId()%>" class="btn"> 
						</a>
					</div>
				</div>
			</div>
			<%
				}
			}
			%>
		</div>
		<hr class="mb-4">
		<section>
			<h3 class="text-center mb-4 pb-2">FAQ</h3>
			<p class="text-center mb-5">Trova le risposte alle domande
				richieste pi&ugrave; frequentemente mostrate qui sotto.</p>

			<div class="row">
				<div class="col-md-6 col-lg-4 mb-4">
					<h6 class="mb-3 text-primary">
						<i class="far fa-paper-plane text-primary pe-2"></i> Questo sito
						&egrave; sicuro?
					</h6>
					<p>
						<strong><u>Certamente!</u></strong> Assolutamente! Lavoriamo con
						le migliori societ&agrave; di pagamento che garantiscono la tua
						sicurezza. Tutte le informazioni di fatturazione sono memorizzate
						sul nostro partner di elaborazione dei pagamenti.
					</p>
				</div>

				<div class="col-md-6 col-lg-4 mb-4">
					<h6 class="mb-3 text-primary">
						<i class="fas fa-pen-alt text-primary pe-2"></i> Posso ricevere
						assistenza?
					</h6>
					<p>
						<strong><u>Certo, &egrave; possibile!</u></strong> Tu puoi
						contattare un operatore con un tickets nella sezione dedicata
						sulla barra di navigazione, e spiegargli il problema. L'operatore
						provveder&agrave; tempestivamente a risolverlo.
					</p>
				</div>

				<div class="col-md-6 col-lg-4 mb-4">
					<h6 class="mb-3 text-primary">
						<i class="fas fa-user text-primary pe-2"></i> Come posso ottenere
						il Platinum Pass?
					</h6>
					<p>
						Acquistalo ora nella sezione Catalogo Abbonamenti > Platinum Pass.
						Oppure <a href="prodottoAbb.jsp?id=Platinum Pass">clicca qui</a>.
					</p>
				</div>
			</div>
		</section>
	</div>

	<%@ include file="../fragments/footer.jsp"%>
</body>
</html>