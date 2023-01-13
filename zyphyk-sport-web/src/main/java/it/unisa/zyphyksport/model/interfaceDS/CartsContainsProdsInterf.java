package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.CartsContainsProdsBean;

public interface CartsContainsProdsInterf {
	public void doSave(int cartId, String productId, int quantity, int size) throws SQLException;
	
	public void doUpdate(int cartId, String productId, int quantity, int size) throws SQLException;
	
	public void doDelete(int cartId, String productId, int size) throws SQLException;

	public Collection<CartsContainsProdsBean> doRetrieveAll(String order) throws SQLException;
	
	public Collection<CartsContainsProdsBean> doRetrieveAllByCartId(int id, String order) throws SQLException;

}