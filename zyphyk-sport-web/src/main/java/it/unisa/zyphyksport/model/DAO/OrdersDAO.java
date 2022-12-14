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

import it.unisa.zyphyksport.model.bean.OrdersBean;
import it.unisa.zyphyksport.model.interfaceDS.OrdersInterf;

public class OrdersDAO implements OrdersInterf{

	private static final String TABLE_NAME = "orders";
	
	private DataSource ds = null;
	

	public OrdersDAO(DataSource ds) {
		
		this.ds = ds;

	}
	
	
	@Override
	public synchronized void doSave(int id, String clienteUsername, String gestOrdUsername, LocalDateTime dateTime, String shippingAddress,
			String paymentMethod, int amount, boolean sent) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String insertSQL = "INSERT INTO " + OrdersDAO.TABLE_NAME
				+ " (ID, CLIENTE_USERNAME, GEST_ORD_USERNAME, DATE_TIME, SHIPPING_ADDRESS, PAYMENT_METHOD, AMOUNT, SENT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			
			
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setInt(1, id);
			preparedStmt.setString(2, clienteUsername);
			preparedStmt.setString(3, gestOrdUsername);
			preparedStmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			preparedStmt.setString(5, shippingAddress);
			preparedStmt.setString(6, paymentMethod);
			preparedStmt.setInt(7, amount);
			preparedStmt.setBoolean(8, sent);
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
	public synchronized void doUpdate(int id, LocalDateTime dateTime, String shippingAddress, String paymentMethod, int amount, boolean sent)
			throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String updateSQL = "UPDATE " + OrdersDAO.TABLE_NAME
				+ " SET DATE_TIME = ?, SHIPPING_ADDRESS = ?, PAYMENT_METHOD = ?, AMOUNT = ?, SENT = ?" + " WHERE ID = ?";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(updateSQL);
			preparedStmt.setTimestamp(1, java.sql.Timestamp.valueOf(dateTime));
			preparedStmt.setString(2, shippingAddress);
			preparedStmt.setString(3, paymentMethod);
			preparedStmt.setInt(4, amount);
			
			preparedStmt.setBoolean(5, sent);
			preparedStmt.setInt(6, id);
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
	public synchronized void doUpdateSent(int id, String gestOrdUsername) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String updateSQL = "UPDATE " + OrdersDAO.TABLE_NAME
				+ " SET SENT = TRUE, GEST_ORD_USERNAME = ?" + " WHERE ID = ?";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(updateSQL);
			preparedStmt.setString(1, gestOrdUsername);
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
	
	public void doDelete(int id) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + OrdersDAO.TABLE_NAME + " WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setInt(1, id);

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
	public synchronized OrdersBean doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		OrdersBean orderBean = new OrdersBean(0, null, null, null, null, null, 0);

		String selectSQL = "SELECT * FROM " + OrdersDAO.TABLE_NAME + " WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setInt(1, id);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				orderBean.setId(rs.getInt("ID"));
				orderBean.setClienteUsername(rs.getString("CLIENTE_USERNAME"));
				orderBean.setGestOrdUsername(rs.getString("GEST_ORD_USERNAME"));
				orderBean.setDateTime(rs.getTimestamp("DATE_TIME").toLocalDateTime());
				orderBean.setShippingAddress(rs.getString("SHIPPING_ADDRESS"));
				orderBean.setPaymentMethod(rs.getString("PAYMENT_METHOD"));
				orderBean.setAmount(rs.getInt("AMOUNT"));
				orderBean.setSent(rs.getBoolean("SENT"));
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
		return orderBean;
	}

	@Override
	public synchronized Collection<OrdersBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Collection<OrdersBean> orders = new LinkedList<OrdersBean>();

		String selectSQL = "SELECT * FROM " + OrdersDAO.TABLE_NAME;
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				OrdersBean orderBean = new OrdersBean(0, null, null, null, null, null, 0);
				
				orderBean.setId(rs.getInt("ID"));
				orderBean.setClienteUsername(rs.getString("CLIENTE_USERNAME"));
				orderBean.setGestOrdUsername(rs.getString("GEST_ORD_USERNAME"));
				orderBean.setDateTime(rs.getTimestamp("DATE_TIME").toLocalDateTime());
				orderBean.setShippingAddress(rs.getString("SHIPPING_ADDRESS"));
				orderBean.setPaymentMethod(rs.getString("PAYMENT_METHOD"));
				orderBean.setAmount(rs.getInt("AMOUNT"));
				orderBean.setSent(rs.getBoolean("SENT"));
				orders.add(orderBean);
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
		return orders;
	}

}
