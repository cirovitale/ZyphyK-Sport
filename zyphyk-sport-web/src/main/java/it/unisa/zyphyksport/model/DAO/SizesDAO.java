package it.unisa.zyphyksport.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import it.unisa.zyphyksport.model.bean.SizesBean;
import it.unisa.zyphyksport.model.interfaceDS.SizesInterf;

public class SizesDAO implements SizesInterf{
	private static final String TABLE_NAME = "sizes";
	private DataSource ds = null;
	
	public SizesDAO(DataSource ds) {
		super();
		this.ds = ds;
	}

	@Override
	public synchronized void doSave(String productId, int value) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String insertSQL = "INSERT INTO " + SizesDAO.TABLE_NAME
				+ " (PRODUCT_ID, VALUED) VALUES (?, ?)";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setString(1, productId);
			preparedStmt.setInt(2, value);

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
	public synchronized void doDelete(String productId, int value) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + SizesDAO.TABLE_NAME + " WHERE PRODUCT_ID = ? AND VALUED = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setString(1, productId);
			preparedStmt.setInt(2, value);

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
	public Set<SizesBean> doRetrieveByProductId(String productId, String order) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Set<SizesBean> array = new HashSet<SizesBean>();

		String selectSQL = "SELECT * FROM " + SizesDAO.TABLE_NAME + " WHERE PRODUCT_ID = ?";
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setString(1, productId);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				SizesBean bean = new SizesBean(0,null);
				bean.setProductId(rs.getString("PRODUCT_ID"));
				bean.setValue(rs.getInt("VALUED"));
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

	@Override
	public Set<SizesBean> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Set<SizesBean> array = new HashSet<SizesBean>();

		String selectSQL = "SELECT * FROM " + SizesDAO.TABLE_NAME;
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				SizesBean bean = new SizesBean(0,null);
				bean.setProductId(rs.getString("PRODUCT_ID"));
				bean.setValue(rs.getInt("VALUED"));
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
