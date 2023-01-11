package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.GestoriCatalogoBean;

public interface GestoriCatalogoInterf {

	public void doSave(String username, String name, String surname, String email, String pass_word, int ral) throws SQLException;
	
	public void doUpdate(GestoriCatalogoBean gestCat, String username, String name, String surname, String email, String pass_word, int ral) throws SQLException;

	public void doDelete(String username) throws SQLException;

	public GestoriCatalogoBean doRetrieveByKey(String username) throws SQLException;
	
	//public GestoriCatalogoBean doRetrieveByKeyEmail(String email) throws SQLException;
	
	public Collection<GestoriCatalogoBean> doRetrieveAll(String username) throws SQLException;
	
}
