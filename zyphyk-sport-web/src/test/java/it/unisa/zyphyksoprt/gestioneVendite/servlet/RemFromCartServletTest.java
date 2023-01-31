package it.unisa.zyphyksoprt.gestioneVendite.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Test;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.servlet.RemFromCartServlet;

public class RemFromCartServletTest {
	@Test
	public void testDoGetRemFromCart() throws ServletException, IOException, SQLException {
		// inizializzazione - mock
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		
		CartsBean cartBean = Mockito.mock(CartsBean.class);
		
		RemFromCartServlet remFromCart = new RemFromCartServlet(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
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
		when(session.getAttribute("roles")).thenReturn("cliente");
		when(session.getAttribute("carrello")).thenReturn(cartBean);
		when(request.getParameter("id")).thenReturn("ZZB35");
		when(request.getParameter("size")).thenReturn("37");
		
		// dao
		String checkSQL1 = "SELECT * FROM " + CartsContainsProdsDAO.TABLE_NAME + " WHERE CART_ID = ? AND PRODUCT_ID = ? AND SIZE = ?";
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		String checkSQL2 = "SELECT * FROM " + ProductsDAO.TABLE_NAME + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL2)).thenReturn(preparedStmt2);
		when(preparedStmt2.executeQuery()).thenReturn(rs2);
		
		String checkSQL3 = "UPDATE " + CartsDAO.TABLE_NAME
				+ " SET AMOUNT = ?" + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL3)).thenReturn(preparedStmt3);
		when(preparedStmt3.executeQuery()).thenReturn(rs3);
		
		String checkSQL4 = "DELETE FROM " + CartsContainsProdsDAO.TABLE_NAME + " WHERE CART_ID = ? AND PRODUCT_ID = ? AND SIZE = ?";
		when(conn.prepareStatement(checkSQL4)).thenReturn(preparedStmt4);
		when(preparedStmt4.executeQuery()).thenReturn(rs4);
		
		String checkSQL5 = "SELECT * FROM " + CartsContainsProdsDAO.TABLE_NAME + " WHERE CART_ID = ?";
		when(conn.prepareStatement(checkSQL5)).thenReturn(preparedStmt5);
		when(preparedStmt5.executeQuery()).thenReturn(rs5);
		
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
		
		// test
		remFromCart.doGet(request, response);
		
		// verify
		assertEquals("cliente", session.getAttribute("roles"));
		assertEquals("ZZB35", request.getParameter("id"));
		assertEquals("37", request.getParameter("size"));
		verify(response).sendRedirect("http://localhost/zyphyk-sport-web/cart.jsp");
	}
}
