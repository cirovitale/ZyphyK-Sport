package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.OrdersContainsProdsBean;


public interface OrdersContainsProdsInterf {

	public void doSave(int orderId, String productId, int quantity) throws SQLException;
	
	public void doDelete(int orderId, String productId) throws SQLException;
	
	public Collection<OrdersContainsProdsBean> doRetrieveAll(String order) throws SQLException;
}
