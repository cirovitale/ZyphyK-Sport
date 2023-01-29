package it.unisa.zyphyksport.gestioneUtente.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ClientiBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private int cartId;
	private String name; 
	private String surname;
	private String email;
	private String pass_word;
  	private LocalDate birthDate;
  	private final String RUOLO = "cliente";
  	
  	
  	
	public ClientiBean() {
		super();
	}

	public ClientiBean(String username, int cartId, String name, String surname, String email, String pass_word,
			LocalDate localDate) {
		super();
		this.username = username;
		this.cartId = cartId;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.pass_word = pass_word;
		this.birthDate = localDate;
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
	
	public int getCartId() {
		return cartId;
	}
	
	public void setCartId(int cartId) {
		this.cartId = cartId;
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
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ClientiBean [username=" + username + ", cartId=" + cartId + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", pass_word=" + pass_word + ", birthDate=" + birthDate + ", RUOLO=" + RUOLO
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(RUOLO, birthDate, cartId, email, name, pass_word, surname, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientiBean other = (ClientiBean) obj;
		return Objects.equals(RUOLO, other.RUOLO) && Objects.equals(birthDate, other.birthDate)
				&& cartId == other.cartId && Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(pass_word, other.pass_word) && Objects.equals(surname, other.surname)
				&& Objects.equals(username, other.username);
	}
	
	
}
