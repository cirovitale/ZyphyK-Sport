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

		String ruolo = (String) request.getSession().getAttribute("roles");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		int subtotale = 0;
		boolean flag = false;
		if(ruolo != null) {
			
			CartsInterf cartsDS = new CartsDAO(ds); 
			CartsBean carrello = (CartsBean) request.getSession().getAttribute("carrello");
			
			String id = request.getParameter("id");
			//System.out.println(id);
			int size = Integer.parseInt(request.getParameter("size"));
			
			//System.out.println(size);
			CartsContainsProdsInterf cartsContProdsDS = new CartsContainsProdsDAO(ds);	
		
			

			ProductsInterf productDS = new ProductsDAO(ds);
			ProductsBean productBean = null;
			try {
				productBean = productDS.doRetrieveByKey(id);
				//System.out.println(productBean);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<ProductsBean> colProd = (ArrayList<ProductsBean>) request.getSession().getAttribute("prodsCart");
			//System.out.println(colProd);
			List<CartsContainsProdsBean> colContainsProds = (ArrayList<CartsContainsProdsBean>) request.getSession().getAttribute("prodsContainsCart");
			//System.out.println(colContainsProds);
			if(size == 0) {
				flag = true;
				request.getSession().setAttribute("prodsCart", colProd);
				request.getSession().setAttribute("prodsContainsCart", colContainsProds);
				request.getSession().setAttribute("responseCart", false);
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			
			for(CartsContainsProdsBean cartContBean2 : colContainsProds) {
				if(cartContBean2.getProductId().equals(id) && cartContBean2.getSize() == size && flag == false) {
					System.out.println("prodotto con stessa taglia");
					flag = true;
					subtotale = carrello.getAmount() + productBean.getPrice();
					carrello.setAmount(subtotale);
					try {
						cartsDS.doUpdate(carrello.getId(), subtotale);
						cartContBean2.setQuantity(cartContBean2.getQuantity() + 1);
						cartsContProdsDS.doUpdate(carrello.getId(), productBean.getId(), cartContBean2.getQuantity());
						
						request.getSession().setAttribute("prodsCart", colProd);
						request.getSession().setAttribute("prodsContainsCart", colContainsProds);
						request.getSession().setAttribute("responseCart", false);
						response.sendRedirect(request.getContextPath() + "/cart.jsp");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(flag == false){
					System.out.println("nuovo prodotto");
					subtotale = carrello.getAmount() + productBean.getPrice();
					carrello.setAmount(subtotale);
					colProd.add(productBean);
					try {
						cartsDS.doUpdate(carrello.getId(), subtotale);
						cartsContProdsDS.doSave(carrello.getId(), productBean.getId(), 1, size);
						CartsContainsProdsBean cartContBean = cartsContProdsDS.doRetrieveByKey(carrello.getId(), productBean.getId(), size);
						colContainsProds.add(cartContBean);
						request.getSession().setAttribute("prodsCart", colProd);
						request.getSession().setAttribute("prodsContainsCart", colContainsProds);
						request.getSession().setAttribute("responseCart", false);
						response.sendRedirect(request.getContextPath() + "/cart.jsp");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		}
			
			
			/*
				
				if(!colProd.contains(productBean)) {
					subtotale = carrello.getAmount() + productBean.getPrice();
					carrello.setAmount(subtotale);
					colProd.add(productBean);
					System.out.println(colProd);
						
					try {
						cartsDS.doUpdate(carrello.getId(), subtotale);
						cartsContProdsDS.doSave(carrello.getId(), productBean.getId(), 1, size);
						cartContBean = cartsContProdsDS.doRetrieveByKey(carrello.getId(),productBean.getId(), size);
						System.out.println(cartContBean);
						colContainsProds.add(cartContBean);
						System.out.println(colContainsProds);
						
						
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					subtotale = carrello.getAmount() + productBean.getPrice();
					carrello.setAmount(subtotale);
					try {
						cartsDS.doUpdate(carrello.getId(), subtotale);
						cartContBean = cartsContProdsDS.doRetrieveByKey(carrello.getId(),productBean.getId(), size);
						cartsContProdsDS.doUpdate(carrello.getId(), productBean.getId(), cartContBean.getQuantity() + 1);
						cartContBean = cartsContProdsDS.doRetrieveByKey(carrello.getId(),productBean.getId(), size);
						System.out.println(cartContBean);
						colContainsProds.add(cartContBean);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			request.getSession().setAttribute("prodsCart", colProd);
			request.getSession().setAttribute("prodsContainsCart", colContainsProds);	
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
			
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
		*/
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
