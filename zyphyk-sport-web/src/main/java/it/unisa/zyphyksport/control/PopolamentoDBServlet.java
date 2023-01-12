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
        	
        	cartsDAO.doSave(0, 0);
        	cartsDAO.doSave(1, 0);
        	cartsDAO.doSave(2, 0);
        	cartsDAO.doSave(3, 0);
        	
        	
      
        	cartsContainsProdsDAO.doSave(1, "ASD56");
        	cartsContainsProdsDAO.doSave(1, "23AX1");
        	cartsContainsProdsDAO.doSave(1, "ZZB35");
        	cartsContainsProdsDAO.doSave(1, "111AQ");
        	cartsContainsProdsDAO.doSave(2, "QEWER");
        	cartsContainsProdsDAO.doSave(3, "23AX1");
        	cartsContainsProdsDAO.doSave(3, "ASD56");

        	
        	
        	
			ordersDAO.doSave(0, null, null, LocalDateTime.now(),"Via Roma, 221" , "5333171120934758", 120, false);
			ordersDAO.doSave(1, null, null, LocalDateTime.now(),"Via Casa Varone, 113" , "5333171121903456", 37, false);
	        ordersDAO.doSave(2, null, null, LocalDateTime.now(),"Via Stabia, 36" , "6734536271823456", 99, true);
	        ordersDAO.doSave(3, null, null, LocalDateTime.now(),"Via De Goti, 78" , "3324558912349076", 69, true);
	        ordersDAO.doSave(4, null, null, LocalDateTime.now(),"Via Misano, 221" , "1234789467387462", 24, false);
	        
	        ordersContainsProdsDAO.doSave(0, "ASD56");
	        ordersContainsProdsDAO.doSave(0, "23AX1");
	        ordersContainsProdsDAO.doSave(0, "XXC01");
	        ordersContainsProdsDAO.doSave(1, "3ASD7");
	        ordersContainsProdsDAO.doSave(2, "3ASD7");
	        ordersContainsProdsDAO.doSave(2, "X2341");
	        ordersContainsProdsDAO.doSave(2, "QEWER");
	        ordersContainsProdsDAO.doSave(3, "111AQ");
	        ordersContainsProdsDAO.doSave(3, "0ASDA");
	        ordersContainsProdsDAO.doSave(4, "ASD56");
	        ordersContainsProdsDAO.doSave(4, "23AX1");
	        ordersContainsProdsDAO.doSave(4, "QRW20");
	        
	        
	        
	        
	        
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
