package it.unisa.zyphyksport.gestioneCatalogo.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriCatalogoBean;

public class ModInCatalogServletTest {
	@Mock
	private ServletContext servletContext;
	
	@Test
	public void testDoPostModInCatalog() throws Exception {
		// inizializzazione - mock
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		ModInCatalogServlet modInCat = new ModInCatalogServlet() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		GestoriCatalogoBean gestCat = Mockito.mock(GestoriCatalogoBean.class);
		Part part = Mockito.mock(Part.class);
		
		PreparedStatement preparedStmt1 = Mockito.mock(PreparedStatement.class);
		ResultSet rs1 = Mockito.mock(ResultSet.class);
		
		// when
		when(ds.getConnection()).thenReturn(conn);
		when(request.getSession()).thenReturn(session);	
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(servletContext.getRequestDispatcher("/modInCatalog.jsp")).thenReturn(dispatcher);
		//when(servletContext.getRequestDispatcher("/modInCatalog.jsp?id=0ASDA")).thenReturn(dispatcher);

		
		when(session.getAttribute("utente")).thenReturn(gestCat);
		when(request.getPart("inputImage")).thenReturn(part);
		when(request.getParameter("sizesValue36")).thenReturn("36");
		when(request.getParameter("sizesValue37")).thenReturn("37");
		when(request.getParameter("sizesValue38")).thenReturn("38");
		when(request.getParameter("sizesValue39")).thenReturn("39");
		when(request.getParameter("sizesValue40")).thenReturn("40");
		when(request.getParameter("sizesValue41")).thenReturn("41");
		when(request.getParameter("sizesValue42")).thenReturn("42");
		when(request.getParameter("nomeProd")).thenReturn("Revolution X");
		when(request.getParameter("prodSelect")).thenReturn("0ASDA");
		when(request.getParameter("sport")).thenReturn("Calcio");
		when(request.getParameter("brand")).thenReturn("nike");
		when(request.getParameter("price")).thenReturn("49");
		
		when(servletContext.getRealPath("/"+"img"+"/prod"+File.separator+"POP12")).thenReturn("/img/prod/POP12");
		
		// dao
		String checkSQL1 = "SELECT * FROM " + ProductsDAO.TABLE_NAME + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
		
		// test
		modInCat.doPost(request, response);
		
		// verify
		assertEquals("0ASDA", request.getParameter("prodSelect"));
		assertEquals("Revolution X", request.getParameter("nomeProd"));
		assertEquals("Calcio", request.getParameter("sport"));
		assertEquals("nike", request.getParameter("brand"));
		assertEquals("49", request.getParameter("price"));
		assertEquals("36", request.getParameter("sizesValue36"));
		assertEquals("37", request.getParameter("sizesValue37"));
		assertEquals("38", request.getParameter("sizesValue38"));
		assertEquals("39", request.getParameter("sizesValue39"));
		assertEquals("40", request.getParameter("sizesValue40"));
		assertEquals("41", request.getParameter("sizesValue41"));
		assertEquals("42", request.getParameter("sizesValue42"));
		//verify(response).sendRedirect("http://localhost/zyphyk-sport-web/products.jsp?id=0ASDA");
	}
}
