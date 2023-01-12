package it.unisa.zyphyksport.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.zyphyksport.model.bean.GestoriCatalogoBean;
import it.unisa.zyphyksport.model.interfaceDS.GestoriCatalogoInterf;

public class GestoriCatalogoDAO implements GestoriCatalogoInterf {
	private static final String TABLE_NAME = "gestori_catalogo";
	private DataSource ds = null;
	
	public GestoriCatalogoDAO(DataSource ds) {
		this.ds = ds;
		
		System.out.println("Creazione DataSource...");
	}

	@Override
	public synchronized void doSave(String username, String name, String surname,  String email, String pass_word, int ral) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String insertSQL = "INSERT INTO " + GestoriCatalogoDAO.TABLE_NAME
				+ " (USERNAME, NAME, SURNAME, EMAIL, PASS_WORD,"
				+ "RAL) VALUES (?, ?, ?, ?, MD5(?), ?)";
		
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
	public synchronized void doUpdate(GestoriCatalogoBean gestCat, String username, String name, String surname, String email,
			String pass_word, int ral) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String updateSQL = "UPDATE " + GestoriCatalogoDAO.TABLE_NAME
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

		String deleteSQL = "DELETE FROM " + GestoriCatalogoDAO.TABLE_NAME + " WHERE username = ?";

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
	public synchronized GestoriCatalogoBean doRetrieveByKey(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		GestoriCatalogoBean gestCat = new GestoriCatalogoBean(null,null,null,null,null,0);

		String selectSQL = "SELECT * FROM " + GestoriCatalogoDAO.TABLE_NAME + " WHERE USESRNAME = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setString(1, username);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				gestCat.setUsername(rs.getString("USERNAME"));
				gestCat.setName(rs.getString("NAME"));
				gestCat.setUsername(rs.getString("SURNAME"));
				gestCat.setEmail(rs.getString("EMAIL"));
				gestCat.setPass_word(rs.getString("PASS_WORD"));
				gestCat.setRal(rs.getInt("RAL"));
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
		return gestCat;
	}

	@Override
	public synchronized Collection<GestoriCatalogoBean> doRetrieveAll(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Collection<GestoriCatalogoBean> array = new LinkedList<GestoriCatalogoBean>();

		String selectSQL = "SELECT * FROM " + GestoriCatalogoDAO.TABLE_NAME;
		

		if (username != null && !username.equals("")) {
			selectSQL += " ORDER BY " + username;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				GestoriCatalogoBean gestCat = new GestoriCatalogoBean(null,null,null,null,null,0);
				gestCat.setUsername(rs.getString("CODICE_FISCALE"));
				gestCat.setName(rs.getString("NOME"));
				gestCat.setUsername(rs.getString("COGNOME"));
				gestCat.setEmail(rs.getString("EMAIL"));
				gestCat.setPass_word(rs.getString("PASS_WORD"));
				gestCat.setRal(rs.getInt("RAL"));
				array.add(gestCat);
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
