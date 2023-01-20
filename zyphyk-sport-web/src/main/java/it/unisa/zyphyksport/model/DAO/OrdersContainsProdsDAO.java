package it.unisa.zyphyksport.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import it.unisa.zyphyksport.model.bean.OrdersContainsProdsBean;
import it.unisa.zyphyksport.model.interfaceDS.OrdersContainsProdsInterf;

public class OrdersContainsProdsDAO implements OrdersContainsProdsInterf{

	private static final String TABLE_NAME = "orders_contains_prods";
	
	private DataSource ds = null;
	

	public OrdersContainsProdsDAO(DataSource ds) {
		
		this.ds = ds;
		
	}
	
	
	@Override
	public synchronized void doSave(int orderId, String productId, int quantity, int size) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		PreparedStatement preparedStmtProdotto = null;
		
		String insertSQL = "INSERT INTO " + OrdersContainsProdsDAO.TABLE_NAME
				+ " (ORDER_ID, PRODUCT_ID, QUANTITY, SIZE, PRICE) VALUES (?, ?, ?, ?, ?)";
		
		String selectSQL = "SELECT PRICE FROM PRODUCTS WHERE ID = ?";
		
		
		
		try {
			connection = ds.getConnection();
			
			preparedStmtProdotto = connection.prepareStatement(selectSQL);
			preparedStmtProdotto.setString(1, productId);
			ResultSet rs = preparedStmtProdotto.executeQuery();
			int costo = 0;
			if(rs.next()) {
				costo = rs.getInt("PRICE");
			}
			
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setInt(1, orderId);
			preparedStmt.setString(2, productId);
			preparedStmt.setInt(3, quantity);
			preparedStmt.setInt(4, size);
			preparedStmt.setInt(5, costo);
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
	public synchronized void doDelete(int orderId, String productId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + OrdersContainsProdsDAO.TABLE_NAME + " WHERE ORDER_ID = ? AND PRODUCT_ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setInt(1, orderId);
			preparedStmt.setString(2, productId);
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
	public synchronized Set<OrdersContainsProdsBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Set<OrdersContainsProdsBean> ordersContProds = new HashSet<OrdersContainsProdsBean>();

		String selectSQL = "SELECT * FROM " + OrdersContainsProdsDAO.TABLE_NAME;
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				OrdersContainsProdsBean orderContProd = new OrdersContainsProdsBean(0, null,0,0,0);
				orderContProd.setOrderId(rs.getInt("ORDER_ID"));
				orderContProd.setProductId(rs.getString("PRODUCT_ID"));
				orderContProd.setQuantity(rs.getInt("QUANTITY"));
				orderContProd.setSize(rs.getInt("SIZE"));
				orderContProd.setPrice(rs.getInt("PRICE"));
				ordersContProds.add(orderContProd);
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
		return ordersContProds;
	}
	
	
	public synchronized Set<OrdersContainsProdsBean> doRetrieveAllProds(int orderId, String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Set<OrdersContainsProdsBean> ordersContProds = new HashSet<OrdersContainsProdsBean>();

		String selectSQL = "SELECT * FROM " + OrdersContainsProdsDAO.TABLE_NAME + " WHERE ORDER_ID = ?";
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setInt(1, orderId);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				OrdersContainsProdsBean orderContProd = new OrdersContainsProdsBean(0, null,0,0,0);
				orderContProd.setOrderId(rs.getInt("ORDER_ID"));
				orderContProd.setProductId(rs.getString("PRODUCT_ID"));
				orderContProd.setQuantity(rs.getInt("QUANTITY"));
				orderContProd.setSize(rs.getInt("SIZE"));
				orderContProd.setPrice(rs.getInt("PRICE"));
				ordersContProds.add(orderContProd);
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
		return ordersContProds;
	}

	
}
