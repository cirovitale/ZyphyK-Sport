package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.ProductsBean;

public interface ProductsInterf {

	public void doSave(int id, String name, String sport, String brand, int price) throws SQLException;
	
	public void doUpdate(int id, String name, String sport, String brand, int price) throws SQLException;

	public void doDelete(int id) throws SQLException;

	public ProductsBean doRetrieveByKey(int id) throws SQLException;
	
	public Collection<ProductsBean> doRetrieveAll(String order) throws SQLException;
	
}
