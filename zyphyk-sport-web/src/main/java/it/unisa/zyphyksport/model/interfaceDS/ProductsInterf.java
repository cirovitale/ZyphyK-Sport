package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.ProductsBean;

public interface ProductsInterf {

	public void doSave(String id, String name, String sport, String brand, int price) throws SQLException;
	
	public void doUpdate(String id, String name, String sport, String brand, int price) throws SQLException;

	public void doDelete(String id) throws SQLException;

	public ProductsBean doRetrieveByKey(String id) throws SQLException;
	
	public Collection<ProductsBean> doRetrieveAll(String order) throws SQLException;
	
}
