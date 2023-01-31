package it.unisa.zyphyksoprt.gestioneUtente.servlet;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Test;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO;
import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriCatalogoBean;
import it.unisa.zyphyksport.gestioneUtente.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriCatalogoInterf;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriOrdiniInterf;
import it.unisa.zyphyksport.gestioneUtente.servlet.SignUpServlet;

public class SignUpServletTest {

	@Test
	public void testDoPostSignUp() throws SQLException, ServletException, IOException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		PreparedStatement preparedStmt1 = Mockito.mock(PreparedStatement.class);
		ResultSet rs1 = Mockito.mock(ResultSet.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		SignUpServlet signUp = new SignUpServlet(){
			public ServletContext getServletContext() {
				return servletContext;
			}
			
		};
		
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		
		when(request.getParameter("nome")).thenReturn("Danilo");
		when(request.getParameter("cognome")).thenReturn("Piccolo");
		when(request.getParameter("username")).thenReturn("daniPiccolo");
		when(request.getParameter("data")).thenReturn("2001-06-12");
		when(request.getParameter("email")).thenReturn("danilopicc@gmail.com");
		when(request.getParameter("password")).thenReturn("Ciaoitalia!");
		when(request.getParameter("conferma")).thenReturn("Ciaoitalia!");
		
		ClientiDAO clDS = mock(ClientiDAO.class);
		GestoriCatalogoInterf gestCatDS = mock(GestoriCatalogoInterf.class);
		GestoriOrdiniInterf gestOrdDS = mock(GestoriOrdiniInterf.class);
		ClientiBean cliente = mock(ClientiBean.class);
		
		Set<ClientiBean> colClienti = (Set<ClientiBean>) mock(Set.class);
		Set<GestoriCatalogoBean> colGestCat = (Set<GestoriCatalogoBean>) mock(Set.class);
		Set<GestoriOrdiniBean> colGestOrd = (Set<GestoriOrdiniBean>) mock(Set.class);
		
		when(ds.getConnection()).thenReturn(conn);
		when(request.getSession()).thenReturn(session);
		
		when(clDS.doRetrieveAll(null)).thenReturn(colClienti);
		String checkSQL1 = "SELECT * FROM " + ClientiDAO.TABLE_NAME;
		when(conn.prepareStatement(checkSQL1)).thenReturn(preparedStmt1);
		when(preparedStmt1.executeQuery()).thenReturn(rs1);
		
		signUp.doPost(request, response);
		
		verify(request).getParameter("nome");
		verify(request).getParameter("cognome");
		verify(request).getParameter("username");
		verify(request).getParameter("data");
		verify(request).getParameter("email");
		verify(request).getParameter("password");
		verify(request).getParameter("conferma");
		assertEquals("Danilo", request.getParameter("nome"));
		assertEquals("Piccolo", request.getParameter("cognome"));
		assertEquals("daniPiccolo", request.getParameter("username"));
		assertEquals("2001-06-12", request.getParameter("data"));
		assertEquals("danilopicc@gmail.com", request.getParameter("email"));
		assertEquals("Ciaoitalia!", request.getParameter("password"));
		assertEquals("Ciaoitalia!", request.getParameter("conferma"));
		
		
		
	}
}
