package it.unisa.zyphyksport.gestioneUtente.interfaceDS;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

import it.unisa.zyphyksport.gestioneUtente.bean.ClientiBean;

public interface ClientiInterf {
	public void doSave(String username, int cartId, String name, String surname, String email, String pass_word, LocalDate birthDate) throws SQLException;
	
	public void doUpdate(String username, String name, String surname, String email, String pass_word, LocalDate birthDate) throws SQLException;

	public void doDelete(String username) throws SQLException;

	public ClientiBean doRetrieveByKey(String username) throws SQLException;
	
	public Set<ClientiBean> doRetrieveAll(String order) throws SQLException;
	
}