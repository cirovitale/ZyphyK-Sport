package it.unisa.zyphyksport.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;
import it.unisa.zyphyksport.model.bean.CartsContainsProdsBean;
import it.unisa.zyphyksport.model.interfaceDS.CartsContainsProdsInterf;

public class CartsContainsProdsDAO implements CartsContainsProdsInterf{
	private static final String TABLE_NAME = "carts_contains_prods";
	private DataSource ds = null;
	
	public CartsContainsProdsDAO(DataSource ds) {
		super();
		this.ds = ds;
	}

	@Override
	public synchronized void doSave(int cartId, String productId) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String insertSQL = "INSERT INTO " + CartsContainsProdsDAO.TABLE_NAME
				+ "(CART_ID, PRODUCT_ID) VALUES (?, ?)";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setInt(1, cartId);
			preparedStmt.setString(2, productId);

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
	public synchronized void doDelete(int cartId, String productId) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		String deleteSQL = "DELETE FROM " + CartsContainsProdsDAO.TABLE_NAME + " WHERE CART_ID = ? AND PRODUCT_ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(deleteSQL);
			preparedStmt.setInt(1, cartId);
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
	public synchronized Collection<CartsContainsProdsBean> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Collection<CartsContainsProdsBean> array = new LinkedList<CartsContainsProdsBean>();

		String selectSQL = "SELECT * FROM " + CartsContainsProdsDAO.TABLE_NAME;
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				CartsContainsProdsBean bean = new CartsContainsProdsBean(0,null);
				bean.setCartId(rs.getInt("CART_ID"));
				bean.setProductId(rs.getString("PRODUCT_ID"));
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
