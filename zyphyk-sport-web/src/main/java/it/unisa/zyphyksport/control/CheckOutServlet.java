package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.zyphyksport.model.DAO.CartsDAO;
import it.unisa.zyphyksport.model.DAO.GestoriOrdiniDAO;
import it.unisa.zyphyksport.model.DAO.OrdersContainsProdsDAO;
import it.unisa.zyphyksport.model.DAO.OrdersDAO;
import it.unisa.zyphyksport.model.bean.CartsBean;
import it.unisa.zyphyksport.model.bean.ClientiBean;
import it.unisa.zyphyksport.model.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.model.interfaceDS.GestoriOrdiniInterf;
import it.unisa.zyphyksport.model.interfaceDS.OrdersContainsProdsInterf;
import it.unisa.zyphyksport.model.interfaceDS.OrdersInterf;

/**
 * Servlet implementation class CheckOutServlet
 */
@WebServlet("/CheckOutServlet")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClientiBean cliente = (ClientiBean) request.getSession().getAttribute("utente");
		CartsBean carrello = (CartsBean) request.getSession().getAttribute("carrello");
		
		Collection<VideogiocoBean> arrVid = carrello.getArrVidBean();
		
		String username = cliente.getUsername();
		
		
		String via = request.getParameter("via");
		String numCivico = request.getParameter("numCivico");
		String citta = request.getParameter("città");
		String provincia = request.getParameter("provincia");
		String numCarta = (request.getParameter("cc-number"));
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");		
		OrdersContainsProdsInterf acqVidDS = new OrdersContainsProdsDAO(ds);
		OrdersInterf ordDS = new OrdersDAO(ds);
		GestoriOrdiniInterf gestOrdDS = new GestoriOrdiniDAO(ds);
		Collection<GestoriOrdiniBean> gestBean = gestOrdDS.doRetrieveAll(username); 
		
    	int min = 100000;
		int max = 999999;
    	int id = (int) (Math.random() * (max - min)) + min;
  	
    	
    	try {
    		ordDS.doSave(id,username, gestOrd, LocalDateTime.now(), via + ", " + numCivico + ", " citta + ", " + provincia, numCarta, carrello.getAmount(), false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    
		for(VideogiocoBean vidBean : arrVid){
			try {
				acqVidDS.doSave(id,vidBean.getCodice());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			acqDS.doUpdate(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		request.getSession().setAttribute("carrello", new CartsDAO(ds));
		 
		request.getSession().setAttribute("idAcquisto", id);
		response.sendRedirect(request.getContextPath()+"/thankYouPage.jsp");
		
	}

}
