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

import it.unisa.zyphyksport.model.bean.GestoriOrdiniBean;


public class GestoriOrdiniDAOTest extends DataSourceBasedDBTestCase{

	private GestoriOrdiniDAO gestoriOrdiniDAO;

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
        gestoriOrdiniDAO = new GestoriOrdiniDAO(getDataSource());
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    
    
    @Test
    public void testDoRetrieveAllGestioniOrdini() throws SQLException {
    	GestoriOrdiniBean gestOrd1 = new GestoriOrdiniBean("LuBacco","Luigi","Bacco","lbgames@gmail.com","dd6c2b03adfe02e7ef9b1fd016581415",1700);
        Set<GestoriOrdiniBean> expectedgestOrd = new HashSet<>();
        expectedgestOrd.add(gestOrd1);

        Set<GestoriOrdiniBean> actualOrders =  gestoriOrdiniDAO.doRetrieveAll(null);
        assertEquals(1, actualOrders.size());
        assertArrayEquals(expectedgestOrd.toArray(), actualOrders.toArray(), "Le tuple di GestoriOrdiniDAO recuperate non sono identiche alle attese");
    }
    
    
    @Test
    public void testDoRetrievebyKeyGestioniOrdini() throws SQLException {
    	GestoriOrdiniBean expectedgestOrd = new GestoriOrdiniBean("LuBacco","Luigi","Bacco","lbgames@gmail.com","dd6c2b03adfe02e7ef9b1fd016581415",1700);
        GestoriOrdiniBean actualgestOrd =  gestoriOrdiniDAO.doRetrieveByKey("LuBacco");
        assertEquals(expectedgestOrd, actualgestOrd);
        //assertArrayEquals(expectedgestOrd.toArray(), actualgestOrd.toArray(), "Le tuple di GestoriOrdiniDAO recuperate non sono identiche alle attese");
    }
    
    @Test
    public void testDoSaveGestoriOrdini() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(GestoriOrdiniDAOTest.class.getClassLoader().getResourceAsStream("db/expected/GestoriOrdiniDAOTest/testDoSaveGestoriOrdini.xml"))
                 .getTable(GestoriOrdiniDAO.TABLE_NAME);

         
    	 gestoriOrdiniDAO.doSave("AldoD","Aldo","DAntuono","aldodantuono@libero.it","posterio",2000);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(GestoriOrdiniDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoUpdateGestoriOrdini() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(GestoriOrdiniDAOTest.class.getClassLoader().getResourceAsStream("db/expected/GestoriOrdiniDAOTest/testDoUpdateGestoriOrdini.xml"))
                 .getTable(GestoriOrdiniDAO.TABLE_NAME);

         
    	 gestoriOrdiniDAO.doUpdate("LuBacco","Luigi","Bacco","lbgames@gmail.com","paperoga1048",2000);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(GestoriOrdiniDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoDeleteGestoriOrdini() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(GestoriOrdiniDAOTest.class.getClassLoader().getResourceAsStream("db/expected/GestoriOrdiniDAOTest/testDoDeleteGestoriOrdini.xml"))
                 .getTable(GestoriOrdiniDAO.TABLE_NAME);

    	 gestoriOrdiniDAO.doDelete("LuBacco");

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(GestoriOrdiniDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    
    
    
}
