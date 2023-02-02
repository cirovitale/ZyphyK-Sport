package it.unisa.zyphyksport.gestioneVendite.interfaceDS;

import java.sql.SQLException;
import java.util.Set;

import it.unisa.zyphyksport.gestioneVendite.bean.CartsContainsProdsBean;

public interface CartsContainsProdsInterf {
	public void doSave(int cartId, String productId, int quantity, int size) throws SQLException;
	
	public void doUpdate(int cartId, String productId, int quantity) throws SQLException;
	
	public void doUpdateSize(int cartId, String productId, int quantity, int size) throws SQLException;
	
	public void doDelete(int cartId, String productId, int size) throws SQLException;
	
	public CartsContainsProdsBean doRetrieveByKey(int cartId, String productId, int size) throws SQLException;

	public Set<CartsContainsProdsBean> doRetrieveAll(String order) throws SQLException;
	
	public Set<CartsContainsProdsBean> doRetrieveAllByCartId(int id, String order) throws SQLException;

}