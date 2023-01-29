package it.unisa.zyphyksport.gestioneCatalogo.interfaceDS;

import java.sql.SQLException;
import java.util.Set;

import it.unisa.zyphyksport.gestioneCatalogo.bean.SizesBean;

public interface SizesInterf {
	public int doSave(String productId, int value) throws SQLException;

	public void doDelete(String productId, int value) throws SQLException;

	public Set<SizesBean> doRetrieveByProductId(String productId, String order) throws SQLException;
	
	public Set<SizesBean> doRetrieveAll(String order) throws SQLException;
	
}