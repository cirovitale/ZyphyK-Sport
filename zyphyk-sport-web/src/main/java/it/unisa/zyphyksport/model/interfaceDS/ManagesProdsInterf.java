package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.ManagesProdsBean;

public interface ManagesProdsInterf {

	public void doSave(String gestCatUsername, int productId, int tipologia) throws SQLException;

	public void doDelete(String gestCatUsername, int productId) throws SQLException;
	
	public ManagesProdsBean doRetrieveByKey(String gestCatUsername, int productId) throws SQLException;
	
	public Collection<ManagesProdsBean> doRetrieveAll(String gestCatUsername, int productId) throws SQLException;
	
}
