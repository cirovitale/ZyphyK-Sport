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
import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;

import it.unisa.zyphyksport.gestioneVendite.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.gestioneVendite.servlet.AddToCartServlet;

public class AddToCartServletTest {

	@Test
	public void testDoGetAddToCart() throws ServletException, IOException, SQLException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		PreparedStatement preparedStmt1 = Mockito.mock(PreparedStatement.class);
		ResultSet rs1 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt2 = Mockito.mock(PreparedStatement.class);
		ResultSet rs2 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt3 = Mockito.mock(PreparedStatement.class);
		ResultSet rs3 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt4 = Mockito.mock(PreparedStatement.class);
		ResultSet rs4 = Mockito.mock(ResultSet.class);
		
		
		ProductsDAO prods = mock(ProductsDAO.class);
		ProductsBean prodBean = mock(ProductsBean.class);
		CartsDAO carts = mock(CartsDAO.class);
		CartsBean cartBean = Mockito.mock(CartsBean.class);
		CartsContainsProdsBean contProds = mock(CartsContainsProdsBean.class);
		CartsContainsProdsDAO cartContProds = mock(CartsContainsProdsDAO.class);
		List<ProductsBean> colProd = (List<ProductsBean>) mock(ArrayList.class);
		List<CartsContainsProdsBean> colContainsProds = (List<CartsContainsProdsBean>) mock(ArrayList.class); 
		Iterator iterator = Mockito.mock(Iterator.class);
		
		AddToCartServlet addToCart = new AddToCartServlet(){
			public ServletContext getServletContext() {
				return servletContext;
			}
			
		};
		
		when(request.getParameter("id")).thenReturn("0");
		when(request.getParameter("size")).thenReturn("46");
		
		when(ds.getConnection()).thenReturn(conn);
		when(request.getSession()).thenReturn(session);
		
		
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(session.getAttribute("roles")).thenReturn("cliente");
		
		when(prods.doRetrieveByKey(null)).thenReturn(prodBean);
		String checkSQL1 = "SELECT * FROM " + ProductsDAO.TABLE_NAME + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		when(session.getAttribute("prodsContainsCart")).thenReturn(colContainsProds);
		when(colContainsProds.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(false);
		
		when(session.getAttribute("carrello")).thenReturn(cartBean);
		
		when(session.getAttribute("prodsCart")).thenReturn(colProd);
		when(colContainsProds.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(false);
		
		String checkSQL2 = "UPDATE " + CartsDAO.TABLE_NAME
				+ " SET AMOUNT = ?" + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL2)).thenReturn(preparedStmt2);
		when(preparedStmt2.executeQuery()).thenReturn(rs2);
		
		String checkSQL3 = "INSERT INTO " + CartsContainsProdsDAO.TABLE_NAME
				+ "(CART_ID, PRODUCT_ID, QUANTITY, SIZE) VALUES (?, ?, ?, ?)";
		when(conn.prepareStatement(checkSQL3)).thenReturn(preparedStmt3);
		when(preparedStmt3.executeQuery()).thenReturn(rs3);
		
		when(cartContProds.doRetrieveByKey(0,null,0)).thenReturn(contProds);
		String checkSQL4 = "SELECT * FROM " + CartsContainsProdsDAO.TABLE_NAME + " WHERE CART_ID = ? AND PRODUCT_ID = ? AND SIZE = ?";
		when(conn.prepareStatement(checkSQL4)).thenReturn(preparedStmt4);
		when(preparedStmt4.executeQuery()).thenReturn(rs4);
		
		addToCart.doGet(request, response);
		
		verify(request).getParameter("id");
		verify(request).getParameter("size");
		verify(session).getAttribute("roles");
		assertEquals("0", request.getParameter("id"));
		assertEquals("46", request.getParameter("size"));
		assertEquals("cliente", session.getAttribute("roles"));
		
		
		
		
	}
}
