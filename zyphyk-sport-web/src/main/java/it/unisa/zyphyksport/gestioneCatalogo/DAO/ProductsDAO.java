package it.unisa.zyphyksport.gestioneCatalogo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;
import it.unisa.zyphyksport.gestioneCatalogo.interfaceDS.ProductsInterf;

public class ProductsDAO implements ProductsInterf{

	public static final String TABLE_NAME = "products";
	
	private DataSource ds = null;
	

	public ProductsDAO(DataSource ds) {
		
		this.ds = ds;

	}
	
	@Override
	public synchronized void doSave(String id, String name, String sport, String brand, int price)
			throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String insertSQL = "INSERT INTO " + ProductsDAO.TABLE_NAME
				+ " (ID, NAME, SPORT, BRAND, PRICE) VALUES (?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			
			
			preparedStmt = connection.prepareStatement(insertSQL);
			preparedStmt.setString(1, id);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, sport);
			preparedStmt.setString(4, brand);
			preparedStmt.setInt(5, price);
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
	public synchronized void doUpdate(String id, String name, String sport, String brand, int price)
			throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String updateSQL = "UPDATE " + ProductsDAO.TABLE_NAME
				+ " SET NAME = ?, SPORT = ?, BRAND = ?, PRICE = ?" + " WHERE ID = ?";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(updateSQL);
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, sport);
			preparedStmt.setString(3, brand);
			preparedStmt.setInt(4, price);
			
			preparedStmt.setString(5, id);
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
	public synchronized void doDelete(String id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		
		String updateSQL = "UPDATE " + ProductsDAO.TABLE_NAME
				+ " SET REMOVED = TRUE" + " WHERE ID = ?";
		
		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(updateSQL);
			
			preparedStmt.setString(1, id);
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
	public synchronized ProductsBean doRetrieveByKey(String id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		ProductsBean prodBean = new ProductsBean(null, null, null, null, 0);

		String selectSQL = "SELECT * FROM " + ProductsDAO.TABLE_NAME + " WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);
			preparedStmt.setString(1, id);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				prodBean.setId(rs.getString("ID"));
				prodBean.setName(rs.getString("NAME"));
				prodBean.setSport(rs.getString("SPORT"));
				prodBean.setBrand(rs.getString("BRAND"));
				prodBean.setPrice(rs.getInt("PRICE"));
				prodBean.setRemoved(rs.getBoolean("REMOVED"));
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
		return prodBean;
	}

	@Override
	public synchronized Set<ProductsBean> doRetrieveAll(String order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Set<ProductsBean> products = new HashSet<ProductsBean>();

		String selectSQL = "SELECT * FROM " + ProductsDAO.TABLE_NAME;
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				ProductsBean prodBean = new ProductsBean(null, null, null, null, 0);
				
				prodBean.setId(rs.getString("ID"));
				prodBean.setName(rs.getString("NAME"));
				prodBean.setSport(rs.getString("SPORT"));
				prodBean.setBrand(rs.getString("BRAND"));
				prodBean.setPrice(rs.getInt("PRICE"));
				prodBean.setRemoved(rs.getBoolean("REMOVED"));
				products.add(prodBean);
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
		return products;
	}


	@Override
	public synchronized Set<ProductsBean> doRetrieveAllExists(String order) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStmt = null;

		Set<ProductsBean> products = new HashSet<ProductsBean>();

		String selectSQL = "SELECT * FROM " + ProductsDAO.TABLE_NAME + " WHERE REMOVED = FALSE";
		

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStmt = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				ProductsBean prodBean = new ProductsBean(null, null, null, null, 0);
				
				prodBean.setId(rs.getString("ID"));
				prodBean.setName(rs.getString("NAME"));
				prodBean.setSport(rs.getString("SPORT"));
				prodBean.setBrand(rs.getString("BRAND"));
				prodBean.setPrice(rs.getInt("PRICE"));
				prodBean.setRemoved(rs.getBoolean("REMOVED"));
				products.add(prodBean);
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
		return products;
	}

}
