package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Set;

import it.unisa.zyphyksport.model.bean.SizesBean;

public interface SizesInterf {
	public void doSave(String productId, int value) throws SQLException;

	public void doDelete(String productId, int value) throws SQLException;

	public Set<SizesBean> doRetrieveByProductId(String productId, String order) throws SQLException;
	
	public Set<SizesBean> doRetrieveAll(String order) throws SQLException;
	
}