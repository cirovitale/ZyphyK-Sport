package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.zyphyksport.model.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.model.DAO.ProductsDAO;
import it.unisa.zyphyksport.model.bean.CartsBean;
import it.unisa.zyphyksport.model.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.model.bean.ProductsBean;
import it.unisa.zyphyksport.model.interfaceDS.CartsContainsProdsInterf;
import it.unisa.zyphyksport.model.interfaceDS.ProductsInterf;

/**
 * Servlet implementation class GetProdsFromCart
 */
@WebServlet("/GetProdsFromCartServlet")
public class GetProdsFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProdsFromCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruolo = (String) request.getSession().getAttribute("roles");
		
		
		if(ruolo.equals("cliente")) {
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			CartsContainsProdsInterf cartContsProdDAO = new CartsContainsProdsDAO(ds);
			ProductsInterf productDAO = new ProductsDAO(ds);
			Collection<ProductsBean> prodsArray = new ArrayList<ProductsBean>();
			Collection<CartsContainsProdsBean> prodsContainsCartArray = new ArrayList<CartsContainsProdsBean>();
	
			
			CartsBean carrello = (CartsBean) request.getSession().getAttribute("carrello");
			int cartId = carrello.getId();
			
			try {
				Collection<CartsContainsProdsBean> cartContsProdArr = cartContsProdDAO.doRetrieveAllByCartId(cartId, null);
				for(CartsContainsProdsBean cartContProd: cartContsProdArr) {
					ProductsBean product = productDAO.doRetrieveByKey(cartContProd.getProductId());
					
					prodsArray.add(product);
					prodsContainsCartArray.add(cartContProd);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().setAttribute("prodsCart", prodsArray);
			request.getSession().setAttribute("prodsContainsCart", prodsContainsCartArray);
		}
		
		
		request.getSession().setAttribute("responseCart", true);
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}
}
