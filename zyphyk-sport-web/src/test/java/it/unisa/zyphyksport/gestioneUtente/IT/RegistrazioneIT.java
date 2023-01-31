package it.unisa.zyphyksport.gestioneUtente.IT;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO;
import it.unisa.zyphyksport.gestioneUtente.servlet.SignUpServlet;

public class RegistrazioneIT extends DataSourceBasedDBTestCase{
	
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
	public void testRegistrazioneIT() throws SQLException, Exception {
		// datasource
		DataSource ds = null;
		try {
			ds = getDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		// mock
		//request e response
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		//qui mettere i request rispettivi delle servlet testate
		SignUpServlet signUp = new SignUpServlet(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		
		
		when(request.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(request.getSession()).thenReturn(session);
		when(servletContext.getRequestDispatcher("/signUp.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
        
		// test
        try {
			signUp.doPost(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(RegistrazioneIT.class.getClassLoader().getResourceAsStream("db/expected/IT/testRegistrazioneIT.xml"))
    			.getTable(ClientiDAO.TABLE_NAME);
		
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable(ClientiDAO.TABLE_NAME);

       	Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
		verify(response).sendRedirect("http://localhost/zyphyk-sport-web/index.jsp");
        verify(session).setAttribute("roles", "cliente");
	}
}
