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

import it.unisa.zyphyksport.gestioneVendite.DAO.OrdersContainsProdsDAO;
import it.unisa.zyphyksport.gestioneVendite.bean.OrdersContainsProdsBean;


public class OrdersContainsProdsDAOTest extends DataSourceBasedDBTestCase{

	private OrdersContainsProdsDAO ordersContainsProdsDAO;

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
        ordersContainsProdsDAO = new OrdersContainsProdsDAO(getDataSource());
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testDoRetrieveAllOrdersContainsProds() throws SQLException {
    	OrdersContainsProdsBean order1 = new OrdersContainsProdsBean(1, "ASD56", 1, 39, 85);
        OrdersContainsProdsBean order2 = new OrdersContainsProdsBean(1, "23AX1", 2, 37, 99);
        OrdersContainsProdsBean order3 = new OrdersContainsProdsBean(1, "XXC01", 1, 36, 74);
        OrdersContainsProdsBean order4 = new OrdersContainsProdsBean(2, "3ASD7", 3, 39, 82);
        OrdersContainsProdsBean order5 = new OrdersContainsProdsBean(3, "3ASD7", 1, 41, 45);
        OrdersContainsProdsBean order6 = new OrdersContainsProdsBean(3, "X2341", 2, 36, 65);
        OrdersContainsProdsBean order7 = new OrdersContainsProdsBean(3, "QEWER", 1, 38, 39);
        OrdersContainsProdsBean order8 = new OrdersContainsProdsBean(4, "111AQ", 4, 38, 100);
        OrdersContainsProdsBean order9 = new OrdersContainsProdsBean(4, "0ASDA", 1, 41, 74);
        OrdersContainsProdsBean order10 = new OrdersContainsProdsBean(5, "ASD56", 2, 40, 59);
        OrdersContainsProdsBean order11 = new OrdersContainsProdsBean(5, "23AX1", 1, 37, 69);
        OrdersContainsProdsBean order12 = new OrdersContainsProdsBean(5, "QRW20", 1, 39, 110);
        Set<OrdersContainsProdsBean> expectedOrders = new HashSet<>();
        expectedOrders.add(order1);
        expectedOrders.add(order2);
        expectedOrders.add(order3);
        expectedOrders.add(order4);
        expectedOrders.add(order5);
        expectedOrders.add(order6);
        expectedOrders.add(order7);
        expectedOrders.add(order8);
        expectedOrders.add(order9);
        expectedOrders.add(order10);
        expectedOrders.add(order11);
        expectedOrders.add(order12);

        Set<OrdersContainsProdsBean> actualOrders =  ordersContainsProdsDAO.doRetrieveAll(null);
        assertEquals(12, actualOrders.size());
        assertArrayEquals(expectedOrders.toArray(), actualOrders.toArray(), "Le tuple di OrdersContainsProdsDAO recuperate non sono identiche alle attese");
    }
    
    @Test
    public void testDoRetrieveAllProdsOrdersContainsProds() throws SQLException {
    	OrdersContainsProdsBean order1 = new OrdersContainsProdsBean(3, "3ASD7", 1, 41, 45);
        OrdersContainsProdsBean order2 = new OrdersContainsProdsBean(3, "X2341", 2, 36, 65);
        OrdersContainsProdsBean order3 = new OrdersContainsProdsBean(3, "QEWER", 1, 38, 39);
        Set<OrdersContainsProdsBean> expectedOrders = new HashSet<>();
        expectedOrders.add(order1);
        expectedOrders.add(order2);
        expectedOrders.add(order3);
        Set<OrdersContainsProdsBean> actualOrders =  ordersContainsProdsDAO.doRetrieveAllProds(3,null);
        assertEquals(3, actualOrders.size());
        assertArrayEquals(expectedOrders.toArray(), actualOrders.toArray(), "Le tuple di CartsContainsProdsDAOTest recuperate non sono identiche alle attese");
    }
    
    @Test
    public void testDoSaveOrdersContainsProds() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(OrdersContainsProdsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/OrdersContainsProdsDAOTest/testDoSaveOrdersContainsProds.xml"))
                 .getTable(OrdersContainsProdsDAO.TABLE_NAME);

         
         ordersContainsProdsDAO.doSave(5,"23AX1",2,38);
         ordersContainsProdsDAO.doSave(5,"QRW20",3,40);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(OrdersContainsProdsDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoDeleteOrdersContainsProds() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(OrdersContainsProdsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/OrdersContainsProdsDAOTest/testDoDeleteOrdersContainsProds.xml"))
                 .getTable(OrdersContainsProdsDAO.TABLE_NAME);

         
         ordersContainsProdsDAO.doDelete(5,"23AX1");
         ordersContainsProdsDAO.doDelete(3,"QEWER");

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(OrdersContainsProdsDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }

}
