package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import it.unisa.zyphyksport.model.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.model.DAO.CartsDAO;
import it.unisa.zyphyksport.model.DAO.ProductsDAO;
import it.unisa.zyphyksport.model.bean.CartsBean;
import it.unisa.zyphyksport.model.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.model.bean.ProductsBean;
import it.unisa.zyphyksport.model.interfaceDS.CartsContainsProdsInterf;
import it.unisa.zyphyksport.model.interfaceDS.CartsInterf;
import it.unisa.zyphyksport.model.interfaceDS.ProductsInterf;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
