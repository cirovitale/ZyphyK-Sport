package it.unisa.zyphyksoprt.gestioneUtente.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO;
import it.unisa.zyphyksport.gestioneUtente.DAO.GestoriCatalogoDAO;
import it.unisa.zyphyksport.gestioneUtente.DAO.GestoriOrdiniDAO;
import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriCatalogoBean;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.ClientiInterf;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriCatalogoInterf;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriOrdiniInterf;
import it.unisa.zyphyksport.gestioneUtente.servlet.LoginServlet;
import it.unisa.zyphyksport.utils.MainContext;


public class LoginServletTest {
	@Mock
	private ServletContext servletContext;
	private ServletConfig servletConfig;
	
	@Before
	public void setUp() {
		
	}
	
	
	@Test
	public void testDoPost() throws Exception {
		// Setup
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
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
		PreparedStatement preparedStmt5 = Mockito.mock(PreparedStatement.class);
		ResultSet rs5 = Mockito.mock(ResultSet.class);
		PreparedStatement preparedStmt6 = Mockito.mock(PreparedStatement.class);
		ResultSet rs6 = Mockito.mock(ResultSet.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		LoginServlet login = new LoginServlet(){
			public ServletContext getServletContext() {
				return servletContext;
			}
			
		};
		
		
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		
		
		
		GestoriCatalogoInterf gestCatDS = mock(GestoriCatalogoInterf.class);
		GestoriOrdiniInterf gestOrdDS = mock(GestoriOrdiniInterf.class);
		ClientiDAO clDS = mock(ClientiDAO.class);
		ClientiBean cliente = mock(ClientiBean.class);
		Set<ClientiBean> colClienti = (Set<ClientiBean>) mock(Set.class);
		Set<GestoriCatalogoBean> colGestCat = (Set<GestoriCatalogoBean>) mock(Set.class);
		Set<GestoriOrdiniBean> colGestOrd = (Set<GestoriOrdiniBean>) mock(Set.class);
		
		when(request.getParameter("username")).thenReturn("daniPicci");
		when(request.getParameter("password")).thenReturn("password");

		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(ds.getConnection()).thenReturn(conn);
		
		
		when(request.getSession()).thenReturn(session);
		when(servletContext.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);
		
		
		
		
		
		String checkSQL1 = "SELECT PASS_WORD, ROLES FROM CLIENTI"
				+ " WHERE USERNAME = ?";
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		String checkSQL2 = "SELECT PASS_WORD, ROLES FROM GESTORI_CATALOGO"
				+ " WHERE USERNAME = ?";
		when(conn.prepareStatement(checkSQL2)).thenReturn(preparedStmt2);
		when(preparedStmt2.executeQuery()).thenReturn(rs2);
		String checkSQL3 = "SELECT PASS_WORD, ROLES FROM GESTORI_ORDINI"
				+ " WHERE USERNAME = ?";
		when(conn.prepareStatement(checkSQL3)).thenReturn(preparedStmt3);
		when(preparedStmt3.executeQuery()).thenReturn(rs3);
		
		when(clDS.doRetrieveAll(null)).thenReturn(colClienti);
		String checkSQL4 = "SELECT * FROM " + ClientiDAO.TABLE_NAME;
		when(conn.prepareStatement(checkSQL4)).thenReturn(preparedStmt4);
		when(preparedStmt4.executeQuery()).thenReturn(rs4);
		
		when(gestCatDS.doRetrieveAll(null)).thenReturn(colGestCat);
		String checkSQL5 = "SELECT * FROM " + GestoriCatalogoDAO.TABLE_NAME;
		when(conn.prepareStatement(checkSQL5)).thenReturn(preparedStmt5);
		when(preparedStmt5.executeQuery()).thenReturn(rs5);
		
		when(gestOrdDS.doRetrieveAll(null)).thenReturn(colGestOrd);
		String checkSQL6 = "SELECT * FROM " + GestoriOrdiniDAO.TABLE_NAME;
		when(conn.prepareStatement(checkSQL6)).thenReturn(preparedStmt6);
		when(preparedStmt6.executeQuery()).thenReturn(rs6);
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
		//when(checkLogin("username", "password")).thenReturn("ruolo");
		
		// Test
		
		login.doPost(request, response);

		// Verify
		
		verify(request).getParameter("username");
		assertEquals("daniPicci", request.getParameter("username"));
		//verify(session).setAttribute("roles", ruolo);
	}
}
