package it.unisa.zyphyksoprt.gestioneVendite.servlet;

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

import it.unisa.zyphyksport.gestioneUtente.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.gestioneVendite.DAO.OrdersDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.OrdersBean;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.OrdersInterf;
import it.unisa.zyphyksport.gestioneVendite.servlet.OrderManageServlet;

public class OrderManageServletTest {
	@Test
	public void testDoGetOrderManage() throws ServletException, IOException, SQLException {
	
		// inizializzazione - mock
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		
		GestoriOrdiniBean gestOrdBean = Mockito.mock(GestoriOrdiniBean.class);
		OrdersBean ordBean = Mockito.mock(OrdersBean.class);			
		OrdersInterf ordersDAO = Mockito.mock(OrdersInterf.class);
		
		OrderManageServlet ordManage = new OrderManageServlet(){
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
		when(request.getParameter("id")).thenReturn("1");
		when(session.getAttribute("utente")).thenReturn(gestOrdBean);
		when(ordersDAO.doRetrieveByKey(1)).thenReturn(ordBean);
		
		when(gestOrdBean.getUsername()).thenReturn("LuBacco");
		
		// dao
		String checkSQL1 = "SELECT * FROM " + OrdersDAO.TABLE_NAME + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		String checkSQL2 = "UPDATE " + OrdersDAO.TABLE_NAME
				+ " SET SENT = TRUE, GEST_ORD_USERNAME = ?" + " WHERE ID = ?";
		when(conn.prepareStatement(checkSQL2)).thenReturn(preparedStmt2);
		when(preparedStmt2.executeQuery()).thenReturn(rs2);
		
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
		
		
		// test
		ordManage.doGet(request, response);
		
		// verify
		verify(response).sendRedirect("http://localhost/zyphyk-sport-web/orderManage.jsp");
	}
}
