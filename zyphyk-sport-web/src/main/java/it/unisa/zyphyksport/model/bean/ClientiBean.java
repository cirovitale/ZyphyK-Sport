package it.unisa.zyphyksport.model.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
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
  	private LocalDateTime birthDate;
  	
	public ClientiBean(String username, int cartId, String name, String surname, String email, String pass_word,
			LocalDateTime birthDate) {
		super();
		this.username = username;
		this.cartId = cartId;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.pass_word = pass_word;
		this.birthDate = birthDate;
	}
	
	public String getUsername() {
		return username;
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
	
	public LocalDateTime getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "Clienti [username=" + username + ", cartId=" + cartId + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", pass_word=" + pass_word + ", birthDate=" + birthDate + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(birthDate, cartId, email, name, pass_word, surname, username);
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
		return Objects.equals(birthDate, other.birthDate) && cartId == other.cartId
				&& Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(pass_word, other.pass_word) && Objects.equals(surname, other.surname)
				&& Objects.equals(username, other.username);
	}
}
