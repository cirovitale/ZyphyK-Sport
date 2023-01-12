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
        
        

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
