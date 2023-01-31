package it.unisa.zyphyksport.gestioneVendite.IT;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.mockito.Mockito;

import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;
import it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO;
import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;
import it.unisa.zyphyksport.gestioneUtente.servlet.LoginServlet;
import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.DAO.OrdersDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;
import it.unisa.zyphyksport.gestioneVendite.bean.OrdersBean;
import it.unisa.zyphyksport.gestioneVendite.interfaceDS.OrdersInterf;
import it.unisa.zyphyksport.gestioneVendite.servlet.CheckOutServlet;

public class AcquistoIT extends DataSourceBasedDBTestCase{
	
	
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
	public void testAcquistoIT() throws Exception {
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
		ClientiBean cliente = new ClientiBean("daniPicci", 1, "Daniele", "Piccirillo", "dani_san@gmail.com", "Passw1234!", LocalDate.of(1996, 9, 6));
		CartsDAO carts = new CartsDAO(ds);
		CartsBean carrello = carts.doRetrieveByKey(cliente.getCartId());
		
		//qui mettere i request rispettivi delle servlet testate
		when(session.getAttribute("utente")).thenReturn(cliente);
		when(session.getAttribute("carrello")).thenReturn(carrello);
		
		when(request.getParameter("via")).thenReturn("Via Roma");
		when(request.getParameter("numCivico")).thenReturn("45");
		when(request.getParameter("città")).thenReturn("Pontecagnano");
		when(request.getParameter("provincia")).thenReturn("SA");
		when(request.getParameter("cc-number")).thenReturn("5333478291234785");
		when(request.getParameter("cc-expiration")).thenReturn("07-24");
		when(request.getParameter("cc-cvv")).thenReturn("735");	
		
		// context
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		CheckOutServlet checkout = new CheckOutServlet(){
			public ServletContext getServletContext() {
				return servletContext;
			}
			
			
			
		};
		when(request.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(request.getSession()).thenReturn(session);
		when(servletContext.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
		
		
		try {
			 checkout.doPost(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable(ClientiDAO.TABLE_NAME);
		
		OrdersInterf ordersDAO = new OrdersDAO(ds);
		Set<OrdersBean> actualOrders =  ordersDAO.doRetrieveAll(null);
			
		verify(response).sendRedirect("http://localhost/zyphyk-sport-web/thankYouPage.jsp");
		verify(session).setAttribute("orderId", 6);
		assertEquals(6, actualOrders.size());
	}
}
