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

import it.unisa.zyphyksport.gestioneCatalogo.bean.ManagesProdsBean;

public class ManagesProdsDAOTest extends DataSourceBasedDBTestCase{

	private ManagesProdsDAO managesProdsDAO;
	
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
        managesProdsDAO = new ManagesProdsDAO(getDataSource());
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
	
    @Test
    public void testDoRetrieveAllManagesProds() throws SQLException {
    	ManagesProdsBean manProds1 = new ManagesProdsBean(1,"DarMoccia","ASD56", 0);
    	ManagesProdsBean manProds2 = new ManagesProdsBean(2,"DarMoccia","3ASD7", 0);
    	ManagesProdsBean manProds3 = new ManagesProdsBean(3,"DarMoccia","ZZB35", 0);
    	ManagesProdsBean manProds4 = new ManagesProdsBean(4,"DarMoccia","QRW20", 0);
    	ManagesProdsBean manProds5 = new ManagesProdsBean(5,"DarMoccia","XXC01", 0);
    	ManagesProdsBean manProds6 = new ManagesProdsBean(6,"DarMoccia","0ASDA", 0);
    	ManagesProdsBean manProds7 = new ManagesProdsBean(7,"MasVarriale","23AX1", 0);
    	ManagesProdsBean manProds8 = new ManagesProdsBean(8,"MasVarriale","X2341", 0);
    	ManagesProdsBean manProds9 = new ManagesProdsBean(9,"MasVarriale","BFD32", 0);
    	ManagesProdsBean manProds10 = new ManagesProdsBean(10,"MasVarriale","111AQ", 0);
        Set<ManagesProdsBean> expectedmanProds = new HashSet<>();
        expectedmanProds.add(manProds1);
        expectedmanProds.add(manProds2);
        expectedmanProds.add(manProds3);
        expectedmanProds.add(manProds4);
        expectedmanProds.add(manProds5);
        expectedmanProds.add(manProds6);
        expectedmanProds.add(manProds7);
        expectedmanProds.add(manProds8);
        expectedmanProds.add(manProds9);
        expectedmanProds.add(manProds10);

        Set<ManagesProdsBean> actualmanProds =  managesProdsDAO.doRetrieveAll(null);
        assertEquals(10, actualmanProds.size());
        assertArrayEquals(expectedmanProds.toArray(), actualmanProds.toArray(), "Le tuple di ManagesProdsDAO recuperate non sono identiche alle attese");
    }
    

    @Test
    public void testDoRetrieveByKeyManagesProds() throws SQLException {
    	ManagesProdsBean manProds1 = new ManagesProdsBean(1,"DarMoccia","ASD56", 0);

        ManagesProdsBean actualmanProds =  managesProdsDAO.doRetrieveByKey(1);
        assertEquals(manProds1, actualmanProds);
    }
    
    @Test
    public void testDoSaveManagesProds() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(ManagesProdsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/ManagesProdsDAOTest/testDoSaveManagesProds.xml"))
                 .getTable(ManagesProdsDAO.TABLE_NAME);

         
    	 managesProdsDAO.doSave("MasVarriale","0ASDA", 0);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(ManagesProdsDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    
    @Test
    public void testDoDeleteManagesProds() throws Exception{
   	 ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ManagesProdsDAOTest.class.getClassLoader().getResourceAsStream("db/expected/ManagesProdsDAOTest/testDoDeleteManagesProds.xml"))
                .getTable(ManagesProdsDAO.TABLE_NAME);

        
   	 	managesProdsDAO.doDelete(1);

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable(ManagesProdsDAO.TABLE_NAME);
       
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
   }
}
