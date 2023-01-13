package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.zyphyksport.model.DAO.CartsDAO;
import it.unisa.zyphyksport.model.DAO.ClientiDAO;
import it.unisa.zyphyksport.model.bean.CartsBean;
import it.unisa.zyphyksport.model.bean.ClientiBean;
import it.unisa.zyphyksport.model.interfaceDS.CartsInterf;
import it.unisa.zyphyksport.model.interfaceDS.ClientiInterf;

/**
 * Servlet implementation class SignUpServlet
 */
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String redirectedPage = null;
		
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String username = request.getParameter("username");
		String data = request.getParameter("data");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confPassword = request.getParameter("conferma");
		

		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ClientiInterf clDS = new ClientiDAO(ds);
		CartsInterf cartDS = new CartsDAO(ds);
		CartsBean cartBean = null;
		ClientiBean clBean = null;
		int cartId = 0;
		try {	
			if(confPassword.equals(password)) {
				cartId = cartDS.doSave(0);
				clDS.doSave(username, cartId, nome, cognome, email, password, LocalDate.parse(data));
				cartBean = cartDS.doRetrieveByKey(cartId);

				request.getSession().setAttribute("utente", clBean);
				request.getSession().setAttribute("roles", "cliente");
				request.getSession().setAttribute("carrello", cartBean);
				request.getSession().setAttribute("responseCart", false);
				
				redirectedPage = "/index.jsp";
			}
			
		} catch (Exception e) {
			request.getSession().setAttribute("roles", null);
			redirectedPage = "/signUp.jsp";
		}
		
		response.sendRedirect(request.getContextPath() + redirectedPage);
	}

}
