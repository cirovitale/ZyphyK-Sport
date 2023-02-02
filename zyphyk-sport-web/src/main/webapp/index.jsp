<%@page
	import="it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf,it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO,java.util.*,javax.sql.DataSource,it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%
String roles = (String) session.getAttribute("roles");

if(roles == null){
	
}else if (roles.equals("gestCat")) {
	response.sendRedirect(request.getContextPath() + "/catalogManage.jsp");
} else if (roles.equals("gestOrd")) {
	response.sendRedirect(request.getContextPath() + "/orderManage.jsp");
}

DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
ProductsInterf prodDAO = new ProductsDAO(ds);

Set<ProductsBean> prodsArr = prodDAO.doRetrieveAllExists(null);
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
		<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
  <ol class="carousel-indicators">
    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img class="d-block w-100" src="img\slider\SLIDER1.png" alt="First slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="img\slider\SLIDER2.png" alt="Second slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="img\slider\SLIDER3.png" alt="Third slide">
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Precedente</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Successivo</span>
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
						<a href="products.jsp?id=<%=prod.getId()%>"> <img class="card-img-top" src="ImageServlet?immagine=<%=prod.getId()%>_1.jpg" alt="Card image"> </a>
					</form>
					<div class="card-body">
							<h5 class="card-title"><%= prod.getName() %></h5>
							<h6 class="price">&euro;<%=prod.getPrice()%></h6>
						

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
						<strong>Certamente!</strong> Assolutamente! Lavoriamo con
						le migliori societ&agrave; di pagamento che garantiscono la tua
						sicurezza. Tutte le informazioni di fatturazione sono memorizzate
						sul nostro partner di elaborazione dei pagamenti.
					</p>
				</div>

				<div class="col-md-6 col-lg-4 mb-4">
					<h6 class="mb-3 text-primary">
						<i class="fas fa-pen-alt text-primary pe-2"></i> Come posso effettuare un acquisto su questo sito?
					</h6>
					<p>
						Per effettuare un acquisto basta seguire i seguenti passaggi: scegliere il prodotto desiderato, aggiungerlo al carrello, 
						procedere alla cassa e inserire i dettagli della spedizione e del pagamento.
					</p>
				</div>

				<div class="col-md-6 col-lg-4 mb-4">
					<h6 class="mb-3 text-primary">
						<i class="fas fa-user text-primary pe-2"></i> Quanto tempo ci vuole per ricevere il mio ordine?
					</h6>
					<p>
						 Il tempo di consegna dipende dalla destinazione, dal metodo di consegna scelto e dalla disponibilità dei prodotti.
					</p>
				</div>
			</div>
		</section>
	</div>

	<%@ include file="../fragments/footer.jsp"%>
</body>
</html>