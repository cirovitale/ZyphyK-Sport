package it.unisa.zyphyksport.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class GestoriOrdiniBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String name;
	private String surname;
	private String email;
	private String pass_word;
	private int ral;
	private final String RUOLO = "gestOrd";
	
	public GestoriOrdiniBean(String username, String name, String surname, String email, String pass_word, int ral) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.pass_word = pass_word;
		this.ral = ral;
	}

	public String getUsername() {
		return username;
	}
	
	public String getRUOLO() {
		return RUOLO;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass_word() {
		return pass_word;
	}

	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

	public int getRal() {
		return ral;
	}

	public void setRal(int ral) {
		this.ral = ral;
	}

	@Override
	public String toString() {
		return "GestoriOrdiniBean [username=" + username + ", name=" + name + ", surname=" + surname + ", email="
				+ email + ", pass_word=" + pass_word + ", ral=" + ral + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, pass_word, ral, surname, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GestoriOrdiniBean other = (GestoriOrdiniBean) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(pass_word, other.pass_word) && ral == other.ral
				&& Objects.equals(surname, other.surname) && Objects.equals(username, other.username);
	}	
}
