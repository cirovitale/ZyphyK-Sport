package it.unisa.zyphyksport.gestioneVendite.interfaceDS;

import java.sql.SQLException;
import java.util.Set;

import it.unisa.zyphyksport.gestioneVendite.bean.OrdersContainsProdsBean;


public interface OrdersContainsProdsInterf {

	public void doSave(int orderId, String productId, int quantity, int size) throws SQLException;
	
	public void doDelete(int orderId, String productId) throws SQLException;
	
	public Set<OrdersContainsProdsBean> doRetrieveAll(String order) throws SQLException;
	
	public Set<OrdersContainsProdsBean> doRetrieveAllProds(int orderId, String order) throws SQLException;
}
