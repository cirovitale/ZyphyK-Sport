package it.unisa.zyphyksport.gestioneCatalogo.servlet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneCatalogo.DAO.ManagesProdsDAO;
import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriCatalogoBean;

public class RemFromCatalogServletTest {
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
		
		RemFromCatalogServlet remFromCat = new RemFromCatalogServlet() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		GestoriCatalogoBean gestCat = Mockito.mock(GestoriCatalogoBean.class);
		PreparedStatement preparedStmt1 = Mockito.mock(PreparedStatement.class);
		ResultSet rs1 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt2 = Mockito.mock(PreparedStatement.class);
		ResultSet rs2 = Mockito.mock(ResultSet.class);
		
		// when
		when(ds.getConnection()).thenReturn(conn);
		when(request.getSession()).thenReturn(session);	
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		
		when(session.getAttribute("utente")).thenReturn(gestCat);
		when(request.getParameter("productId")).thenReturn("23AX1");
		
		
		// dao
		String checkSQL1 = "UPDATE " + ProductsDAO.TABLE_NAME
				+ " SET REMOVED = TRUE" + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		
		String checkSQL2 = "INSERT INTO " + ManagesProdsDAO.TABLE_NAME
				+ " (GEST_CAT_USERNAME, PRODUCT_ID, TIPOLOGIA) VALUES (?, ?, ?)";
		when(conn.prepareStatement(checkSQL2)).thenReturn(preparedStmt2);
		when(preparedStmt2.executeQuery()).thenReturn(rs2);
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
		
		// test
		remFromCat.doPost(request, response);
		
		// verify
		verify(response).sendRedirect("http://localhost/zyphyk-sport-web/catalogManage.jsp");
	}
}
