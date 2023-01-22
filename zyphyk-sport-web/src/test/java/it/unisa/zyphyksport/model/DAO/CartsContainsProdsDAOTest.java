package it.unisa.zyphyksport.model.DAO;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unisa.zyphyksport.model.bean.CartsContainsProdsBean;

public class CartsContainsProdsDAOTest extends DataSourceBasedDBTestCase{

	 private static IDatabaseTester tester;
	 private CartsContainsProdsDAO CartsContainsProdsDAO;
	 private Connection connection;
	    @Override
	    protected DataSource getDataSource() {
	        JdbcDataSource dataSource = new JdbcDataSource();
	        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/database_Zyphyk-Sport.sql'");
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
	        // setUp del padre serve a (1) chiamare il nostro getSetUpOperation, e (2) il nostro getDataSet() per inizializzare il DB
	        super.setUp();
	        connection = getConnection().getConnection();
	        CartsContainsProdsDAO = new CartsContainsProdsDAO(connection);
	    }

	    @AfterEach
	    public void tearDown() throws Exception {
	        // tearDown del padre serve a chiamare il nostro getTearDownOperation
	        super.tearDown();
	    }

	    @Test
	    public void testGetAll() {
	        // TODO
	    }

	    @Test
	    public void testInsertScan() {
	        // TODO
	    }
}
