package it.unisa.zyphyksport.gestioneVendite.interfaceDS;

import java.sql.SQLException;
import java.util.Set;

import it.unisa.zyphyksport.gestioneVendite.bean.CartsBean;


public interface CartsInterf {
	public int doSave(int amount) throws SQLException;
	
	public void doUpdate(int id, int amount) throws SQLException;

	public void doDelete(int id) throws SQLException;

	public CartsBean doRetrieveByKey(int id) throws SQLException;
	
	public Set<CartsBean> doRetrieveAll(String order) throws SQLException;
	
}