package it.unisa.zyphyksport.gestioneVendite.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO;
import it.unisa.zyphyksport.gestioneCatalogo.DAO.SizesDAO;
import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.SizesInterf;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.CartsContainsProdsInterf;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.CartsInterf;

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
		int subtotale = 0;
		
		if(ruolo.equals("cliente")) {
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			CartsContainsProdsInterf cartContsProdDAO = new CartsContainsProdsDAO(ds);
			ProductsInterf productDAO = new ProductsDAO(ds);
			CartsInterf cartsDAO = new CartsDAO(ds);
			SizesInterf sizes = new SizesDAO(ds);
			List<ProductsBean> prodsArray = new ArrayList<ProductsBean>();
			List<CartsContainsProdsBean> prodsContainsCartArray = new ArrayList<CartsContainsProdsBean>();
	
			
			CartsBean carrello = (CartsBean) request.getSession().getAttribute("carrello");
			int cartId = carrello.getId();
			
			try {
				Set<CartsContainsProdsBean> cartContsProdArr = cartContsProdDAO.doRetrieveAllByCartId(cartId, null);
				for(CartsContainsProdsBean cartContProd: cartContsProdArr) {
					ProductsBean product = productDAO.doRetrieveByKey(cartContProd.getProductId());
					/*
					if(product.isRemoved()) {
						cartContsProdDAO.doDelete(cartId, cartContProd.getProductId(), cartContProd.getSize());
					*/
					if(product.isRemoved()) {
						cartContsProdDAO.doDelete(cartId, cartContProd.getProductId(), cartContProd.getSize());
						subtotale = carrello.getAmount();
						cartsDAO.doUpdate(cartId, subtotale - (product.getPrice() * cartContProd.getQuantity()));
						carrello = cartsDAO.doRetrieveByKey(cartId);
						sizes.doDelete(product.getId(), cartContProd.getSize());
					}else {
						prodsArray.add(product);
						prodsContainsCartArray.add(cartContProd);
					}
					
					
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().setAttribute("carrello", carrello);
			request.getSession().setAttribute("prodsCart", prodsArray);
			request.getSession().setAttribute("prodsContainsCart", prodsContainsCartArray);
		}
		
		
		request.getSession().setAttribute("responseCart", true);
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}
}
