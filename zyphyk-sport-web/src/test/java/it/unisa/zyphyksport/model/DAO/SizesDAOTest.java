package it.unisa.zyphyksport.model.DAO;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.Column;
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
import it.unisa.zyphyksport.model.bean.SizesBean;

public class SizesDAOTest extends DataSourceBasedDBTestCase{
	
	private SizesDAO sizesDAO;
	
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
	        sizesDAO = new SizesDAO(getDataSource());
	    }

	    @AfterEach
	    public void tearDown() throws Exception {
	        super.tearDown();
	    }
	    
	    
	    @Test
	    public void testDoRetrieveAllSizes() throws SQLException {
    	
	    	SizesBean size1 = new SizesBean(36, "23AX1",1);
	    	SizesBean size2 = new SizesBean(36, "3ASD7",2);
	    	SizesBean size3 = new SizesBean(36, "XXC01",3);
	    	SizesBean size24 = new SizesBean(36, "BFD32",4);	    	
	    	SizesBean size4 = new SizesBean(37, "ASD56",5);
	    	SizesBean size5 = new SizesBean(37, "23AX1",6);	    	
	    	SizesBean size6 = new SizesBean(38, "QEWER",7);	    	
	    	SizesBean size7 = new SizesBean(39, "23AX1",8);
	    	SizesBean size8 = new SizesBean(39, "3ASD7",9);	    	
	    	SizesBean size9 = new SizesBean(39, "X2341",10);	    	
	    	SizesBean size10 = new SizesBean(39, "ZZB35",11);	    	
	    	SizesBean size11 = new SizesBean(39, "QRW20",12);	    	
	    	SizesBean size12 = new SizesBean(40, "ASD56",13);		    	
	    	SizesBean size13 = new SizesBean(40, "23AX1",14);	    	
	    	SizesBean size14 = new SizesBean(40, "XXC01",15);	    	
	    	SizesBean size15 = new SizesBean(40, "111AQ",16);	    	
	    	SizesBean size16 = new SizesBean(40, "QEWER",17);	    	
	    	SizesBean size17 = new SizesBean(41, "ASD56",18);	    	
	    	SizesBean size18 = new SizesBean(41, "X2341",19);
	    	SizesBean size19 = new SizesBean(41, "ZZB35",20);
	    	SizesBean size20 = new SizesBean(41, "QRW20",21);		    	
	    	SizesBean size21 = new SizesBean(41, "QEWER",22);	    	
	    	SizesBean size22 = new SizesBean(42, "3ASD7",23);	    
	    	SizesBean size23 = new SizesBean(42, "X2341",24);
	    	
	    	Set<SizesBean> expectedSizes = new HashSet<>();
	    	expectedSizes.add(size1);
	    	expectedSizes.add(size2);
	    	expectedSizes.add(size3);
	    	expectedSizes.add(size24);
	    	expectedSizes.add(size4);
	    	expectedSizes.add(size5);
	    	expectedSizes.add(size6);
	    	expectedSizes.add(size7);
	    	expectedSizes.add(size8);
	    	expectedSizes.add(size9);
	    	expectedSizes.add(size10);
	    	expectedSizes.add(size11);
	    	expectedSizes.add(size12);
	    	expectedSizes.add(size13);
	    	expectedSizes.add(size14);
	    	expectedSizes.add(size15);
	    	expectedSizes.add(size16);
	    	expectedSizes.add(size17);
	    	expectedSizes.add(size18);
	    	expectedSizes.add(size19);
	    	expectedSizes.add(size20);
	    	expectedSizes.add(size21);
	    	expectedSizes.add(size22);
	    	expectedSizes.add(size23);
	    	
	        Set<SizesBean> actualSizes =  sizesDAO.doRetrieveAll(null);
	        assertEquals(24, actualSizes.size());
	        assertArrayEquals(expectedSizes.toArray(), actualSizes.toArray(), "Le tuple di SizesDAO recuperate non sono identiche alle attese"); 	
	    }
	    
	    
	    @Test
	    public void testDoSaveSizes() throws Exception{
	    	 ITable expectedTable = new FlatXmlDataSetBuilder()
	                 .build(SizesDAOTest.class.getClassLoader().getResourceAsStream("db/expected/SizesDAOTest/testDoSaveSizes.xml"))
	                 .getTable(SizesDAO.TABLE_NAME);
	    	 
	    	 sizesDAO.doSave("X2341", 40);
	    	 sizesDAO.doSave("3ASD7", 37);
	    	 

	         IDataSet databaseDataSet = getConnection().createDataSet();
	         ITable actualTable = databaseDataSet.getTable(SizesDAO.TABLE_NAME);
	         System.out.println(new SortedTable(expectedTable));
	         System.out.println(new SortedTable(actualTable));
	         String[] columnNames = {"count"};
	         Assertion.assertEquals(new SortedTable(expectedTable, columnNames), new SortedTable(actualTable, columnNames));
	    }
	    
	    
	    
	    @Test
	    public void testDoDeleteSizes() throws Exception{
	    	 ITable expectedTable = new FlatXmlDataSetBuilder()
	                 .build(SizesDAOTest.class.getClassLoader().getResourceAsStream("db/expected/SizesDAOTest/testDoDeleteSizes.xml"))
	                 .getTable(SizesDAO.TABLE_NAME);	    	
	    
	    	 sizesDAO.doDelete("X2341",42);
	    	 sizesDAO.doDelete("111AQ",40);
	    
	         IDataSet databaseDataSet = getConnection().createDataSet();
	         ITable actualTable = databaseDataSet.getTable(SizesDAO.TABLE_NAME);
	         String[] columnNames = {"count"};
	         Assertion.assertEquals(new SortedTable(expectedTable, columnNames), new SortedTable(actualTable, columnNames));
	    	    	 
	    } 
	    
	    
	    @Test
	    public void testDoRetrieveByProductIdSizes() throws SQLException {
	    	SizesBean size1 = new SizesBean(36, "23AX1",1); 	
	    	SizesBean size2 = new SizesBean(37, "23AX1",6);
	    	SizesBean size3 = new SizesBean(39, "23AX1",8);
	    	SizesBean size4 = new SizesBean(40, "23AX1",14);
	        Set<SizesBean> expectedSizes = new HashSet<>();
	        expectedSizes.add(size1);
	        expectedSizes.add(size2);
	        expectedSizes.add(size3);
	        expectedSizes.add(size4);
	        Set<SizesBean> actualSizes =  sizesDAO.doRetrieveByProductId("23AX1",null);
	        assertEquals(4, actualSizes.size());
	        assertArrayEquals(expectedSizes.toArray(), actualSizes.toArray(), "Le tuple di CartsContainsProdsDAO recuperate non sono identiche alle attese");
	    
	    }
   	
	    
	    
	    
}
