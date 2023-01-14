package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

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
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartServlet() {
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
		String ruolo = (String) request.getSession().getAttribute("roles");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		int subtotale = 0;
		int quantity = 1;
		if(ruolo != null) {
			
			CartsInterf cartsDS = new CartsDAO(ds); 
			CartsBean carrello = (CartsBean) request.getSession().getAttribute("carrello");
			String id = request.getParameter("id");
			System.out.println(id);
			int size = Integer.parseInt(request.getParameter("size"));
			System.out.println(size);
			CartsContainsProdsInterf cartsContProdsDS = new CartsContainsProdsDAO(ds);	
			

			ProductsInterf productDS = new ProductsDAO(ds);
			ProductsBean productBean = null;
			CartsContainsProdsBean cartContBean = null;
			try {
				productBean = productDS.doRetrieveByKey(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Collection<ProductsBean> colProd = (Collection<ProductsBean>) request.getSession().getAttribute("prodsCart");
			System.out.println(colProd);
			Collection<CartsContainsProdsBean> colContainsProds = (Collection<CartsContainsProdsBean>) request.getSession().getAttribute("prodsContainsCart");
			System.out.println(colContainsProds);
			
			if(!colProd.contains(productBean)) {
				subtotale = carrello.getAmount() + productBean.getPrice();
				carrello.setAmount(subtotale);
				colProd.add(productBean);
					
				try {
					cartsDS.doUpdate(carrello.getId(), subtotale);
					cartsContProdsDS.doSave(carrello.getId(), productBean.getId(), quantity, size);
					cartContBean = cartsContProdsDS.doRetrieveByKey(carrello.getId(),productBean.getId(), size);
					colContainsProds.add(cartContBean);
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				subtotale = carrello.getAmount() + productBean.getPrice();
				carrello.setAmount(subtotale);
				try {
					cartsDS.doUpdate(carrello.getId(), subtotale);
					cartsContProdsDS.doUpdate(carrello.getId(), productBean.getId(), quantity + 1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
				
		
			
			
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		}else {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
		
	}
}

