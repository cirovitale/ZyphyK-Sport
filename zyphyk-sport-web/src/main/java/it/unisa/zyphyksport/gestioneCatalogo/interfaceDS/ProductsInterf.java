package it.unisa.zyphyksport.gestioneCatalogo.interfaceDS;

import java.sql.SQLException;
import java.util.Set;

import it.unisa.zyphyksport.gestioneCatalogo.bean.ProductsBean;

public interface ProductsInterf {

	public void doSave(String id, String name, String sport, String brand, int price) throws SQLException;
	
	public void doUpdate(String id, String name, String sport, String brand, int price) throws SQLException;

	public void doDelete(String id) throws SQLException;

	public ProductsBean doRetrieveByKey(String id) throws SQLException;
	
	public Set<ProductsBean> doRetrieveAll(String order) throws SQLException;

	public Set<ProductsBean> doRetrieveAllExists(String order) throws SQLException;
	
}
