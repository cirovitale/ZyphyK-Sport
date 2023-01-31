package it.unisa.zyphyksport.gestioneCatalogo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import it.unisa.zyphyksport.gestioneCatalogo.bean.SizesBean;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.SizesInterf;

public class SizesDAO implements SizesInterf{
	static final String TABLE_NAME = "sizes";
	private DataSource ds = null;
	
	public SizesDAO(DataSource ds) {
		super();
		this.ds = ds;
	}

	@Override
	public synchronized int doSave(String productId, int valued) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		PreparedStatement preparedStmt2 = null;

		String insertSQL = "INSERT INTO " + SizesDAO.TABLE_NAME
				+ " (PRODUCT_ID, VALUED) VALUES (?, ?)";
		
		String selectSQL = "SELECT MAX(COUNT) AS MAX FROM " + SizesDAO.TABLE_NAME;
		
		int count = 0;
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setString(1, productId);
			preparedStmt.setInt(2, valued);

			preparedStmt.executeUpdate();
			
			preparedStmt2 = connection.prepareStatement(selectSQL);
			ResultSet rs2 = preparedStmt2.executeQuery();
			
			if(rs2.next()) {
				count = rs2.getInt("MAX");
			}

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
		
		return count;
	}

	@Override
	public synchronized void doDelete(String productId, int valued) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + SizesDAO.TABLE_NAME + " WHERE PRODUCT_ID = ? AND VALUED = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setString(1, productId);
			preparedStmt.setInt(2, valued);

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
	public synchronized Set<SizesBean> doRetrieveByProductId(String productId, String order) throws SQLException {
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
				SizesBean bean = new SizesBean(0,null,0);
				bean.setProductId(rs.getString("PRODUCT_ID"));
				bean.setValue(rs.getInt("VALUED"));
				bean.setCount(rs.getInt("COUNT"));
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
	public synchronized Set<SizesBean> doRetrieveAll(String order) throws SQLException {
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
				SizesBean bean = new SizesBean(0,null,0);
				bean.setProductId(rs.getString("PRODUCT_ID"));
				bean.setValue(rs.getInt("VALUED"));
				bean.setCount(rs.getInt("COUNT"));
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
