package it.unisa.zyphyksport.model.DAO;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;



import java.sql.SQLException;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unisa.zyphyksport.model.bean.CartsContainsProdsBean;

public class CartsContainsProdsDAOTest extends DataSourceBasedDBTestCase{

	 private CartsContainsProdsDAO cartsContainsProdsDAO;

	    @Override
	    protected DataSource getDataSource() {
	        JdbcDataSource dataSource = new JdbcDataSource();
	        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/database_Zyphyk-Sport-Test.sql'");
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
	        cartsContainsProdsDAO = new CartsContainsProdsDAO(getDataSource());
	    }

	    @AfterEach
	    public void tearDown() throws Exception {
	        super.tearDown();
	    }

	    @Test
	    public void testDoRetrieveAll() throws SQLException {
	    	CartsContainsProdsBean cart1 = new CartsContainsProdsBean(1, "ASD56", 1, 39);
	        CartsContainsProdsBean cart2 = new CartsContainsProdsBean(1, "23AX1", 2, 37);
	        CartsContainsProdsBean cart3 = new CartsContainsProdsBean(1, "ZZB35", 3, 36);
	        CartsContainsProdsBean cart4 = new CartsContainsProdsBean(1, "111AQ", 1, 39);
	        CartsContainsProdsBean cart5 = new CartsContainsProdsBean(2, "QEWER", 1, 41);
	        CartsContainsProdsBean cart6 = new CartsContainsProdsBean(3, "23AX1", 2, 36);
	        CartsContainsProdsBean cart7 = new CartsContainsProdsBean(3, "ASD56", 1, 39);
	        Set<CartsContainsProdsBean> expectedCarts = new HashSet<>();
	        expectedCarts.add(cart1);
	        expectedCarts.add(cart2);
	        expectedCarts.add(cart3);
	        expectedCarts.add(cart4);
	        expectedCarts.add(cart5);
	        expectedCarts.add(cart6);
	        expectedCarts.add(cart7);

	        Set<CartsContainsProdsBean> actualCarts =  cartsContainsProdsDAO.doRetrieveAll(null);
	        assertEquals(7, actualCarts.size());
	        assertArrayEquals(expectedCarts.toArray(), actualCarts.toArray(), "Le tuple di CartsContainsProdsDAO recuperate non sono identiche alle attese");
	    }

	    @Test
	    public void testDoSaveCartsContainsProds() throws Exception{
	    	 ITable expectedTable = new FlatXmlDataSetBuilder()
	                 .build(CartsContainsProdsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/CartsContainsProdsDAOTest/testDoSaveCartsContainsProds.xml"))
	                 .getTable(CartsContainsProdsDAO.TABLE_NAME);

	         
	         cartsContainsProdsDAO.doSave(4,"ZZB35",3,41);
	         cartsContainsProdsDAO.doSave(4,"ASD56",2,37);

	         IDataSet databaseDataSet = getConnection().createDataSet();
	         ITable actualTable = databaseDataSet.getTable(CartsContainsProdsDAO.TABLE_NAME);
	        
	         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	    }
	    @Test
	    public void testDoDeleteCartsContainsProds() throws Exception{
	    	 ITable expectedTable = new FlatXmlDataSetBuilder()
	                 .build(CartsContainsProdsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/CartsContainsProdsDAOTest/testDoDeleteCartsContainsProds.xml"))
	                 .getTable(CartsContainsProdsDAO.TABLE_NAME);

	         
	         cartsContainsProdsDAO.doDelete(3,"23AX1",36);
	         cartsContainsProdsDAO.doDelete(3,"ASD56",39);

	         IDataSet databaseDataSet = getConnection().createDataSet();
	         ITable actualTable = databaseDataSet.getTable(CartsContainsProdsDAO.TABLE_NAME);
	        
	         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	    }
	    
	    @Test
	    public void testDoUpdateCartsContainsProds() throws Exception{
	    	 ITable expectedTable = new FlatXmlDataSetBuilder()
	                 .build(CartsContainsProdsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/CartsContainsProdsDAOTest/testDoUpdateCartsContainsProds.xml"))
	                 .getTable(CartsContainsProdsDAO.TABLE_NAME);

	         
	         cartsContainsProdsDAO.doUpdate(1,"111AQ",4);
	         cartsContainsProdsDAO.doUpdate(3,"ASD56",5);

	         IDataSet databaseDataSet = getConnection().createDataSet();
	         ITable actualTable = databaseDataSet.getTable(CartsContainsProdsDAO.TABLE_NAME);
	        
	         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	    }
	    
	    @Test
	    public void testDoRetrieveAllByCartId() throws SQLException {
	    	CartsContainsProdsBean cart1 = new CartsContainsProdsBean(1, "ASD56", 1, 39);
	        CartsContainsProdsBean cart2 = new CartsContainsProdsBean(1, "23AX1", 2, 37);
	        CartsContainsProdsBean cart3 = new CartsContainsProdsBean(1, "ZZB35", 3, 36);
	        CartsContainsProdsBean cart4 = new CartsContainsProdsBean(1, "111AQ", 1, 39);
	        Set<CartsContainsProdsBean> expectedCarts = new HashSet<>();
	        expectedCarts.add(cart1);
	        expectedCarts.add(cart2);
	        expectedCarts.add(cart3);
	        expectedCarts.add(cart4);
	        Set<CartsContainsProdsBean> actualCarts =  cartsContainsProdsDAO.doRetrieveAllByCartId(1,null);
	        assertEquals(4, actualCarts.size());
	        assertArrayEquals(expectedCarts.toArray(), actualCarts.toArray(), "Le tuple di CartsContainsProdsDAOTest recuperate non sono identiche alle attese");
	    }
	    
	    
	    @Test
	    public void testDoRetrieveByKey() throws SQLException {
	    	CartsContainsProdsBean expectedCart = new CartsContainsProdsBean(1, "ASD56", 1, 39);

	        CartsContainsProdsBean actualCarts =  cartsContainsProdsDAO.doRetrieveByKey(1,"ASD56",39);
	        assertEquals(expectedCart, actualCarts);
	    }
	    
	    
	    
}
