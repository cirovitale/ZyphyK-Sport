package it.unisa.zyphyksport.gestioneVendite.IT;

import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

import it.unisa.zyphyksport.gestioneUtente.servlet.LoginServlet;

public class AggiornaStatoOrdineIT extends DataSourceBasedDBTestCase{
	
	
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
	public void testAggiornaStatoOrdineIT() {
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
				HttpSession session = Mockito.mock(HttpSession.class);
				RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
				//qui mettere i request rispettivi delle servlet testate
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
				when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
	}
}
