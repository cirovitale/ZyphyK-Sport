package it.unisa.zyphyksport.gestioneCatalogo.interfaceDS;

import java.sql.SQLException;
import java.util.Set;

import it.unisa.zyphyksport.gestioneCatalogo.bean.ManagesProdsBean;

public interface ManagesProdsInterf {

	public void doSave(String gestCatUsername, String productId, int tipologia) throws SQLException;

	public void doDelete(int id) throws SQLException;
	
	public ManagesProdsBean doRetrieveByKey(int id) throws SQLException;
	
	public Set<ManagesProdsBean> doRetrieveAll(String order) throws SQLException;
	
}
