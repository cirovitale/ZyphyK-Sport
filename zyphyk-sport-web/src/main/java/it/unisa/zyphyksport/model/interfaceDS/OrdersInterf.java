package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.OrdersBean;

public interface OrdersInterf {

	public void doSave(int id, String clienteUsername, String gestOrdUsername, LocalDateTime dateTime, String shippingAddress, String paymentMethod, int amount, boolean sent) throws SQLException;
	
	public void doUpdate(int id, LocalDateTime dateTime, String shippingAddress, String paymentMethod, int amount, boolean sent) throws SQLException;

	public void doUpdateSent(int id, String gestOrdUsername) throws SQLException;
	
	public void doDelete(int id) throws SQLException;

	public OrdersBean doRetrieveByKey(int id) throws SQLException;
	
	public Collection<OrdersBean> doRetrieveAll(String order) throws SQLException;
}
