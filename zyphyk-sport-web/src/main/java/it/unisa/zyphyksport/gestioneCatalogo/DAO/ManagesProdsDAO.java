package it.unisa.zyphyksport.gestioneCatalogo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import it.unisa.zyphyksport.gestioneCatalogo.bean.ManagesProdsBean;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ManagesProdsInterf;

public class ManagesProdsDAO implements ManagesProdsInterf {
	public static final String TABLE_NAME = "manages_prods";
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
	public synchronized void doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + ManagesProdsDAO.TABLE_NAME + " WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setInt(1, id);
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
	public synchronized ManagesProdsBean doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		ManagesProdsBean managesProds = new ManagesProdsBean(0,null,null,0);

		String selectSQL = "SELECT * FROM " + ManagesProdsDAO.TABLE_NAME + " WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setInt(1, id);


			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				managesProds.setId(rs.getInt("ID"));
				managesProds.setGestCatUsername(rs.getString("GEST_CAT_USERNAME"));
				managesProds.setProductId(rs.getString("PRODUCT_ID"));
				managesProds.setTipologia(rs.getInt("TIPOLOGIA"));
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
	public Set<ManagesProdsBean> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Set<ManagesProdsBean> array = new HashSet<ManagesProdsBean>();

		String selectSQL = "SELECT * FROM " + ManagesProdsDAO.TABLE_NAME;
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				ManagesProdsBean managesProds = new ManagesProdsBean(0,null,null,0);
				managesProds.setId(rs.getInt("ID"));
				managesProds.setGestCatUsername(rs.getString("GEST_CAT_USERNAME"));
				managesProds.setProductId(rs.getString("PRODUCT_ID"));
				managesProds.setTipologia(rs.getInt("TIPOLOGIA"));

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
