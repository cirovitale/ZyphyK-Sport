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
import it.unisa.zyphyksport.model.bean.GestoriCatalogoBean;

public class GestoriCatalogoDAOTest extends DataSourceBasedDBTestCase{
	private GestoriCatalogoDAO gestCatDAO;
	
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
        gestCatDAO = new GestoriCatalogoDAO(getDataSource());
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testDoSaveGestCat() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(GestoriCatalogoDAOTest.class.getClassLoader().getResourceAsStream("db/expected/GestCatDAOTest/testDoSaveGestCat.xml"))
                 .getTable(GestoriCatalogoDAO.TABLE_NAME);

    	 gestCatDAO.doSave("NickMir", "Nicola", "Mirra", "nick991@gmail.com", "gestoreCatalogo", 1400);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(GestoriCatalogoDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoUpdateGestCat() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(GestoriCatalogoDAOTest.class.getClassLoader().getResourceAsStream("db/expected/GestCatDAOTest/testDoUpdateGestCat.xml"))
                 .getTable(GestoriCatalogoDAO.TABLE_NAME);

         
    	 gestCatDAO.doUpdate("MasVarriale", "Massy", "Varria", "MasVar1@gmail.it", "6537dc2c9654109f741cd01878c8f403", 1900);

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(GestoriCatalogoDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoDeleteGestCat() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(GestoriCatalogoDAOTest.class.getClassLoader().getResourceAsStream("db/expected/GestCatDAOTest/testDoDeleteGestCat.xml"))
                 .getTable(GestoriCatalogoDAO.TABLE_NAME);

         
    	 gestCatDAO.doDelete("DarMoccia");

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(GestoriCatalogoDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoRetrieveByKeyGestCat() throws SQLException {
    	GestoriCatalogoBean expectedGestCat = new GestoriCatalogoBean("DarMoccia", "Dario", "Moccia", "darMoccia@gmail.com", "5fc35d28951c7bf323b8fff9885562ed", 1500);

    	GestoriCatalogoBean actualGestCat =  gestCatDAO.doRetrieveByKey("DarMoccia");
        assertEquals(expectedGestCat, actualGestCat);
    }
    
    @Test
    public void testDoRetrieveAllGestCat() throws SQLException {
    	GestoriCatalogoBean gestCat1 = new GestoriCatalogoBean("DarMoccia", "Dario", "Moccia", "darMoccia@gmail.com", "5fc35d28951c7bf323b8fff9885562ed", 1500);
    	GestoriCatalogoBean gestCat2 = new GestoriCatalogoBean("MasVarriale", "Massimo", "Varriale", "MasVar@gmail.com", "6537dc2c9654109f741cd01878c8f403", 1400);
    	Set<GestoriCatalogoBean> expectedGestCat = new HashSet<>();
    	expectedGestCat.add(gestCat1);
    	expectedGestCat.add(gestCat2);
        

        Set<GestoriCatalogoBean> actualGestCat =  gestCatDAO.doRetrieveAll(null);
        
        assertEquals(2, actualGestCat.size());
        assertArrayEquals(expectedGestCat.toArray(), actualGestCat.toArray(), "Le tuple di GestoriCatalogoDAO recuperate non sono identiche alle attese");
    }
}
