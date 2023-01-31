package it.unisa.zyphyksoprt.gestioneVendite.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.gestioneVendite.servlet.ModQuantityInCartServlet;

public class ModQuantityInCartServletTest {
	@Test
	public void testDoGetModQuantityInCart() throws ServletException, IOException, SQLException {
		// inizializzazione - mock
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		
		CartsBean cartBean = Mockito.mock(CartsBean.class);
		CartsContainsProdsDAO ordersDS = Mockito.mock(CartsContainsProdsDAO.class);
		List<CartsContainsProdsBean> colContainsProds = (List<CartsContainsProdsBean>) mock(ArrayList.class); 
		Iterator iterator = Mockito.mock(Iterator.class);
		
		ModQuantityInCartServlet modQuantInCart = new ModQuantityInCartServlet(){
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
		
		// when
		when(ds.getConnection()).thenReturn(conn);
		when(request.getSession()).thenReturn(session);	
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(session.getAttribute("roles")).thenReturn("cliente");
		when(session.getAttribute("carrello")).thenReturn(cartBean);
		when(request.getParameter("offset")).thenReturn("-1");	
		when(request.getParameter("size")).thenReturn("37");	
		when(session.getAttribute("prodsContainsCart")).thenReturn(colContainsProds);
		when(colContainsProds.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(false);
		
		// dao
		String checkSQL1 = "SELECT * FROM " + CartsContainsProdsDAO.TABLE_NAME + " WHERE CART_ID = ? AND PRODUCT_ID = ? AND SIZE = ?";
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		String checkSQL2 = "SELECT * FROM " + ProductsDAO.TABLE_NAME + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL2)).thenReturn(preparedStmt2);
		when(preparedStmt2.executeQuery()).thenReturn(rs2);
		
		
		// test
		modQuantInCart.doGet(request, response);
		
		// verify
		verify(request).getParameter("offset");
		verify(request).getParameter("size");
		verify(session).getAttribute("roles");
		assertEquals("-1", request.getParameter("offset"));
		assertEquals("37", request.getParameter("size"));
		assertEquals("cliente", session.getAttribute("roles"));
	}
}
