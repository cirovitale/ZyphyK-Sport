package it.unisa.zyphyksport.gestioneCatalogo.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.zyphyksport.gestioneCatalogo.DAO.ManagesProdsDAO;
import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO;
import it.unisa.zyphyksport.gestioneCatalogo.DAO.SizesDAO;
import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ManagesProdsInterf;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.SizesInterf;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriCatalogoBean;

/**
 * Servlet implementation class ModInCatServlet
 */
@WebServlet("/ModInCatalogServlet")
public class ModInCatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModInCatalogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		GestoriCatalogoBean gestCat = (GestoriCatalogoBean) request.getSession().getAttribute("utente");
		ProductsInterf productsDAO = new ProductsDAO(ds);
		
		
		String productId = request.getParameter("prodSelect");
		ProductsBean prodBean = null;
		try {
			prodBean =  productsDAO.doRetrieveByKey(productId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(productId == null || (prodBean.getId() == null || prodBean.isRemoved())) {
			String redirectedPage = "/modInCatalog.jsp";
			request.setAttribute("message", "true");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirectedPage);
			dispatcher.forward(request, response);
		} else {
			String nome = request.getParameter("nomeProd");
			
			System.out.println(productId);
			String sport = request.getParameter("sport");
			String brand = request.getParameter("brand");
			int price = Integer.parseInt(request.getParameter("price"));

			ArrayList<Integer> arlist = new ArrayList<Integer>( );
			
			if (request.getParameter("sizesValue36")!=null) {
				int sizesValue36 = Integer.parseInt(request.getParameter("sizesValue36"));
				arlist.add(sizesValue36);	
			}
			if (request.getParameter("sizesValue37")!=null) {
				int sizesValue37 = Integer.parseInt(request.getParameter("sizesValue37"));
				arlist.add(sizesValue37);
			}
			if (request.getParameter("sizesValue38")!=null) {
				int sizesValue38 = Integer.parseInt(request.getParameter("sizesValue38"));
				arlist.add(sizesValue38);
			}
			if (request.getParameter("sizesValue39")!=null) {
				int sizesValue39 = Integer.parseInt(request.getParameter("sizesValue39"));
				arlist.add(sizesValue39);
			}		
			if (request.getParameter("sizesValue40")!=null) {
				int sizesValue40 = Integer.parseInt(request.getParameter("sizesValue40"));
				arlist.add(sizesValue40);
			}
			if (request.getParameter("sizesValue41")!=null) {
				int sizesValue41 = Integer.parseInt(request.getParameter("sizesValue41"));
				arlist.add(sizesValue41);
			}
			if (request.getParameter("sizesValue42")!=null) {
				int sizesValue42 = Integer.parseInt(request.getParameter("sizesValue42"));
				arlist.add(sizesValue42);
			}
			
			
			ManagesProdsInterf managesProdsDAO = new ManagesProdsDAO(ds);
			
			SizesInterf sizesDAO = new SizesDAO(ds); //dobbiamo eliminare le taglie che non sono state scelte
			
			
			try {
				productsDAO.doUpdate(productId, nome, sport, brand, price);
				managesProdsDAO.doSave(gestCat.getUsername(), productId, 2);
				
				for(int i = 36; i<43; i++) {
					sizesDAO.doDelete(productId, i);
				}
				
				if (arlist.size() == 0) {
					productsDAO.doDelete(productId);
				} else {
					for(int size: arlist){
						sizesDAO.doSave(productId, size);
					}
				}
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.sendRedirect(request.getContextPath() + "/products.jsp?id=" + productId);
		}
      }

}
