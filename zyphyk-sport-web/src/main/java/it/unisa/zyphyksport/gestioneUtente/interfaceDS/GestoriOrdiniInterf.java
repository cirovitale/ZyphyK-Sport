package it.unisa.zyphyksport.gestioneUtente.interfaceDS;

import java.sql.SQLException;
import java.util.Set;

import it.unisa.zyphyksport.gestioneUtente.bean.GestoriOrdiniBean;

public interface GestoriOrdiniInterf {

	public void doSave(String username, String name, String surname, String email, String pass_word, int ral) throws SQLException;
	
	public void doUpdate(String username, String name, String surname, String email, String pass_word, int ral) throws SQLException;

	public void doDelete(String username) throws SQLException;

	public GestoriOrdiniBean doRetrieveByKey(String username) throws SQLException;
	
	public Set<GestoriOrdiniBean> doRetrieveAll(String order) throws SQLException;
}
