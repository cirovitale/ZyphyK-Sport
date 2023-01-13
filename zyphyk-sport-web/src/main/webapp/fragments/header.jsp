<div style="background-color: rgba(0, 0, 0, 0.05);" class='mb-3'>
	<%
		String ruoloHeader = (String) session.getAttribute("roles");
	%>
	<header class="d-flex flex-wrap justify-content-center py-3" >


		<ul class="nav nav-pills justify-content-center align-items-center text-center">
			<li class="nav-item"><a href="index.jsp" class=""> <img src="img/logo/logoNomeTransparent.png" style="width: 12%"></a></li>
			
			<%
				if(ruoloHeader == null){
			%>
			<li class="nav-item mt-1"><a href="index.jsp" class="nav-link" aria-current="page">Home</a></li>
			<li class="nav-item mt-1"><a href="catalog.jsp" class="nav-link">Catalogo</a></li>
			<li class="nav-item"><a href="cart.jsp" class="nav-link btn"><img src="img\icon\shopping-cart.png" class="" alt="add-to-cart" class="icona"></a></li>
			<li class="nav-item"><a href="login.jsp" class="nav-link btn"><img src="img\icon\user.png" class="img-responsive" alt="user" style="font-size:0;width:40px;height:40px;"> Accedi</a></li>
			<%
				} else if (ruoloHeader.equals("cliente")) {
			%>
			
			<li class="nav-item mt-1"><a href="index.jsp" class="nav-link" aria-current="page">Home</a></li>
			<li class="nav-item mt-1"><a href="catalog.jsp" class="nav-link">Catalogo</a></li>
			<li class="nav-item"><a href="cart.jsp" class="nav-link btn"><img src="img\icon\shopping-cart.png" alt="add-to-cart" class="icona"></a></li>
			
			<li class="nav-item">
				<div class="dropdown">
					<button class="btn dropdown-toggle border-0" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="img\icon\user.png" alt="user" style="font-size:0;width:40px;height:40px;"></button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<a class="dropdown-item" href="myProfile.jsp">Mio Profilo</a>
						<a class="dropdown-item" href="myOrders.jsp">I miei ordini</a>
						<a class="dropdown-item" href="LogoutServlet">Logout</a>
					</div>
				</div>
			</li>
			
			<%
				} else if (ruoloHeader.equals("gestCat")) {
			%>
			<li class="nav-item mt-1"><a href="index.jsp" class="nav-link" aria-current="page">Home</a></li>
			<li class="nav-item mt-1"><a href="catalogManage.jsp" class="nav-link">Gestione Catalogo</a></li>
			<li class="nav-item mt-1"><a href="modInCat.jsp" class="nav-link">Modifica Catalogo</a></li>
			
			<li class="nav-item">
				<div class="dropdown">
					<button class="btn dropdown-toggle border-0" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="img\icon\user.png" alt="user" style="font-size:0;width:40px;height:40px;"></button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<a class="dropdown-item" href="myProfile.jsp">Mio Profilo</a>
						<a class="dropdown-item" href="LogoutServlet">Logout</a>
					</div>
				</div>
			</li>
			<%
				} else if (ruoloHeader.equals("gestOrd")) {
			%>
			<li class="nav-item mt-1"><a href="orderManage.jsp" class="nav-link" aria-current="page">Gestione Ordini</a></li>
				
			<li class="nav-item">
				<div class="dropdown">
					<button class="btn dropdown-toggle border-0" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="img\icon\user.png" alt="user" style="font-size:0;width:40px;height:40px;"></button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<a class="dropdown-item" href="myProfile.jsp">Mio Profilo</a>
						<a class="dropdown-item" href="LogoutServlet">Logout</a>
					</div>
				</div>
			</li>
			<%
				}
			%>
		</ul>
	</header>

</div>