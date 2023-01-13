package it.unisa.zyphyksport.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.zyphyksport.model.bean.CartsBean;
import it.unisa.zyphyksport.model.interfaceDS.CartsInterf;

public class CartsDAO implements CartsInterf{
	private static final String TABLE_NAME = "carts";
	private DataSource ds = null;
	
	public CartsDAO(DataSource ds) {
		super();
		this.ds = ds;
	}

	@Override
	public synchronized int doSave(int amount) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt1 = null;
		PreparedStatement preparedStmt2 = null;
		
		String insertSQL = "INSERT INTO " + CartsDAO.TABLE_NAME
				+ " (AMOUNT) VALUES (?)";
		
		String selectSQL = "SELECT LAST_INSERT_ID()"; 
		
		int cartId = 0;
		try {
			connection = ds.getConnection();
			
			preparedStmt1 = connection.prepareStatement(insertSQL);
			preparedStmt1.setInt(1, amount);

			preparedStmt1.executeUpdate();
			
			preparedStmt2 = connection.prepareStatement(selectSQL);
			ResultSet rs2 = preparedStmt2.executeQuery();
			
			if(rs2.next()) {
				cartId = rs2.getInt("LAST_INSERT_ID()");
			}

			connection.setAutoCommit(false);
			connection.commit();
		} finally {
			try {
				if (preparedStmt1 != null)
					preparedStmt1.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return cartId;
	}

	@Override
	public synchronized void doUpdate(int id, int amount) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String updateSQL = "UPDATE " + CartsDAO.TABLE_NAME
				+ " SET AMOUNT = ?" + " WHERE ID = ?";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(updateSQL);
			preparedStmt.setInt(1, amount);
			preparedStmt.setInt(2, id);

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
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + CartsDAO.TABLE_NAME + " WHERE ID = ?";

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
	public synchronized CartsBean doRetrieveByKey(int id) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		CartsBean bean = new CartsBean(0,0);

		String selectSQL = "SELECT * FROM " + CartsDAO.TABLE_NAME + " WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setInt(1, id);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				bean.setId(rs.getInt("ID"));
				bean.setAmount(rs.getInt("AMOUNT"));
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
	public synchronized Collection<CartsBean> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Collection<CartsBean> array = new LinkedList<CartsBean>();

		String selectSQL = "SELECT * FROM " + CartsDAO.TABLE_NAME;
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				CartsBean bean = new CartsBean(0,0);
				
				bean.setId(rs.getInt("ID"));
				bean.setAmount(rs.getInt("AMOUNT"));
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
