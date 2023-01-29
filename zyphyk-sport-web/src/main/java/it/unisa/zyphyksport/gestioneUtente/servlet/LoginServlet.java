package it.unisa.zyphyksport.gestioneUtente.servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
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
import it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO;
import it.unisa.zyphyksport.gestioneUtente.DAO.GestoriCatalogoDAO;
import it.unisa.zyphyksport.gestioneUtente.DAO.GestoriOrdiniDAO;
import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriCatalogoBean;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.ClientiInterf;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriCatalogoInterf;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriOrdiniInterf;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.CartsContainsProdsInterf;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.CartsInterf;

import java.security.MessageDigest;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ClientiInterf clDS = new ClientiDAO(ds);
		GestoriCatalogoInterf gestCatDS = new GestoriCatalogoDAO(ds);
		GestoriOrdiniInterf gestOrdDS = new GestoriOrdiniDAO(ds);
		String redirectedPage = null;
		String ruolo = null;
		try {
			ruolo = checkLogin(username, password);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		boolean exist = false;		
		
		//controllo se l'username del cliente è nel database
		Set<ClientiBean> colClienti = null;
		try {
			colClienti = clDS.doRetrieveAll(null);
			for(ClientiBean cliente : colClienti) {
				String usernameServlet = cliente.getUsername().toLowerCase();
				if(usernameServlet.equals(username.toLowerCase()) ) {
					if(ruolo!=null) {						
						exist = true;
					}
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//controllo se l'username del gestore catalogo è nel database
		Set<GestoriCatalogoBean> colGestCat = null;
		try {
			colGestCat = gestCatDS.doRetrieveAll(null);
			for(GestoriCatalogoBean gestoreCat : colGestCat) {
				String usernameServlet = gestoreCat.getUsername().toLowerCase();
				
				if(usernameServlet.equals(username.toLowerCase()) ) {					
					if(ruolo!=null) {						
						exist = true;
					}
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//controllo se l'username del gestore ordine è nel database
		Set<GestoriOrdiniBean> colGestOrd = null;
		try {
			colGestOrd = gestOrdDS.doRetrieveAll(null);
			for(GestoriOrdiniBean gestoreOrd : colGestOrd) {
				String usernameServlet = gestoreOrd.getUsername().toLowerCase();
				
				if(usernameServlet.equals(username.toLowerCase()) ) {					
					if(ruolo!=null) {
						exist = true;						
					}
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			ruolo = checkLogin(username, password);
			ClientiBean clBean = null;
			GestoriCatalogoBean catBean = null;
			GestoriOrdiniBean ordBean = null;
			
			if(ruolo.equals("cliente") && exist == true) {
				int subtotale = 0;
				
				clBean = clDS.doRetrieveByKey(username);
				request.getSession().setAttribute("utente", clBean);
				CartsInterf cart = new CartsDAO(ds);
				CartsContainsProdsInterf cartContsProdDAO = new CartsContainsProdsDAO(ds);
				ProductsInterf productDAO = new ProductsDAO(ds);
				SizesInterf sizes = new SizesDAO(ds); 
				CartsBean cartBean = cart.doRetrieveByKey(clBean.getCartId());
				//request.getSession().setAttribute("carrello", cartBean);
				
				
				List<ProductsBean> prodsArray = new ArrayList<ProductsBean>();
				List<CartsContainsProdsBean> prodsContainsCartArray = new ArrayList<CartsContainsProdsBean>();
				try {
					Set<CartsContainsProdsBean> cartContsProdArr = cartContsProdDAO.doRetrieveAllByCartId(clBean.getCartId(), null);
					for(CartsContainsProdsBean cartContProd: cartContsProdArr) {
						ProductsBean product = productDAO.doRetrieveByKey(cartContProd.getProductId());
						if(product.isRemoved()) {
							cartContsProdDAO.doDelete(clBean.getCartId(), cartContProd.getProductId(), cartContProd.getSize());
							subtotale = cartBean.getAmount();
							cart.doUpdate(clBean.getCartId(), subtotale - (product.getPrice() * cartContProd.getQuantity()));
							cartBean = cart.doRetrieveByKey(clBean.getCartId());
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
				request.getSession().setAttribute("carrello", cartBean);
				request.getSession().setAttribute("prodsCart", prodsArray);
				request.getSession().setAttribute("prodsContainsCart", prodsContainsCartArray);
				request.getSession().setAttribute("responseCart", false);
				
			} else if(ruolo.equals("gestCat")  && exist == true) {
				GestoriCatalogoInterf catDS = new GestoriCatalogoDAO(ds);
				catBean = catDS.doRetrieveByKey(username);
				request.getSession().setAttribute("utente", catBean);
			} else if(ruolo.equals("gestOrd")  && exist == true) {
				GestoriOrdiniInterf ordDS = new GestoriOrdiniDAO(ds);
				ordBean = ordDS.doRetrieveByKey(username);
				request.getSession().setAttribute("utente", ordBean);
				
			} else if(ruolo.equals(null)) {	//se la password non corrisponde
				exist=false;
				request.setAttribute("message", "true");
				redirectedPage = "/index.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirectedPage);
				dispatcher.forward(request, response);
				}
			
			request.getSession().setAttribute("roles", ruolo);
			redirectedPage = "/index.jsp";
			
		} catch (Exception e) {
			request.getSession().setAttribute("roles", null);
			redirectedPage = "/login.jsp";
		}
		if(exist == false) {
			request.setAttribute("message", "true");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirectedPage);
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + redirectedPage);
		}		
		
	}

	private String checkLogin(String username, String password) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStm1 = null;
		PreparedStatement preparedStm2 = null;
		PreparedStatement preparedStm3 = null;

		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		String checkSQL1 = "SELECT PASS_WORD, ROLES FROM CLIENTI"
				+ " WHERE USERNAME = ?";
		
		String checkSQL2 = "SELECT PASS_WORD, ROLES FROM GESTORI_CATALOGO"
				+ " WHERE USERNAME = ?";
		
		String checkSQL3 = "SELECT PASS_WORD, ROLES FROM GESTORI_ORDINI"
				+ " WHERE USERNAME = ?";
		
		
		String ruolo1 = null;
		String ruolo2 = null;
		String ruolo3 = null;

		
		try {
			connection = ds.getConnection();
			
			MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(password.getBytes());
		    byte[] digest = md.digest();
		    String passwordMD5 = String.format("%032x", new BigInteger(1, digest));
			
			preparedStm1 = connection.prepareStatement(checkSQL1);
			preparedStm1.setString(1, username);
			ResultSet rs1 = preparedStm1.executeQuery();
			String pass_word1 = null;
			if(rs1.next()) {
				pass_word1 = rs1.getString("pass_word");
				if (pass_word1.equals(passwordMD5))				
					ruolo1 = rs1.getString("roles");
			}	
			
			preparedStm2 = connection.prepareStatement(checkSQL2);
			preparedStm2.setString(1, username);
			ResultSet rs2 = preparedStm2.executeQuery();
			String pass_word2 = null;
			if(rs2.next()) {
				pass_word2 = rs2.getString("pass_word");
				if (pass_word2.equals(passwordMD5))				
					ruolo2 =  rs2.getString("roles");
			}
			
			preparedStm3 = connection.prepareStatement(checkSQL3);
			preparedStm3.setString(1, username);
			ResultSet rs3 = preparedStm3.executeQuery();
			String pass_word3 = null;
			if(rs3.next()) {
				pass_word3 = rs3.getString("pass_word");
				if (pass_word3.equals(passwordMD5))				
					ruolo3 =  rs3.getString("roles");
			}
			
						
			connection.setAutoCommit(false);
			connection.commit();
		} finally {
			try {
				if (preparedStm1 != null)
					preparedStm1.close();
				if (preparedStm2 != null)
					preparedStm2.close();
				if (preparedStm3 != null)
					preparedStm3.close();
				
			} finally {
				if (connection != null)
					connection.close();
			}
		}
				
		if (ruolo1 != null) {
			return ruolo1;
		} else if (ruolo2 != null){
			return ruolo2;
		} else if (ruolo3 != null){
			return ruolo3;
		}
		else {
			//throw new Exception("Invalid login and password");
			return null; //se la pw non corrisponde
		}
				
	}

}
