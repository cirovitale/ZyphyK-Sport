package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.ManagesProdsBean;

public interface ManagesProdsInterf {

	public void doSave(String gestCatUsername, String productId, int tipologia) throws SQLException;

	public void doDelete(int id) throws SQLException;
	
	public ManagesProdsBean doRetrieveByKey(int id) throws SQLException;
	
	public Collection<ManagesProdsBean> doRetrieveAll(String order) throws SQLException;
	
}
