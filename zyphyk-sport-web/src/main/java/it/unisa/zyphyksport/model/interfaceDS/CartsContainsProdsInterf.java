package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.CartsContainsProdsBean;

public interface CartsContainsProdsInterf {
	public void doSave(int cartId, String productId) throws SQLException;
	
	public void doDelete(int cartId, String productId) throws SQLException;

	public Collection<CartsContainsProdsBean> doRetrieveAll(String order) throws SQLException;

}