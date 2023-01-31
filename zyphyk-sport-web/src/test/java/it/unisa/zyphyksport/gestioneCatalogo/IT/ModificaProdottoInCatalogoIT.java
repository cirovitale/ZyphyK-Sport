package it.unisa.zyphyksport.gestioneCatalogo.IT;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
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

import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAO;
import it.unisa.zyphyksport.gestioneCatalogo.DAO.ProductsDAOTest;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf;
import it.unisa.zyphyksport.gestioneCatalogo.servlet.AddInCatalogServlet;
import it.unisa.zyphyksport.gestioneCatalogo.servlet.ModInCatalogServlet;
import it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO;
import it.unisa.zyphyksport.gestioneUtente.DAO.GestoriCatalogoDAO;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.ClientiInterf;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.GestoriCatalogoInterf;
import it.unisa.zyphyksport.gestioneUtente.servlet.LoginServlet;

public class ModificaProdottoInCatalogoIT extends DataSourceBasedDBTestCase{
	
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
	public void testModificaProdottoInCatalogoIT() throws Exception {
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
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		
		// context
		final ServletContext servletContext = Mockito.mock(ServletContext.class);
		ModInCatalogServlet modInCat = new ModInCatalogServlet() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		when(request.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("DataSource")).thenReturn(ds);
		when(request.getSession()).thenReturn(session);
		when(servletContext.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
				
		Part part = Mockito.mock(Part.class);
		
		
		//qui mettere i request rispettivi delle servlet testate
		GestoriCatalogoInterf gestCatDAO = new GestoriCatalogoDAO(ds);
		when(session.getAttribute("utente")).thenReturn(gestCatDAO.doRetrieveByKey("DarMoccia"));
		when(request.getPart("inputImage")).thenReturn(part);
		
		ProductsInterf prodsDAO = new ProductsDAO(ds);
		
		when(request.getParameter("sizesValue36")).thenReturn("36");
		when(request.getParameter("sizesValue37")).thenReturn("37");
		when(request.getParameter("sizesValue38")).thenReturn("38");
		when(request.getParameter("sizesValue39")).thenReturn("39");
		when(request.getParameter("sizesValue40")).thenReturn("40");
		when(request.getParameter("sizesValue41")).thenReturn("41");
		when(request.getParameter("sizesValue42")).thenReturn("42");
		when(request.getParameter("nomeProd")).thenReturn("Revolution X");
		when(request.getParameter("prodSelect")).thenReturn("0ASDA");
		when(request.getParameter("sport")).thenReturn("Calcio");
		when(request.getParameter("brand")).thenReturn("nike");
		when(request.getParameter("price")).thenReturn("49");
		
		modInCat.doPost(request, response);
		
		
		ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(ModificaProdottoInCatalogoIT.class.getClassLoader().getResourceAsStream("db/expected/IT/testModificaProdottoInCatalogoIT.xml"))
    			.getTable(ProductsDAO.TABLE_NAME);
		
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable(ProductsDAO.TABLE_NAME);

       	Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
		verify(response).sendRedirect("http://localhost/zyphyk-sport-web/products.jsp?id=0ASDA");
	}
}
