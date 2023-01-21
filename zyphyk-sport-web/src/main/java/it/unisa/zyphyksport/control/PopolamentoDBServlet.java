package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.zyphyksport.model.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.model.DAO.CartsDAO;
import it.unisa.zyphyksport.model.DAO.ClientiDAO;
import it.unisa.zyphyksport.model.DAO.GestoriCatalogoDAO;
import it.unisa.zyphyksport.model.DAO.GestoriOrdiniDAO;
import it.unisa.zyphyksport.model.DAO.ManagesProdsDAO;
import it.unisa.zyphyksport.model.DAO.OrdersContainsProdsDAO;
import it.unisa.zyphyksport.model.DAO.OrdersDAO;
import it.unisa.zyphyksport.model.DAO.ProductsDAO;
import it.unisa.zyphyksport.model.DAO.SizesDAO;

/**
 * Servlet implementation class PopolamentoDBServlet
 */
@WebServlet("/PopolamentoDBServlet")
public class PopolamentoDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PopolamentoDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		PrintWriter out = response.getWriter();
		out.println("<p> Servlet di popolamento effettuata con successo. </p>");
		
		// Creazione DataSource necessari
		GestoriCatalogoDAO gestCatDAO = new GestoriCatalogoDAO(ds);
		GestoriOrdiniDAO gestOrdDAO = new GestoriOrdiniDAO(ds);
        CartsDAO cartsDAO = new CartsDAO(ds);
        ClientiDAO clientiDAO = new ClientiDAO(ds);
        ProductsDAO productDAO = new ProductsDAO(ds);
        OrdersDAO ordersDAO = new OrdersDAO(ds);
        SizesDAO sizesDAO = new SizesDAO(ds);        
        ManagesProdsDAO managesProdsDAO = new ManagesProdsDAO(ds);
        CartsContainsProdsDAO cartsContainsProdsDAO = new CartsContainsProdsDAO(ds);
        OrdersContainsProdsDAO ordersContainsProdsDAO = new OrdersContainsProdsDAO(ds);
 
        try {
        	gestCatDAO.doSave("DarMoccia", "Dario", "Moccia", "darMoccia@gmail.com", "paperino33", 1500);
        	gestCatDAO.doSave("MasVarriale", "Massimo", "Varriale", "MasVar@gmail.com", "gestoreCat2", 1400);
        
        	gestOrdDAO.doSave("LuBacco", "Luigi", "Bacco", "lbgames@gmail.com", "paperoga104", 1700); 
 
        	cartsDAO.doSave(386);
        	cartsDAO.doSave(39);
        	cartsDAO.doSave(179);
        	cartsDAO.doSave(0);

        	
   	     // Creazione Clienti
	        clientiDAO.doSave("daniPicci", 1, "Daniele", "Piccirillo", "dani_san@gmail.com", "passw1234", LocalDate.of(1996, 9, 6));
	        clientiDAO.doSave("marioRossi", 2, "Mario", "Rossi", "mar-rossi@gmail.com", "cliente", LocalDate.of(1996, 6, 13));
	        clientiDAO.doSave("peppeRoma", 3, "Giuseppe", "Roma", "g.rom1@gmail.com", "cliente", LocalDate.of(2001, 4, 25));
	        clientiDAO.doSave("angeloPorte", 4, "Angelo", "Portelli", "portelli_angelo@gmail.com", "cliente", LocalDate.of(1978, 1, 7));
        	
        	// Creazione Prodotti
			productDAO.doSave("ASD56", "Air Zoom Structure 24", "running", "nike", 59);
			productDAO.doSave("23AX1", "Courtjam Control", "tennis", "adidas", 60);
	        productDAO.doSave("3ASD7", "Nike Air Zoom Vapor Pro", "tennis", "nike", 84);
	        productDAO.doSave("X2341", "Ultraboost 20", "running", "adidas", 99);
	        productDAO.doSave("ZZB35", "KING 21", "calcetto", "puma", 39);
	        productDAO.doSave("QRW20", "Air Zoom Pegasus", "calcetto", "nike", 69);
	        productDAO.doSave("XXC01", "Samba", "calcetto", "adidas", 115);
	        productDAO.doSave("QEWER", "NikeCourt Zoom Pro", "tennis", "nike", 39);
	        productDAO.doSave("BFD32", "Electrify Nitro 2", "running", "puma", 75);
	        productDAO.doSave("111AQ", "Courtflash Speed", "tennis", "adidas", 90);
	        productDAO.doSave("0ASDA", "BREAKSHOT 3 CC", "tennis", "mizuno", 79);

	        sizesDAO.doSave("23AX1",36);
	        sizesDAO.doSave("3ASD7",36);
	        sizesDAO.doSave("XXC01",36);
	        sizesDAO.doSave("BFD32",36);  
	        //sizesDAO.doSave("0ASDA",36);
	        sizesDAO.doSave("ASD56",37);
	        sizesDAO.doSave("23AX1",37);
	        sizesDAO.doSave("QEWER",38); 
	        //sizesDAO.doSave("0ASDA",38);
	        sizesDAO.doSave("23AX1",39);
	        sizesDAO.doSave("3ASD7",39);
	        sizesDAO.doSave("X2341",39);
	        sizesDAO.doSave("ZZB35",39);
	        sizesDAO.doSave("QRW20",39);	
	        //sizesDAO.doSave("0ASDA",39);
	        sizesDAO.doSave("ASD56",40);
	        sizesDAO.doSave("23AX1",40);
	        sizesDAO.doSave("XXC01",40);
	        sizesDAO.doSave("111AQ",40);      
	        sizesDAO.doSave("QEWER",40);  
	        sizesDAO.doSave("ASD56",41);
	        sizesDAO.doSave("X2341",41);
	        sizesDAO.doSave("ZZB35",41);
	        sizesDAO.doSave("QRW20",41); 	       
	        sizesDAO.doSave("QEWER",41); 
	        sizesDAO.doSave("3ASD7",42);
	        sizesDAO.doSave("X2341",42);
	        //sizesDAO.doSave("0ASDA",42);
	        
        	cartsContainsProdsDAO.doSave(1, "ASD56", 1, 39);
        	cartsContainsProdsDAO.doSave(1, "23AX1", 2, 37);
        	cartsContainsProdsDAO.doSave(1, "ZZB35", 3, 36);
        	cartsContainsProdsDAO.doSave(1, "111AQ", 1, 39);
        	cartsContainsProdsDAO.doSave(2, "QEWER", 1, 41);
        	cartsContainsProdsDAO.doSave(3, "23AX1", 2, 36);
        	cartsContainsProdsDAO.doSave(3, "ASD56", 1, 39);
        	
        	managesProdsDAO.doSave("DarMoccia", "ASD56", 0);
        	managesProdsDAO.doSave("DarMoccia", "3ASD7", 0);
        	managesProdsDAO.doSave("DarMoccia", "ZZB35", 0);
        	managesProdsDAO.doSave("DarMoccia", "QRW20", 0);
        	managesProdsDAO.doSave("DarMoccia", "XXC01", 0);
        	managesProdsDAO.doSave("DarMoccia", "0ASDA", 0);
        	managesProdsDAO.doSave("MasVarriale", "23AX1", 0);
        	managesProdsDAO.doSave("MasVarriale", "X2341", 0);
        	managesProdsDAO.doSave("MasVarriale", "BFD32", 0);
        	managesProdsDAO.doSave("MasVarriale", "111AQ", 0);       	

			ordersDAO.doSave("daniPicci", null, LocalDateTime.of(2022,11,23,12,45),"Via Roma, 221" , "5333171120934758", 294, false);
			ordersDAO.doSave("marioRossi", null, LocalDateTime.of(2023,5,11,18,23),"Via Casa Varone, 113" , "5333171121903456", 252, false);
	        ordersDAO.doSave("daniPicci", null, LocalDateTime.of(2022,1,30,22,47),"Via Stabia, 36" , "6734536271823456", 321, false);
	        ordersDAO.doSave("peppeRoma", null, LocalDateTime.of(2022,9,23,21,34),"Via De Goti, 78" , "3324558912349076", 439, false);
	        ordersDAO.doSave("angeloPorte", null, LocalDateTime.of(2023,1,12,19,8),"Via Misano, 221" , "1234789467387462", 247, false);
	        
	        ordersDAO.doUpdateSent(2, "LuBacco");
	        ordersDAO.doUpdateSent(3, "LuBacco");
	        
	        ordersContainsProdsDAO.doSave(1, "ASD56", 1, 39);
	        ordersContainsProdsDAO.doSave(1, "23AX1", 2, 37);
	        ordersContainsProdsDAO.doSave(1, "XXC01", 1, 36);
	        ordersContainsProdsDAO.doSave(2, "3ASD7", 3, 39);
	        ordersContainsProdsDAO.doSave(3, "3ASD7", 1, 41);
	        ordersContainsProdsDAO.doSave(3, "X2341", 2, 36);
	        ordersContainsProdsDAO.doSave(3, "QEWER", 1, 38);
	        ordersContainsProdsDAO.doSave(4, "111AQ", 4, 38);
	        ordersContainsProdsDAO.doSave(4, "0ASDA", 1, 41);
	        ordersContainsProdsDAO.doSave(5, "ASD56", 2, 40);
	        ordersContainsProdsDAO.doSave(5, "23AX1", 1, 37);
	        ordersContainsProdsDAO.doSave(5, "QRW20", 1, 39); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
