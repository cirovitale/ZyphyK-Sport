package it.unisa.zyphyksport.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.zyphyksport.model.bean.GestoriOrdiniBean;
import it.unisa.zyphyksport.model.interfaceDS.GestoriOrdiniInterf;

public class GestoriOrdiniDAO implements GestoriOrdiniInterf {
	private static final String TABLE_NAME = "gestori_ordini";
	private DataSource ds = null;
	
	public GestoriOrdiniDAO(DataSource ds) {
		this.ds = ds;
		
		System.out.println("Creazione DataSource...");
	}
	

	@Override
	public synchronized void doSave(String username, String name, String surname, String email, String pass_word, int ral)
			throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String insertSQL = "INSERT INTO " + GestoriOrdiniDAO.TABLE_NAME
				+ " (USERNAME, NAME, USERNAME, EMAIL, PASS_WORD,"
				+ "RAL) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, surname);
			preparedStmt.setString(4, email);
			preparedStmt.setString(5,pass_word);
			preparedStmt.setInt(6, ral);

			preparedStmt.executeUpdate();

			connection.setAutoCommit(false);
			connection.commit();
		} finally {
			try {
				if (preparedStmt != null)
					preparedStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}

	@Override
	public synchronized void doUpdate(String username, String name, String surname, String email, String pass_word, int ral)
			throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String updateSQL = "UPDATE " + GestoriOrdiniDAO.TABLE_NAME
				+ " SET USERNAME = ?, NAME = ?, SURNAME = ?, EMAIL = ?, PASS_WORD = ?,"
				+ "RAL = ?" + " WHERE USERNAME = ?";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(updateSQL);
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, surname);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, pass_word);
			preparedStmt.setInt(5, ral);
			
			preparedStmt.executeUpdate();

			connection.setAutoCommit(false);
			connection.commit();
		} finally {
			try {
				if (preparedStmt != null)
					preparedStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
	}

	@Override
	public synchronized void doDelete(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + GestoriOrdiniDAO.TABLE_NAME + " WHERE username = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setString(1, username);

		} finally {
			try {
				if (preparedStmt != null)
					preparedStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}

	@Override
	public synchronized GestoriOrdiniBean doRetrieveByKey(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		GestoriOrdiniBean gestOrd = new GestoriOrdiniBean(null,null,null,null,null,0);

		String selectSQL = "SELECT * FROM " + GestoriOrdiniDAO.TABLE_NAME + " WHERE USESRNAME = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setString(1, username);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				gestOrd.setUsername(rs.getString("USERNAME"));
				gestOrd.setName(rs.getString("NAME"));
				gestOrd.setUsername(rs.getString("SURNAME"));
				gestOrd.setEmail(rs.getString("EMAIL"));
				gestOrd.setPass_word(rs.getString("PASS_WORD"));
				gestOrd.setRal(rs.getInt("RAL"));
			}

		} finally {
			try {
				if (preparedStmt != null)
					preparedStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return gestOrd;
		
	}

	@Override
	public synchronized Collection<GestoriOrdiniBean> doRetrieveAll(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Collection<GestoriOrdiniBean> array = new LinkedList<GestoriOrdiniBean>();

		String selectSQL = "SELECT * FROM " + GestoriOrdiniDAO.TABLE_NAME;
		

		if (username != null && !username.equals("")) {
			selectSQL += " ORDER BY " + username;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				GestoriOrdiniBean gestOrd = new GestoriOrdiniBean(null,null,null,null,null,0);
				gestOrd.setUsername(rs.getString("CODICE_FISCALE"));
				gestOrd.setName(rs.getString("NOME"));
				gestOrd.setUsername(rs.getString("COGNOME"));
				gestOrd.setEmail(rs.getString("EMAIL"));
				gestOrd.setPass_word(rs.getString("PASS_WORD"));
				gestOrd.setRal(rs.getInt("RAL"));
				array.add(gestOrd);
			}

		} finally {
			try {
				if (preparedStmt != null)
					preparedStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return array;
	}

	}

