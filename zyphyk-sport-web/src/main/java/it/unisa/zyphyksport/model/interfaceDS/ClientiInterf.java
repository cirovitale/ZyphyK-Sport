package it.unisa.zyphyksport.model.interfaceDS;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import it.unisa.zyphyksport.model.bean.ClientiBean;

public interface ClientiInterf {
	public void doSave(String username, int cartId, String name, String surname, String email, String pass_word, LocalDateTime birthDate) throws SQLException;
	
	public void doUpdate(String username, String name, String surname, String email, String pass_word, LocalDateTime birthDate) throws SQLException;

	public void doDelete(String username) throws SQLException;

	public ClientiBean doRetrieveByKey(String username) throws SQLException;
	
	public Collection<ClientiBean> doRetrieveAll(String order) throws SQLException;
	
}