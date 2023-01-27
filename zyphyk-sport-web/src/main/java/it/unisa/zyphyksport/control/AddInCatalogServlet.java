package it.unisa.zyphyksport.control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import it.unisa.zyphyksport.model.DAO.ManagesProdsDAO;
import it.unisa.zyphyksport.model.DAO.ProductsDAO;
import it.unisa.zyphyksport.model.DAO.SizesDAO;
import it.unisa.zyphyksport.model.bean.ClientiBean;
import it.unisa.zyphyksport.model.bean.GestoriCatalogoBean;
import it.unisa.zyphyksport.model.bean.ProductsBean;
import it.unisa.zyphyksport.model.interfaceDS.ManagesProdsInterf;
import it.unisa.zyphyksport.model.interfaceDS.ProductsInterf;
import it.unisa.zyphyksport.model.interfaceDS.SizesInterf;

/**
 * Servlet implementation class AddInCatServlet
 */
@WebServlet("/AddInCatalogServlet")
public class AddInCatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddInCatalogServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		GestoriCatalogoBean gestCat = (GestoriCatalogoBean) request.getSession().getAttribute("utente");
		
		String productId = null;

		String nome = request.getParameter("nomeProd");
		productId = request.getParameter("productId");
		String sport = request.getParameter("sport");
		String brand = request.getParameter("brand");
		int price = Integer.parseInt(request.getParameter("price"));

		Part part = request.getPart("inputImage");
        String fileName = productId+"_1.jpg";
        System.out.println(fileName);
        
        String path = getServletContext().getRealPath("/"+"img"+"/prod"+File.separator+fileName);
        System.out.println(path);
        part.write(path);

		Set<Integer> arlist = new HashSet<Integer>( );
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
/*
		Part part = request.getPart("inputImage");
		String fileName = codVid+"_1.jpg";
		System.out.println(fileName);

		String path = getServletContext().getRealPath("/"+"img"+"/videog"+File.separator+fileName);
		System.out.println(path);
		part.write(path);
*/
		
		ProductsInterf productsDAO = new ProductsDAO(ds);
		ManagesProdsInterf managesProdsDAO = new ManagesProdsDAO(ds);		
		SizesInterf sizesDAO = new SizesDAO(ds);

		boolean exist = false;
		Set<ProductsBean> colProducts = null;
		try {
			colProducts = productsDAO.doRetrieveAll(null);
			for(ProductsBean prodotto : colProducts) {
				String id = prodotto.getId();
				
				if(id.equals(productId)) {
					exist = true;
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		try {
			if(exist==false) {
				productsDAO.doSave(productId, nome, sport, brand, price);
				managesProdsDAO.doSave(gestCat.getUsername(), productId, 0);
				//sizesDAO.doSave(productId, sizesValue);
				for(int size: arlist){		
					sizesDAO.doSave(productId, size);	
				}					
			} else {
				String redirectedPage = "/addInCatalog.jsp";
				request.setAttribute("message", "true");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirectedPage);
				dispatcher.forward(request, response);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	response.sendRedirect(request.getContextPath() + "/products.jsp?id=" + productId);

      }
}
