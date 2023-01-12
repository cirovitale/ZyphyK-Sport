package it.unisa.zyphyksport.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;


import it.unisa.zyphyksport.model.bean.ManagesProdsBean;
import it.unisa.zyphyksport.model.interfaceDS.ManagesProdsInterf;

public class ManagesProdsDAO implements ManagesProdsInterf {
	private static final String TABLE_NAME = "manages_prods";
	private DataSource ds = null;
	
	public ManagesProdsDAO(DataSource ds) {
		this.ds = ds;

	}

	@Override
	public synchronized void doSave(String gestCatUsername, String productId, int tipologia) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String insertSQL = "INSERT INTO " + ManagesProdsDAO.TABLE_NAME
				+ " (GEST_CAT_USERNAME, PRODUCT_ID, TIPOLOGIA) VALUES (?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setString(1, gestCatUsername);
			preparedStmt.setString(2, productId);
			preparedStmt.setInt(3, tipologia);

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
	public synchronized void doDelete(String gestCatUsername, String productId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + ManagesProdsDAO.TABLE_NAME + " WHERE GEST_CAT_USERNAME = ? && PRODUCT_ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setString(1, gestCatUsername);
			preparedStmt.setString(2, productId);

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
	public synchronized ManagesProdsBean doRetrieveByKey(String gestCatUsername, String productId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		ManagesProdsBean managesProds = new ManagesProdsBean(null,null,0);

		String selectSQL = "SELECT * FROM " + ManagesProdsDAO.TABLE_NAME + " WHERE GEST_CAT_USERNAME = ? AND WHERE PRODUCT_ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setString(1, gestCatUsername);
			preparedStmt.setString(2, productId);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				managesProds.setGestCatUsername(rs.getString("GEST_CAT_USERNAME"));
				managesProds.setProductId(rs.getString("PRODUCT_ID"));
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
		return managesProds;
		
	}

	@Override
	public Collection<ManagesProdsBean> doRetrieveAll(String gestCatUsername, String productId) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Collection<ManagesProdsBean> array = new LinkedList<ManagesProdsBean>();

		String selectSQL = "SELECT * FROM " + ManagesProdsDAO.TABLE_NAME;
		

		if (gestCatUsername != null && !gestCatUsername.equals("")) {
			selectSQL += " ORDER BY " + gestCatUsername;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				ManagesProdsBean managesProds = new ManagesProdsBean(null,null,0);
				managesProds.setGestCatUsername(rs.getString("GEST_CAT_USERNAME"));
				managesProds.setProductId(rs.getString("PRODUCT_ID"));

				array.add(managesProds);
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
