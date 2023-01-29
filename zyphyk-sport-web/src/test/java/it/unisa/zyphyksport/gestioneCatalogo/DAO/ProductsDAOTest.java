package it.unisa.zyphyksport.gestioneCatalogo.DAO;

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

import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;

public class ProductsDAOTest extends DataSourceBasedDBTestCase {

	private ProductsDAO productsDAO;
	
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
        productsDAO = new ProductsDAO(getDataSource());
    }	
	
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }	
	
    @Test
    public void testDoRetrieveAll() throws SQLException {
    	
    	ProductsBean product1 = new ProductsBean("ASD56", "Air Zoom Structure 24", "running", "nike", 59);
    	ProductsBean product2 = new ProductsBean("23AX1", "Courtjam Control", "tennis", "adidas", 60);
    	ProductsBean product3 = new ProductsBean("3ASD7", "Nike Air Zoom Vapor Pro", "tennis", "nike", 84);
    	ProductsBean product4 = new ProductsBean("X2341", "Ultraboost 20", "running", "adidas", 99);
    	ProductsBean product5 = new ProductsBean("ZZB35", "KING 21", "calcetto", "nike", 69);    	
    	ProductsBean product6 = new ProductsBean("QRW20", "Air Zoom Pegasus", "running", "adidas", 99);
    	ProductsBean product7 = new ProductsBean("XXC01", "Samba", "calcetto", "adidas", 115);
    	ProductsBean product8 = new ProductsBean("QEWER", "NikeCourt Zoom Pro", "tennis", "nike", 39);
    	ProductsBean product9 = new ProductsBean("BFD32", "Electrify Nitro 2", "running", "puma", 75);
    	ProductsBean product10 = new ProductsBean("111AQ", "Courtflash Speed", "tennis", "adidas", 90);    	
    	ProductsBean product11 = new ProductsBean("0ASDA", "BREAKSHOT 3 CC", "tennis", "mizuno", 79);    	
    	Set<ProductsBean> expectedProducts = new HashSet<>();
    	expectedProducts.add(product1);
    	expectedProducts.add(product2);
    	expectedProducts.add(product3);
    	expectedProducts.add(product4);
    	expectedProducts.add(product5);
    	expectedProducts.add(product6);
    	expectedProducts.add(product7);
    	expectedProducts.add(product8);
    	expectedProducts.add(product9);
    	expectedProducts.add(product10);
    	expectedProducts.add(product11);
    	
        Set<ProductsBean> actualProducts =  productsDAO.doRetrieveAll(null);
        assertEquals(11, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray(), "Le tuple di ProductsDAO recuperate non sono identiche alle attese");
    }
    
    @Test
    public void testDoSaveProducts() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(ProductsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/ProductsDAOTest/testDoSaveProducts.xml"))
    			.getTable(ProductsDAO.TABLE_NAME);
    	
    	productsDAO.doSave("7HJ8E", "Forza MotorSport A", "calcetto", "nike", 80);
    	productsDAO.doSave("66TTQ", "BREAKFast", "running", "adidas", 100);    	
    
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable(ProductsDAO.TABLE_NAME);

        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoDeleteProducts() throws Exception{
   	 ITable expectedTable = new FlatXmlDataSetBuilder()
             .build(ProductsDAO.class.getClassLoader().getResourceAsStream("db/expected/ProductsDAOTest/testDoDeleteProducts.xml"))
             .getTable(ProductsDAO.TABLE_NAME);
   	 
   	 productsDAO.doDelete("23AX1");
   	 productsDAO.doDelete("ZZB35");
     IDataSet databaseDataSet = getConnection().createDataSet();
     ITable actualTable = databaseDataSet.getTable(ProductsDAO.TABLE_NAME);
    
     Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));

    }
    
    @Test
    public void testDoUpdateProducts() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
    			.build(ProductsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/ProductsDAOTest/testDoUpdateProducts.xml"))
    			.getTable(ProductsDAO.TABLE_NAME);	

    	productsDAO.doUpdate("ASD56", "Zephyr", "running", "adidas", 64);
       	productsDAO.doUpdate("QRW20", "Zaffiro", "running", "adidas", 100);
       	
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable(ProductsDAO.TABLE_NAME);
       
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
            
    @Test
    public void testDoRetrieveByKey() throws SQLException {
    	ProductsBean expectedProd = new ProductsBean("ASD56", "Air Zoom Structure 24", "running", "nike", 59);

    	ProductsBean actualProds =  productsDAO.doRetrieveByKey("ASD56");
        assertEquals(expectedProd, actualProds);
    }
    
    
    //doRetrieveAllExists è uguale a doRetrieveAll
    @Test
    public void testDoRetrieveAllExists() throws SQLException {
    	
    	ProductsBean product1 = new ProductsBean("ASD56", "Air Zoom Structure 24", "running", "nike", 59);
    	ProductsBean product2 = new ProductsBean("23AX1", "Courtjam Control", "tennis", "adidas", 60);
    	ProductsBean product3 = new ProductsBean("3ASD7", "Nike Air Zoom Vapor Pro", "tennis", "nike", 84);
    	ProductsBean product4 = new ProductsBean("X2341", "Ultraboost 20", "running", "adidas", 99);
    	ProductsBean product5 = new ProductsBean("ZZB35", "KING 21", "calcetto", "nike", 69);    	
    	ProductsBean product6 = new ProductsBean("QRW20", "Air Zoom Pegasus", "running", "adidas", 99);
    	ProductsBean product7 = new ProductsBean("XXC01", "Samba", "calcetto", "adidas", 115);
    	ProductsBean product8 = new ProductsBean("QEWER", "NikeCourt Zoom Pro", "tennis", "nike", 39);
    	ProductsBean product9 = new ProductsBean("BFD32", "Electrify Nitro 2", "running", "puma", 75);
    	ProductsBean product10 = new ProductsBean("111AQ", "Courtflash Speed", "tennis", "adidas", 90);    	
    	ProductsBean product11 = new ProductsBean("0ASDA", "BREAKSHOT 3 CC", "tennis", "mizuno", 79);    	
    	Set<ProductsBean> expectedProducts = new HashSet<>();
    	expectedProducts.add(product1);
    	expectedProducts.add(product2);
    	expectedProducts.add(product3);
    	expectedProducts.add(product4);
    	expectedProducts.add(product5);
    	expectedProducts.add(product6);
    	expectedProducts.add(product7);
    	expectedProducts.add(product8);
    	expectedProducts.add(product9);
    	expectedProducts.add(product10);
    	expectedProducts.add(product11);
    	
        Set<ProductsBean> actualProducts =  productsDAO.doRetrieveAllExists(null);
        assertEquals(11, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray(), "Le tuple di ProductsDAO recuperate non sono identiche alle attese");    	
    }
    
    
    
    
}
