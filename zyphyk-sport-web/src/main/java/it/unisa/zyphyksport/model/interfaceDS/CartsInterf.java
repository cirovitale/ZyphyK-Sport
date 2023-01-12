package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.CartsBean;


public interface CartsInterf {
	public void doSave(int id, int amount) throws SQLException;
	
	public void doUpdate(int id, int amount) throws SQLException;

	public void doDelete(int id) throws SQLException;

	public CartsBean doRetrieveByKey(int id) throws SQLException;
	
	public Collection<CartsBean> doRetrieveAll(String order) throws SQLException;
	
}