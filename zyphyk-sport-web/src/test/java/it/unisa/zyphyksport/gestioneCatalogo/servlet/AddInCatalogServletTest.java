package it.unisa.zyphyksport.gestioneCatalogo.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneCatalogo.DAO.ManagesProdsDAO;
import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO;
import it.unisa.zyphyksport.gestioneCatalogo.DAO.SizesDAO;
import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriCatalogoBean;

public class AddInCatalogServletTest {
	@Mock
	private ServletContext servletContext;
	
	@Test
	public void testDoPostAddInCatalog() throws Exception {
		// inizializzazione - mock
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		
		AddInCatalogServlet addInCat = new AddInCatalogServlet() {
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
		Set<ProductsBean> colProducts = (Set<ProductsBean>) mock(Set.class);
		
		ProductsDAO prodsDS = Mockito.mock(ProductsDAO.class);
		ProductsInterf productsDAO = Mockito.mock(ProductsInterf.class);
		
		
		PreparedStatement preparedStmt1 = Mockito.mock(PreparedStatement.class);
		ResultSet rs1 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt2 = Mockito.mock(PreparedStatement.class);
		ResultSet rs2 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt3 = Mockito.mock(PreparedStatement.class);
		ResultSet rs3 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt4 = Mockito.mock(PreparedStatement.class);
		ResultSet rs4 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt5 = Mockito.mock(PreparedStatement.class);
		ResultSet rs5 = Mockito.mock(ResultSet.class);
		
		// when
		when(ds.getConnection()).thenReturn(conn);
		when(request.getSession()).thenReturn(session);	
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		
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
		when(request.getParameter("productId")).thenReturn("POP12");
		when(request.getParameter("sport")).thenReturn("Calcio");
		when(request.getParameter("brand")).thenReturn("nike");
		when(request.getParameter("price")).thenReturn("49");
		
		when(servletContext.getRealPath("/"+"img"+"/prod"+File.separator+"POP12")).thenReturn("/img/prod/POP12");
		
		// dao
		when(productsDAO.doRetrieveAll(null)).thenReturn(colProducts);
		String checkSQL1 = "SELECT * FROM " + ProductsDAO.TABLE_NAME;
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		
		String checkSQL2 = "INSERT INTO " + ProductsDAO.TABLE_NAME
				+ " (ID, NAME, SPORT, BRAND, PRICE) VALUES (?, ?, ?, ?, ?)";
		when(conn.prepareStatement(checkSQL2)).thenReturn(preparedStmt2);
		when(preparedStmt2.executeQuery()).thenReturn(rs2);
		
		String checkSQL3 = "INSERT INTO " + ManagesProdsDAO.TABLE_NAME
				+ " (GEST_CAT_USERNAME, PRODUCT_ID, TIPOLOGIA) VALUES (?, ?, ?)";
		when(conn.prepareStatement(checkSQL3)).thenReturn(preparedStmt3);
		when(preparedStmt3.executeQuery()).thenReturn(rs3);
		
		String checkSQL4 = "INSERT INTO " + SizesDAO.TABLE_NAME
				+ " (PRODUCT_ID, VALUED) VALUES (?, ?)";
		when(conn.prepareStatement(checkSQL4)).thenReturn(preparedStmt4);
		when(preparedStmt4.executeQuery()).thenReturn(rs4);
		
		String checkSQL5 = "SELECT MAX(COUNT) AS MAX FROM " + SizesDAO.TABLE_NAME;
		when(conn.prepareStatement(checkSQL5)).thenReturn(preparedStmt5);
		when(preparedStmt5.executeQuery()).thenReturn(rs5);
		
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
		
		// test
		addInCat.doPost(request, response);
		
		// verify
		assertEquals("POP12", request.getParameter("productId"));
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
		verify(response).sendRedirect("http://localhost/zyphyk-sport-web/products.jsp?id=POP12");
	}
}
