package it.unisa.zyphyksport.gestioneVendite.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.CartsContainsProdsInterf;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.CartsInterf;

/**
 * Servlet implementation class RemFromCartServlet
 */
@WebServlet("/RemFromCartServlet")
public class RemFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemFromCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruolo = (String) request.getSession().getAttribute("roles");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		

		if(ruolo.equals("cliente")) {
			CartsBean carrello = (CartsBean) request.getSession().getAttribute("carrello");
			String productId = request.getParameter("id");
			int size = Integer.parseInt(request.getParameter("size"));			
			
			CartsContainsProdsInterf cartContsProdDAO = new CartsContainsProdsDAO(ds);
			ProductsInterf productDAO = new ProductsDAO(ds);
			CartsInterf cartDAO = new CartsDAO(ds);
			int cartId = carrello.getId();
			List<ProductsBean> prodsArray = new ArrayList<ProductsBean>();
			List<CartsContainsProdsBean> CartContainsProdsArray = new ArrayList<CartsContainsProdsBean>();
			
			try {
				CartsContainsProdsBean cartContRem = cartContsProdDAO.doRetrieveByKey(cartId, productId, size);
				ProductsBean prodRemoved = productDAO.doRetrieveByKey(productId);
				cartDAO.doUpdate(cartId, ((int)carrello.getAmount() - ((int)prodRemoved.getPrice() * (int)cartContRem.getQuantity())));
				
				carrello.setAmount(carrello.getAmount() - (prodRemoved.getPrice() * cartContRem.getQuantity()));
				request.getSession().setAttribute("carrello", carrello);
				
				cartContsProdDAO.doDelete(carrello.getId(), productId, size);
				
				Set<CartsContainsProdsBean> cartContsProdArr = cartContsProdDAO.doRetrieveAllByCartId(cartId, null);
				for(CartsContainsProdsBean cartContProd: cartContsProdArr) {
					ProductsBean product = productDAO.doRetrieveByKey(cartContProd.getProductId());
					
					prodsArray.add(product);
					CartContainsProdsArray.add(cartContProd);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.getSession().setAttribute("prodsCart", prodsArray);
			request.getSession().setAttribute("prodsContainsCart", CartContainsProdsArray);
		}
		
		
		
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
