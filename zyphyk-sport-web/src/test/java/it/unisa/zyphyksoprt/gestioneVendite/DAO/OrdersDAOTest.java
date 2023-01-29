package it.unisa.zyphyksoprt.gestioneVendite.DAO;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;
import it.unisa.zyphyksport.gestioneVendite.DAO.OrdersDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.OrdersBean;

public class OrdersDAOTest extends DataSourceBasedDBTestCase{

	private OrdersDAO ordersDAO;	
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/database_Zyphyk-Sport-Test.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");
		return dataSource;		
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/init.xml"));
	}
	
    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        getDataSet();
        ordersDAO = new OrdersDAO(getDataSource());
    }	
	
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
   
    
    @Test
    public void testDoRetrieveAllOrders() throws SQLException {
    	
    	OrdersBean orders1 = new OrdersBean(1, "daniPicci", "nonPresente", LocalDateTime.of(2022,11,23,12,45,0), "Via Roma 221 Salerno SA", "5333171120934758 09/27 432", 294);
    	OrdersBean orders2 = new OrdersBean(2, "marioRossi", "LuBacco", LocalDateTime.of(2023,5,11,18,23,0), "Via Casa Varone 113 Eboli SA", "5333171121903456 11/26 782", 252);
    	OrdersBean orders3 = new OrdersBean(3, "daniPicci", "LuBacco", LocalDateTime.of(2022,1,30,22,47,0), "Via Stabia 36 Pompei NA", "6734536271823456 12/25 934", 321);
    	OrdersBean orders4 = new OrdersBean(4, "peppeRoma", "nonPresente", LocalDateTime.of(2022,9,23,21,34,0), "Via De Goti 78 Caserta CE", "3324558912349076 04/26 278", 439);
    	OrdersBean orders5 = new OrdersBean(5, "angeloPorte", "nonPresente", LocalDateTime.of(2023,1,12,19,8,0), "Via Misano 221 Milano MI", "1234789467387462 06/25 475", 247);
    	Set<OrdersBean> expectedOrders = new HashSet<>();
    	expectedOrders.add(orders1);
    	expectedOrders.add(orders2);
    	expectedOrders.add(orders3);
    	expectedOrders.add(orders4);    	
    	expectedOrders.add(orders5);
    	
        Set<OrdersBean> actualOrders =  ordersDAO.doRetrieveAll(null);
        assertEquals(5, actualOrders.size());
        assertArrayEquals(expectedOrders.toArray(), actualOrders.toArray(), "Le tuple di OrdersDAO recuperate non sono identiche alle attese");
    }
    
    
    
    @Test
    public void testDoSaveOrders() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(OrdersDAOTest.class.getClassLoader().getResourceAsStream("db/expected/OrdersDAOTest/testDoSaveOrders.xml"))
    			.getTable(OrdersDAO.TABLE_NAME);
    
    	
    	ordersDAO.doSave("angeloPorte", null, LocalDateTime.of(2022,1,21,19,48,0), "Via Misano 200 Milano MI", "1234789467387462 06/25 475", 247, false);
    	
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable(OrdersDAO.TABLE_NAME);

        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    
    /*
    @Test
    public void testDoDeleteOrders() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(OrdersDAO.class.getClassLoader().getResourceAsStream("db/expected/OrdersDAOTest/testDoDeleteOrders.xml"))
    			.getTable(OrdersDAO.TABLE_NAME);
    	
    	ordersDAO.doDelete(5);
    	
    	IDataSet databaseDataSet = getConnection().createDataSet();
    	ITable actualTable = databaseDataSet.getTable(ordersDAO.TABLE_NAME);

    	Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));

    } 
    */
    
    /*
	@Test
	public void testDoRetrieveByKeyOrders() throws SQLException {
		OrdersBean expectedOrds = new OrdersBean(1, "daniPicci", "", LocalDateTime.of(2022,11,23,12,45,0), "Via Roma 221 Salerno SA", "5333171120934758 09/27 432", 294);
		
		OrdersBean actualOrds =  ordersDAO.doRetrieveByKey(1);
		assertEquals(expectedOrds, actualOrds);
	}
    */
    
	/*
    @Test
    public void testDoUpdateSentOrders() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(OrdersDAOTest.class.getClassLoader().getResourceAsStream("db/expected/ordersDAOTest/testDoUpdateOrders.xml"))
    			.getTable(ordersDAO.TABLE_NAME);	

    	ordersDAO.doUpdateSent(4, "LuBacco");
       	ordersDAO.doUpdateSent(5, "LuBacco");
       	
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable(ordersDAO.TABLE_NAME);
       
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoRetrieveAllCliente() throws Exception{
    	OrdersBean orders1 = new OrdersBean(1, "daniPicci", null, LocalDateTime.of(2022,11,23,12,45,0), "Via Roma 221 Salerno SA", "5333171120934758 09/27 432", 294);
    	OrdersBean orders2 = new OrdersBean(2, "marioRossi", "LuBacco", LocalDateTime.of(2023,5,11,18,23,0), "Via Casa Varone 113 Eboli SA", "5333171121903456 11/26 782", 252);
    	OrdersBean orders3 = new OrdersBean(3, "daniPicci", "LuBacco", LocalDateTime.of(2022,1,30,22,47,0), "Via Stabia 36 Pompei NA", "6734536271823456 12/25 934", 321);
    	OrdersBean orders4 = new OrdersBean(4, "peppeRoma", null, LocalDateTime.of(2022,9,23,21,34,0), "Via De Goti 78 Caserta CE", "3324558912349076 04/26 278", 439);
    	OrdersBean orders5 = new OrdersBean(5, "angeloPorte", null, LocalDateTime.of(2023,1,12,19,8,0), "Via Misano 221 Milano MI", "1234789467387462 06/25 475", 247);
    	Set<OrdersBean> expectedOrders = new HashSet<>();
    	expectedOrders.add(orders1);
    	expectedOrders.add(orders2);
    	expectedOrders.add(orders3);
    	expectedOrders.add(orders4);    	
    	expectedOrders.add(orders5);
    	
        Set<OrdersBean> actualOrders =  ordersDAO.doRetrieveAllCliente("daniPicci",null);
        assertEquals(5, actualOrders.size());
        assertArrayEquals(expectedOrders.toArray(), actualOrders.toArray(), "Le tuple di OrdersDAO recuperate non sono identiche alle attese");    	
    }
    
    */
}
