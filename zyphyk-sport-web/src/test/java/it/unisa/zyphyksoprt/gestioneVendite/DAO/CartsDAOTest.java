package it.unisa.zyphyksoprt.gestioneVendite.DAO;

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

import it.unisa.zyphyksport.gestioneVendite.DAO.CartsDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;

public class CartsDAOTest extends DataSourceBasedDBTestCase{
	private CartsDAO cartsDAO;
	
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
        cartsDAO = new CartsDAO(getDataSource());
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testDoSaveCarts() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(CartsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/CartsDAOTest/testDoSaveCarts.xml"))
    			.getTable(CartsDAO.TABLE_NAME);

         cartsDAO.doSave(151);
         cartsDAO.doSave(48);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(CartsDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoUpdateCarts() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(CartsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/CartsDAOTest/testDoUpdateCarts.xml"))
                 .getTable(CartsDAO.TABLE_NAME);

         
         cartsDAO.doUpdate(1, 430);
         cartsDAO.doUpdate(3, 120);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(CartsDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoDeleteCarts() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(CartsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/CartsDAOTest/testDoDeleteCarts.xml"))
                 .getTable(CartsDAO.TABLE_NAME);

         
         cartsDAO.doDelete(2);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(CartsDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoRetrieveByKeyCarts() throws SQLException {
    	CartsBean expectedCart = new CartsBean(1, 386);

        CartsBean actualCarts =  cartsDAO.doRetrieveByKey(1);
        assertEquals(expectedCart, actualCarts);
    }
    
    @Test
    public void testDoRetrieveAllCarts() throws SQLException {
    	CartsBean cart1 = new CartsBean(1, 386);
    	CartsBean cart2 = new CartsBean(2, 39);
    	CartsBean cart3 = new CartsBean(3, 179);
    	CartsBean cart4 = new CartsBean(4, 0);
        Set<CartsBean> expectedCarts = new HashSet<>();
        expectedCarts.add(cart1);
        expectedCarts.add(cart2);
        expectedCarts.add(cart3);
        expectedCarts.add(cart4);

        Set<CartsBean> actualCarts =  cartsDAO.doRetrieveAll(null);
        
        assertEquals(4, actualCarts.size());
        assertArrayEquals(expectedCarts.toArray(), actualCarts.toArray(), "Le tuple di CartsContainsProdsDAO recuperate non sono identiche alle attese");
    }
    
}
