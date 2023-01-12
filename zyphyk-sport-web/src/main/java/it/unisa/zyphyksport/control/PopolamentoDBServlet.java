package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        	gestCatDAO.doSave("DarMoccia", "Dario", "Moccia", "darMoccia@gmail.com", "paperino33", 1500);
        	gestCatDAO.doSave("MasVarriale", "Massimo", "Varriale", "MasVar@gmail.com", "gestoreCat2", 1400);
        
        	gestOrdDAO.doSave("LuBacco", "Luigi", "Bacco", "lbgames@gmail.com", "paperoga104", 1700); 
        
        	managesProdsDAO.doSave("DarMoccia", "ASD56",0);
        	managesProdsDAO.doSave("DarMoccia", "3ASD7",0);
        	managesProdsDAO.doSave("DarMoccia", "ZZB35",0);
        	managesProdsDAO.doSave("DarMoccia", "QRW20",0);
        	managesProdsDAO.doSave("DarMoccia", "XXC01",0);
        	managesProdsDAO.doSave("DarMoccia", "0ASDA",0);
        	
        	managesProdsDAO.doSave("MasVarriale", "23AX1",0);
        	managesProdsDAO.doSave("MasVarriale", "X2341",0);
        	managesProdsDAO.doSave("MasVarriale", "BFD32",0);
        	managesProdsDAO.doSave("MasVarriale", "111AQ",0);
        	
	        sizesDAO.doSave("23AX1",36);
	        sizesDAO.doSave("3ASD7",36);
	        sizesDAO.doSave("XXC01",36);
	        sizesDAO.doSave("BFD32",36);
	        
	        sizesDAO.doSave("ASD56",37);
	        sizesDAO.doSave("23AX1",37);
	        
	        sizesDAO.doSave(null, 38);
	        
	        sizesDAO.doSave("23AX1",39);
	        sizesDAO.doSave("3ASD7",39);
	        sizesDAO.doSave("X2341",39);
	        sizesDAO.doSave("ZZB35",39);
	        sizesDAO.doSave("QRW20",39);
	        
	        sizesDAO.doSave("ASD56",40);
	        sizesDAO.doSave("23AX1",40);
	        sizesDAO.doSave("XXC01",40);
	        sizesDAO.doSave("111AQ",40);        
	        
	        sizesDAO.doSave("ASD56",41);
	        sizesDAO.doSave("X2341",41);
	        sizesDAO.doSave("ZZB35",41);
	        sizesDAO.doSave("QRW20",41); 
	        
	        sizesDAO.doSave("3ASD7",42);
	        sizesDAO.doSave("X2341",42);
        
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
