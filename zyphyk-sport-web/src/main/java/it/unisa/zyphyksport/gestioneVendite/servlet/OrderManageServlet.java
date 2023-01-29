package it.unisa.zyphyksport.gestioneVendite.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.zyphyksport.gestioneUtente.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.gestioneVendite.DAO.OrdersDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.OrdersBean;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.OrdersInterf;

/**
 * Servlet implementation class OrderManageServlet
 */
@WebServlet("/OrderManageServlet")
public class OrderManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		GestoriOrdiniBean gestOrd = (GestoriOrdiniBean) request.getSession().getAttribute("utente");
		
		int orderId = Integer.parseInt(request.getParameter("id"));
		OrdersInterf ordDAO = new OrdersDAO(ds);
		
		try {
			OrdersBean ordBean = ordDAO.doRetrieveByKey(orderId);
			boolean spedito = ordBean.isSent();
			if (spedito!=true) {
				ordBean.setSent(true);
				ordBean.setGestOrdUsername(gestOrd.getUsername());				
			} else {
				request.setAttribute("message", "true");
				String redirectedPage = "/orderManage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirectedPage);
				dispatcher.forward(request, response);				
				
			}
			
			
			ordDAO.doUpdateSent(orderId, gestOrd.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/orderManage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
