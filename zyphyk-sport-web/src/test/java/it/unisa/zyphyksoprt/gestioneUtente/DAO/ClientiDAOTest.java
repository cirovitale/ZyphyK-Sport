package it.unisa.zyphyksoprt.gestioneUtente.DAO;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.SQLException;
import java.time.LocalDate;
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

import it.unisa.zyphyksport.gestioneUtente.DAO.ClientiDAO;
import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class ClientiDAOTest extends DataSourceBasedDBTestCase{
	private ClientiDAO clientiDAO;
	
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
        clientiDAO = new ClientiDAO(getDataSource());
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
	public void testDoSaveClienti() throws Exception{
		ITable expectedTable = new FlatXmlDataSetBuilder()
			.build(ClientiDAOTest.class.getClassLoader().getResourceAsStream("db/expected/ClientiDAOTest/testDoSaveClienti.xml"))
			.getTable(ClientiDAO.TABLE_NAME);
	
		clientiDAO.doSave("fraDant", 3, "Francesco", "Dante", "fr.da31@gmail.com", "cliente", LocalDate.of(1991,02,15));
	
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable(ClientiDAO.TABLE_NAME);
	      
		Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}
    
    @Test
    public void testDoUpdateClienti() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(ClientiDAOTest.class.getClassLoader().getResourceAsStream("db/expected/ClientiDAOTest/testDoUpdateClienti.xml"))
                 .getTable(ClientiDAO.TABLE_NAME);

         
         clientiDAO.doUpdate("peppeRoma", "Peppe", "Rossi", "g.rom26@gmail.com", "4983a0ab83ed86e0e7213c8783940193", LocalDate.of(1991,02,15));

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(ClientiDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }

    @Test
    public void testDoDeleteClienti() throws Exception{
    	 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(ClientiDAOTest.class.getClassLoader().getResourceAsStream("db/expected/ClientiDAOTest/testDoDeleteClienti.xml"))
                 .getTable(ClientiDAO.TABLE_NAME);

         
         clientiDAO.doDelete("peppeRoma");

         IDataSet databaseDataSet = getConnection().createDataSet();
         ITable actualTable = databaseDataSet.getTable(ClientiDAO.TABLE_NAME);
        
         Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void testDoRetrieveByKeyClienti() throws SQLException {
    	ClientiBean expectedCliente = new ClientiBean("daniPicci", 1, "Daniele", "Piccirillo", "dani_san@gmail.com", "194567ccf1a6ad5f751d618a4961719b", LocalDate.of(1996, 9, 6));

    	ClientiBean actualCliente =  clientiDAO.doRetrieveByKey("daniPicci");
        assertEquals(expectedCliente, actualCliente);
    }
    
    @Test
    public void testDoRetrieveAllClienti() throws SQLException {
    	ClientiBean cliente1 = new ClientiBean("daniPicci", 1, "Daniele", "Piccirillo", "dani_san@gmail.com", "194567ccf1a6ad5f751d618a4961719b", LocalDate.of(1996, 9, 6));
    	ClientiBean cliente2 = new ClientiBean("marioRossi", 2, "Mario", "Rossi", "mar-rossi@gmail.com", "4983a0ab83ed86e0e7213c8783940193", LocalDate.of(1996, 6, 13));
    	ClientiBean cliente3 = new ClientiBean("peppeRoma", 3, "Giuseppe", "Roma", "g.rom1@gmail.com", "4983a0ab83ed86e0e7213c8783940193", LocalDate.of(2001, 4, 25));
    	ClientiBean cliente4 = new ClientiBean("angeloPorte", 4, "Angelo", "Portelli", "portelli_angelo@gmail.com", "4983a0ab83ed86e0e7213c8783940193", LocalDate.of(1978, 1, 7));
        Set<ClientiBean> expectedClienti = new HashSet<>();
        expectedClienti.add(cliente1);
        expectedClienti.add(cliente2);
        expectedClienti.add(cliente3);
        expectedClienti.add(cliente4);

        Set<ClientiBean> actualClienti =  clientiDAO.doRetrieveAll(null);
        
        assertEquals(4, actualClienti.size());
        assertArrayEquals(expectedClienti.toArray(), actualClienti.toArray(), "Le tuple di ClientiDAO recuperate non sono identiche alle attese");
    }
}