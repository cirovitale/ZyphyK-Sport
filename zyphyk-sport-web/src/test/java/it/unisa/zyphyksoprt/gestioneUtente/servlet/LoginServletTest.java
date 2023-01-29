//package it.unisa.zyphyksoprt.gestioneUtente.servlet;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.servlet.GenericServlet;
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//
//import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;
//import it.unisa.zyphyksport.gestioneUtente.bean.GestoriCatalogoBean;
//import it.unisa.zyphyksport.gestioneUtente.bean.GestoriOrdiniBean;
//import it.unisa.zyphyksport.gestioneUtente.interfaceDS.ClientiInterf;
//import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriCatalogoInterf;
//import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriOrdiniInterf;
//import it.unisa.zyphyksport.gestioneUtente.servlet.LoginServlet;
//import it.unisa.zyphyksport.gestioneVendite.servlet.MainContext;
//
//public class LoginServletTest {
//	@Mock
//	private ServletContext servletContext;
//	private ServletConfig servletConfig;
//	
//	@Before
//	public void setUp() {
//		MainContext main = mock(MainContext.class);
//		ServletContextEvent servletContextEvent = mock(ServletContextEvent.class);
//		main.contextInitialized(servletContextEvent);
//		GenericServlet servlet = mock(GenericServlet.class);
//		servletConfig = mock(ServletConfig.class);
//		when(servlet.getServletConfig()).thenReturn(servletConfig);
//	}
//	
//	
//	@Test
//	public void testDoPost() throws Exception {
//		// Setup
//		ServletContext servletContext = mock(ServletContext.class);
//		DataSource ds = mock(DataSource.class);
//		when(servletConfig.getServletContext()).thenReturn(servletContext);
//		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		
//		ClientiInterf clDS = mock(ClientiInterf.class);
//		GestoriCatalogoInterf gestCatDS = mock(GestoriCatalogoInterf.class);
//		GestoriOrdiniInterf gestOrdDS = mock(GestoriOrdiniInterf.class);
//		Set<ClientiBean> colClienti = new HashSet<>();
//		ClientiBean cliente = new ClientiBean();
//		cliente.setUsername("username");
//		colClienti.add(cliente);
//		Set<GestoriCatalogoBean> colGestCat = new HashSet<>();
//		Set<GestoriOrdiniBean> colGestOrd = new HashSet<>();
//		
//		when(request.getParameter("username")).thenReturn("username");
//		when(request.getParameter("password")).thenReturn("password");
//
//		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
//		
//		when(clDS.doRetrieveAll(null)).thenReturn(colClienti);
//		when(gestCatDS.doRetrieveAll(null)).thenReturn(colGestCat);
//		when(gestOrdDS.doRetrieveAll(null)).thenReturn(colGestOrd);
//		
//		//when(checkLogin("username", "password")).thenReturn("ruolo");
//		
//		// Test
//		LoginServlet servlet = new LoginServlet();
//		servlet.doPost(request, response);
//
//		// Verify
//		verify(ds).getConnection();
//		verify(clDS, times(1)).doRetrieveAll(null);
//		verify(gestCatDS, times(1)).doRetrieveAll(null);
//		verify(gestOrdDS, times(1)).doRetrieveAll(null);
//	}
//}
