package it.unisa.zyphyksport.gestioneUtente.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.ClientiInterf;
import it.unisa.zyphyksport.gestioneUtente.servlet.LoginServlet;

public class AutenticazioneIT extends DataSourceBasedDBTestCase{
	
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/database_Zyphyk-Sport-Test.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
	}

	protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/init.xml"));
	}
	
	@Test
	public void testAutenticazioneIT() {
		// datasource
		DataSource ds = null;
		try {
			ds = getDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ClientiInterf clDAO = new ClientiDAO(ds);
		
		
		
		// mock
		//request e response
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		when(request.getParameter("username")).thenReturn("daniPicci");
		String pass_word = "Passw1234!";
		when(request.getParameter("password")).thenReturn(pass_word);
		
		// context
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		LoginServlet login = new LoginServlet(){
			public ServletContext getServletContext() {
				return servletContext;
			}
			
			
			
		};
		when(request.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(request.getSession()).thenReturn(session);
		when(servletContext.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);
		
        
        
        // test
        try {
			login.doPost(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        //verify(session).getAttribute("roles");
        assertEquals(session.getAttribute("roles"), "cliente");
        // verify
        //assertEquals(clBean.getPass_word(), encryptedPassword);
        
        // aggiungere anche controlli dei vari setAttribute: roles, utente, ecc.
        
	}
}
