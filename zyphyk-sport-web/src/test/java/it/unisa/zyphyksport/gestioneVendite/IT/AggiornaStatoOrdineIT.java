package it.unisa.zyphyksport.gestioneVendite.IT;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

import it.unisa.zyphyksport.gestioneUtente.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.gestioneUtente.servlet.LoginServlet;
import it.unisa.zyphyksport.gestioneVendite.bean.OrdersBean;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.OrdersInterf;
import it.unisa.zyphyksport.gestioneVendite.servlet.OrderManageServlet;

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
	public void testAggiornaStatoOrdineIT() throws IOException {
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
				
				GestoriOrdiniBean gestOrdBean = Mockito.mock(GestoriOrdiniBean.class);
				OrdersBean ordBean = Mockito.mock(OrdersBean.class);			
				OrdersInterf ordersDAO = Mockito.mock(OrdersInterf.class);				
				//qui mettere i request rispettivi delle servlet testate
				when(request.getSession()).thenReturn(session);
				
				// context
				final ServletContext servletContext = Mockito.mock(ServletContext.class);
				OrderManageServlet ordManage = new OrderManageServlet(){
					public ServletContext getServletContext() {
						return servletContext;
					}
	
				};

				when(request.getSession()).thenReturn(session);	
				when(servletContext.getAttribute("DataSource")).thenReturn(ds);
				when(request.getParameter("id")).thenReturn("1");
				when(session.getAttribute("utente")).thenReturn(gestOrdBean);

		        // test
		        try {
		        	ordManage.doGet(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        //verify(response).sendRedirect("http://localhost/zyphyk-sport-web/orderManage.jsp");
		        verify(request).setAttribute("message", "true");
	
	}
}
