package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.security.MessageDigest;

import it.unisa.zyphyksport.model.bean.ProductsBean;
import it.unisa.zyphyksport.model.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.model.DAO.CartsDAO;
import it.unisa.zyphyksport.model.DAO.ClientiDAO;
import it.unisa.zyphyksport.model.DAO.GestoriCatalogoDAO;
import it.unisa.zyphyksport.model.DAO.GestoriOrdiniDAO;
import it.unisa.zyphyksport.model.DAO.ProductsDAO;
import it.unisa.zyphyksport.model.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.model.bean.ClientiBean;
import it.unisa.zyphyksport.model.bean.GestoriCatalogoBean;
import it.unisa.zyphyksport.model.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.model.interfaceDS.CartsContainsProdsInterf;
import it.unisa.zyphyksport.model.interfaceDS.CartsInterf;
import it.unisa.zyphyksport.model.interfaceDS.ClientiInterf;
import it.unisa.zyphyksport.model.interfaceDS.GestoriCatalogoInterf;
import it.unisa.zyphyksport.model.interfaceDS.GestoriOrdiniInterf;
import it.unisa.zyphyksport.model.interfaceDS.ProductsInterf;

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
		String redirectedPage;
		String ruolo = null;
		try {
			ruolo = checkLogin(username, password);
			ClientiBean clBean = null;
			GestoriCatalogoBean catBean = null;
			GestoriOrdiniBean ordBean = null;
			
			if(ruolo.equals("cliente")) {
				ClientiInterf clDS = new ClientiDAO(ds);
				clBean = clDS.doRetrieveByKey(username);
				request.getSession().setAttribute("utente", clBean);
				CartsInterf cart = new CartsDAO(ds);
				CartsContainsProdsInterf cartContsProdDAO = new CartsContainsProdsDAO(ds);
				ProductsInterf productDAO = new ProductsDAO(ds);
				request.getSession().setAttribute("carrello", cart.doRetrieveByKey(clBean.getCartId()));
				
				
				Set<ProductsBean> prodsArray = new HashSet<ProductsBean>();
				Set<CartsContainsProdsBean> prodsContainsCartArray = new HashSet<CartsContainsProdsBean>();
				try {
					Set<CartsContainsProdsBean> cartContsProdArr = cartContsProdDAO.doRetrieveAllByCartId(clBean.getCartId(), null);
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
				request.getSession().setAttribute("responseCart", false);
				
			} else if(ruolo.equals("gestCat")) {
				GestoriCatalogoInterf catDS = new GestoriCatalogoDAO(ds);
				catBean = catDS.doRetrieveByKey(username);
				request.getSession().setAttribute("utente", catBean);
			} else if(ruolo.equals("gestOrd")) {
				GestoriOrdiniInterf ordDS = new GestoriOrdiniDAO(ds);
				ordBean = ordDS.doRetrieveByKey(username);
				request.getSession().setAttribute("utente", ordBean);
			}
			
			
			request.getSession().setAttribute("roles", ruolo);
			redirectedPage = "/index.jsp";
			
		} catch (Exception e) {
			request.getSession().setAttribute("roles", null);
			redirectedPage = "/login.jsp";
		}
		response.sendRedirect(request.getContextPath() + redirectedPage);
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
			throw new Exception("Invalid login and password");
		}
				
	}

}
