package it.unisa.zyphyksport.gestioneUtente.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;
import it.unisa.zyphyksport.gestioneUtente.interfaceDS.ClientiInterf;

public class ClientiDAO implements ClientiInterf{
	public static final String TABLE_NAME = "clienti";
	private DataSource ds = null;
	
	public ClientiDAO(DataSource ds) {
		super();
		this.ds = ds;
	}

	@Override
	public synchronized void doSave(String username, int cartId, String name, String surname, String email, String pass_word,
			LocalDate birthDate) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;		
		
		String insertSQL = "INSERT INTO " + ClientiDAO.TABLE_NAME
				+ " (USERNAME, CART_ID, NAME, SURNAME, EMAIL, PASS_WORD, BIRTH_DATE) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		String password = pass_word;
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        String encryptedPassword = sb.toString();
		
		
		try {
			connection = ds.getConnection();
			
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setString(1, username);
			preparedStmt.setInt(2, cartId);
			preparedStmt.setString(3, name);
			preparedStmt.setString(4, surname);
			preparedStmt.setString(5, email);
			preparedStmt.setString(6, encryptedPassword);
			preparedStmt.setDate(7, java.sql.Date.valueOf(birthDate));
			preparedStmt.executeUpdate();

			connection.setAutoCommit(false);
			connection.commit();
		} catch (Exception e){
			System.out.println(e);
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
			LocalDate birthDate) throws SQLException {
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
			preparedStmt.setDate(5, java.sql.Date.valueOf(birthDate));
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
				Date date = rs.getDate("BIRTH_DATE"); 
				bean.setBirthDate(date.toLocalDate());
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
	public synchronized Set<ClientiBean> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Set<ClientiBean> array = new HashSet<ClientiBean>();

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
				Date date = rs.getDate("BIRTH_DATE"); 
				bean.setBirthDate(date.toLocalDate());
				
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
