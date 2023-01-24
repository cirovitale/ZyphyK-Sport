package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;

import it.unisa.zyphyksport.model.bean.OrdersBean;

public interface OrdersInterf {

	public int doSave(String clienteUsername, String gestOrdUsername, LocalDateTime dateTime, String shippingAddress, String paymentMethod, int amount, boolean sent) throws SQLException;
	
	public void doUpdateSent(int id, String gestOrdUsername) throws SQLException;
	
	public void doDelete(int id) throws SQLException;

	public OrdersBean doRetrieveByKey(int id) throws SQLException;
	
	public Set<OrdersBean> doRetrieveAll(String order) throws SQLException;

	public Set<OrdersBean> doRetrieveAllCliente(String username, String order) throws SQLException;
	
}
