package it.unisa.zyphyksport.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.zyphyksport.model.bean.ClientiBean;
import it.unisa.zyphyksport.model.interfaceDS.ClientiInterf;

public class ClientiDAO implements ClientiInterf{
	private static final String TABLE_NAME = "clienti";
	private DataSource ds = null;
	
	public ClientiDAO(DataSource ds) {
		super();
		this.ds = ds;
	}

	@Override
	public synchronized void doSave(String username, int cartId, String name, String surname, String email, String pass_word,
			LocalDateTime birthDate) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;		
		
		String insertSQL = "INSERT INTO " + ClientiDAO.TABLE_NAME
				+ " (USERNAME, CART_ID, NAME, SURNAME, EMAIL, PASS_WORD, BIRTH_DATE) VALUES (?, ?, ?, ?, ?, MD5(?), ?)";
		
		try {
			connection = ds.getConnection();
			
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setString(1, username);
			preparedStmt.setInt(2, cartId);
			preparedStmt.setString(3, name);
			preparedStmt.setString(4, surname);
			preparedStmt.setString(5, email);
			preparedStmt.setString(6, pass_word);
			preparedStmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
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
	public synchronized void doUpdate(String username, String name, String surname, String email, String pass_word,
			LocalDateTime birthDate) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String updateSQL = "UPDATE " + ClientiDAO.TABLE_NAME
				+ " SET NAME = ?, SURNAME = ?, EMAIL = ?, PASS_WORD = ?, BIRTH_DATE = ?" + " WHERE USERNAME = ?";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(updateSQL);
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, surname);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, pass_word);
			preparedStmt.setTimestamp(5, java.sql.Timestamp.valueOf(birthDate));
			preparedStmt.setString(6, username);
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
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + ClientiDAO.TABLE_NAME + " WHERE USERNAME = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setString(1, username);

			preparedStmt.executeUpdate();

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
	public synchronized ClientiBean doRetrieveByKey(String username) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		ClientiBean bean = new ClientiBean(null, 0, null, null, null, null, null);

		String selectSQL = "SELECT * FROM " + ClientiDAO.TABLE_NAME + " WHERE USERNAME = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setString(1, username);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				bean.setUsername(rs.getString("USERNAME"));
				bean.setCartId(rs.getInt("CART_ID"));
				bean.setName(rs.getString("NAME"));
				bean.setSurname(rs.getString("SURNAME"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setPass_word(rs.getString("PASS_WORD"));
				bean.setBirthDate(rs.getTimestamp("BIRTH_DATE").toLocalDateTime());
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
		return bean;
	}

	@Override
	public synchronized Collection<ClientiBean> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Collection<ClientiBean> array = new LinkedList<ClientiBean>();

		String selectSQL = "SELECT * FROM " + ClientiDAO.TABLE_NAME;
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				ClientiBean bean = new ClientiBean(null, 0, null, null, null, null, null);
				
				bean.setUsername(rs.getString("USERNAME"));
				bean.setCartId(rs.getInt("CART_ID"));
				bean.setName(rs.getString("NAME"));
				bean.setSurname(rs.getString("SURNAME"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setPass_word(rs.getString("PASS_WORD"));
				bean.setBirthDate(rs.getTimestamp("BIRTH_DATE").toLocalDateTime());
				
				array.add(bean);
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
