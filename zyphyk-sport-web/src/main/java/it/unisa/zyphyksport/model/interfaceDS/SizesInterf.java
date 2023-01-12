package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.ClientiBean;
import it.unisa.zyphyksport.model.bean.SizesBean;

public interface SizesInterf {
	public void doSave(int productId, int value) throws SQLException;

	public void doDelete(int productId, int value) throws SQLException;

	public Collection<SizesBean> doRetrieveByProductId(int productId, String order) throws SQLException;
	
	public Collection<SizesBean> doRetrieveAll(String order) throws SQLException;
	
}