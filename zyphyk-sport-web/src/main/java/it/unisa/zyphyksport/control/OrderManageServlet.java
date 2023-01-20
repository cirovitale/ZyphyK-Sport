package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.zyphyksport.model.DAO.OrdersDAO;
import it.unisa.zyphyksport.model.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.model.bean.OrdersBean;
import it.unisa.zyphyksport.model.interfaceDS.OrdersInterf;

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
			ordBean.setSent(true);
			ordBean.setGestOrdUsername(gestOrd.getUsername());
			
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
