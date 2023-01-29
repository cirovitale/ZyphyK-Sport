package it.unisa.zyphyksport.gestioneVendite.bean;

import java.io.Serializable;
import java.util.Objects;

public class CartsBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int amount;
	
	public CartsBean(int id, int amount) {
		super();
		this.id = id;
		this.amount = amount;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "CartsBean [id=" + id + ", amount=" + amount + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(amount, id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartsBean other = (CartsBean) obj;
		return amount == other.amount && id == other.id;
	}
}
