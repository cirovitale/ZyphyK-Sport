package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.ClientiBean;
import it.unisa.zyphyksport.model.bean.SizesBean;

public interface SizesInterf {
	public void doSave(String productId, int value) throws SQLException;

	public void doDelete(String productId, int value) throws SQLException;

	public Collection<SizesBean> doRetrieveByProductId(String productId, String order) throws SQLException;
	
	public Collection<SizesBean> doRetrieveAll(String order) throws SQLException;
	
}