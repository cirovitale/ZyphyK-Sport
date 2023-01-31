package it.unisa.zyphyksport.gestioneVendite.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO;
import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf;
import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.OrdersContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.OrdersDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.OrdersContainsProdsBean;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.CartsContainsProdsInterf;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.CartsInterf;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.OrdersContainsProdsInterf;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.OrdersInterf;

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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ClientiBean cliente = (ClientiBean) request.getSession().getAttribute("utente");
		
		String username = cliente.getUsername();
		
		String via = request.getParameter("via");
		String numCivico = request.getParameter("numCivico");
		String citta = request.getParameter("città");
		String provincia = request.getParameter("provincia");
		String shippingAddress = via + " " + numCivico + " " + citta + " " + provincia;
		
		String ccNumber = (request.getParameter("cc-number"));
		String ccExpiration = (request.getParameter("cc-expiration"));
		String ccCvv = (request.getParameter("cc-cvv"));
		String paymentMethod = ccNumber + " " + ccExpiration + " " + ccCvv;
		
		CartsBean carrello = (CartsBean) request.getSession().getAttribute("carrello");
		
		OrdersContainsProdsInterf orderContProdsDAO = new OrdersContainsProdsDAO(ds);
		OrdersInterf orderDAO = new OrdersDAO(ds);
		Set<OrdersContainsProdsBean> ordContProdsArr = new HashSet<OrdersContainsProdsBean>();
		
		
		CartsContainsProdsInterf cartContProdsDAO = new CartsContainsProdsDAO(ds);
		ProductsInterf productsDAO = new ProductsDAO(ds);
		
		int orderId = -1;
		try {
			orderId = orderDAO.doSave(username, null, LocalDateTime.now(), shippingAddress, paymentMethod, carrello.getAmount(), false);
			
			Set<CartsContainsProdsBean> cartContProdsArr =  cartContProdsDAO.doRetrieveAllByCartId(carrello.getId(), null);
			for(CartsContainsProdsBean cartContProdsBean: cartContProdsArr) {
				ProductsBean prod = productsDAO.doRetrieveByKey(cartContProdsBean.getProductId());
				OrdersContainsProdsBean ordContProdsBean = new OrdersContainsProdsBean(orderId, cartContProdsBean.getProductId(), cartContProdsBean.getQuantity(), cartContProdsBean.getSize(), prod.getPrice());
				ordContProdsArr.add(ordContProdsBean);	
				
				// DEBUG
				System.out.println("prod: "+prod);
				System.out.println("cartContProdsArr: "+cartContProdsBean);
				System.out.println("ordContProdsBean: "+ordContProdsBean + "\n\n");
				System.out.println("quantità: "+ordContProdsBean.getQuantity() + "\n\n");
				
				
				orderContProdsDAO.doSave(orderId, ordContProdsBean.getProductId(), ordContProdsBean.getQuantity(), ordContProdsBean.getSize());
				cartContProdsDAO.doDelete(cartContProdsBean.getCartId(), cartContProdsBean.getProductId(), cartContProdsBean.getSize());
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CartsInterf cartsDAO = new CartsDAO(ds);
		carrello.setAmount(0);
		try {
			cartsDAO.doUpdate(cliente.getCartId(), 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ProductsBean> prodsArray = new ArrayList<ProductsBean>();
		List<CartsContainsProdsBean> prodsContainsCartArray = new ArrayList<CartsContainsProdsBean>();
		request.getSession().setAttribute("prodsCart", prodsArray);
		request.getSession().setAttribute("prodsContainsCart", prodsContainsCartArray);
		
		request.getSession().setAttribute("orderId", orderId);
		response.sendRedirect(request.getContextPath()+"/thankYouPage.jsp");
		
	}

}
