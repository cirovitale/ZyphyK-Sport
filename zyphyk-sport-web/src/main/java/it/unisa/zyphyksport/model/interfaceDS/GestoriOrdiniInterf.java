package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.GestoriOrdiniBean;

public interface GestoriOrdiniInterf {

	public void doSave(String username, String name, String surname, String email, String pass_word, int ral) throws SQLException;
	
	public void doUpdate(String username, String name, String surname, String email, String pass_word, int ral) throws SQLException;

	public void doDelete(String username) throws SQLException;

	public GestoriOrdiniBean doRetrieveByKey(String username) throws SQLException;
	
	//public GestoriCatalogoBean doRetrieveByKeyEmail(String email) throws SQLException;
	
	public Collection<GestoriOrdiniBean> doRetrieveAll(String username) throws SQLException;
}
