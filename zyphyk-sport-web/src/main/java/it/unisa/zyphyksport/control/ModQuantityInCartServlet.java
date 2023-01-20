package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet implementation class ModQuantityInCartServlet
 */
@WebServlet("/ModQuantityInCartServlet")
public class ModQuantityInCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModQuantityInCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ruolo = (String) request.getSession().getAttribute("roles");

		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		if(ruolo.equals("cliente")) {
			CartsBean carrello = (CartsBean) request.getSession().getAttribute("carrello");
			String productId = request.getParameter("id");
			int offset = Integer.parseInt(request.getParameter("offset"));
			int size = Integer.parseInt(request.getParameter("size"));
			int cartId = carrello.getId();
	
			CartsContainsProdsInterf cartContsProdDAO = new CartsContainsProdsDAO(ds);
			ProductsInterf productDAO = new ProductsDAO(ds);
			CartsInterf cartDAO = new CartsDAO(ds);
			Set<ProductsBean> prodsArray = (Set<ProductsBean>) request.getSession().getAttribute("prodsCart");
			Set<CartsContainsProdsBean> cartContainsProdsArray = (Set<CartsContainsProdsBean>) request.getSession().getAttribute("prodsContainsCart");
			
			try {
				CartsContainsProdsBean cartContMod = cartContsProdDAO.doRetrieveByKey(cartId, productId, size);
				ProductsBean prodMod = productDAO.doRetrieveByKey(productId);
				
				
				for(CartsContainsProdsBean cartContProd: cartContainsProdsArray) {
					if(cartContProd.getProductId().equals(productId) && cartContProd.getSize() == size) {
						if(cartContProd.getQuantity() == 1 && offset == -1) {
							// non rimuove quantità altrimenti sarà pari a 0
						} else {
							cartContProd.setQuantity(cartContProd.getQuantity() + offset);
							cartContsProdDAO.doUpdate(cartId, productId, cartContMod.getQuantity() + offset);
							cartDAO.doUpdate(cartId, ((int)carrello.getAmount() + (int)prodMod.getPrice()  * offset));
							carrello.setAmount(carrello.getAmount() + prodMod.getPrice() * offset);
						}
					}
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.getSession().setAttribute("prodsContainsCart", cartContainsProdsArray);
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
