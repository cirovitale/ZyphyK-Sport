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
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Test;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.OrdersDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.gestioneVendite.servlet.CheckOutServlet;

public class CheckOutServletTest {

	@Test
	public void testDoPostCheckOut() throws SQLException, ServletException, IOException {
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
		ClientiBean clBean = Mockito.mock(ClientiBean.class);
		CartsBean cartBean = Mockito.mock(CartsBean.class);

		CartsContainsProdsDAO cartContProdsDAO = mock(CartsContainsProdsDAO.class);
		Set<CartsContainsProdsBean> setCarts = (Set<CartsContainsProdsBean>) mock(Set.class);
		
		CheckOutServlet checkOut = new CheckOutServlet(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public ServletContext getServletContext() {
				return servletContext;
			}
			
		};
		
		when(ds.getConnection()).thenReturn(conn);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("carrello")).thenReturn(cartBean);
		
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(session.getAttribute("utente")).thenReturn(clBean);
		
		when(request.getParameter("via")).thenReturn("Via Roma");
		when(request.getParameter("numCivico")).thenReturn("45");
		when(request.getParameter("città")).thenReturn("Pontecagnano");
		when(request.getParameter("provincia")).thenReturn("SA");
		when(request.getParameter("cc-number")).thenReturn("5333478291234785");
		when(request.getParameter("cc-expiration")).thenReturn("07-24");
		when(request.getParameter("cc-cvv")).thenReturn("735");	
		
		String checkSQL1 = "INSERT INTO " + OrdersDAO.TABLE_NAME
				+ " (CLIENTE_USERNAME, GEST_ORD_USERNAME, DATE_TIME, SHIPPING_ADDRESS, PAYMENT_METHOD, AMOUNT, SENT) VALUES (?, ?, ?, ?, ?, ?, ?)";
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		String checkSQL2 = "SELECT MAX(ID) AS MAX FROM " + OrdersDAO.TABLE_NAME; 
		when(conn.prepareStatement(checkSQL2)).thenReturn(preparedStmt2);
		when(preparedStmt2.executeQuery()).thenReturn(rs2);
		
		when(cartContProdsDAO.doRetrieveAllByCartId(3, null)).thenReturn(setCarts);
		String checkSQL3 = "SELECT * FROM " + CartsContainsProdsDAO.TABLE_NAME + " WHERE CART_ID = ?";
		when(conn.prepareStatement(checkSQL3)).thenReturn(preparedStmt3);
		when(preparedStmt3.executeQuery()).thenReturn(rs3);
		
		String checkSQL4 = "UPDATE " + CartsDAO.TABLE_NAME
				+ " SET AMOUNT = ?" + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL4)).thenReturn(preparedStmt4);
		when(preparedStmt3.executeQuery()).thenReturn(rs4);
		
		checkOut.doPost(request, response);
		
		
		verify(request).getParameter("via");
		verify(request).getParameter("numCivico");
		verify(request).getParameter("città");
		verify(request).getParameter("provincia");
		verify(request).getParameter("cc-number");
		verify(request).getParameter("cc-expiration");
		verify(request).getParameter("cc-cvv");
		verify(session).getAttribute("utente");
		assertEquals("Via Roma", request.getParameter("via"));
		assertEquals("45", request.getParameter("numCivico"));
		assertEquals("Pontecagnano", request.getParameter("città"));
		assertEquals("SA", request.getParameter("provincia"));
		assertEquals("5333478291234785", request.getParameter("cc-number"));
		assertEquals("07-24", request.getParameter("cc-expiration"));
		assertEquals("735", request.getParameter("cc-cvv"));

	}
}
