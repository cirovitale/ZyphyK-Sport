package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
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
        	// Creazione Prodotti
			productDAO.doSave("ASD56", "PRODUCT_1", "SPORT_1", "BRAND_1", 100);
			productDAO.doSave("23AX1", "PRODUCT_2", "SPORT_2", "BRAND_2", 100);
	        productDAO.doSave("3ASD7", "PRODUCT_3", "SPORT_2", "BRAND_2", 100);
	        productDAO.doSave("X2341", "PRODUCT_4", "SPORT_1", "BRAND_2", 100);
	        productDAO.doSave("ZZB35", "PRODUCT_5", "SPORT_3", "BRAND_3", 100);
	        productDAO.doSave("QRW20", "PRODUCT_6", "SPORT_3", "BRAND_1", 100);
	        productDAO.doSave("XXC01", "PRODUCT_7", "SPORT_3", "BRAND_1", 100);
	        productDAO.doSave("QEWER", "PRODUCT_7", "SPORT_2", "BRAND_1", 100);
	        productDAO.doSave("BFD32", "PRODUCT_8", "SPORT_1", "BRAND_3", 100);
	        productDAO.doSave("111AQ", "PRODUCT_9", "SPORT_2", "BRAND_2", 100);
	        productDAO.doSave("0ASDA", "PRODUCT_10", "SPORT_2", "BRAND_2", 100);
	        
	        
	     // Creazione Clienti
	        clientiDAO.doSave("daniPicci", 0, "Daniele", "Piccirillo", "dani_san@gmail.com", "passw1234", LocalDateTime.of(1996, 9, 6, 0, 0));
	        clientiDAO.doSave("daniPicci", 1, "Mario", "Rossi", "mar-rossi@gmail.com", "cliente", LocalDateTime.of(1996, 6, 13, 0, 0));
	        clientiDAO.doSave("daniPicci", 2, "Giuseppe", "Roma", "g.rom1@gmail.com", "cliente", LocalDateTime.of(2001, 4, 25, 0, 0));
	        clientiDAO.doSave("daniPicci", 3, "Angelo", "Portelli", "portelli_angelo@gmail.com", "cliente", LocalDateTime.of(1978, 1, 7, 0, 0));
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
