package it.unisa.zyphyksport.gestioneCatalogo.bean;

import java.io.Serializable;
import java.util.Objects;
 
public class ProductsBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String sport;
	private String brand;
	private int price;
	private boolean removed;
	
	public ProductsBean(String id, String name, String sport, String brand, int price) {
		super();
		this.id = id;
		this.name = name;
		this.sport = sport;
		this.brand = brand;
		this.price = price;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSport() {
		return sport;
	}
	
	public void setSport(String sport) {
		this.sport = sport;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	@Override
	public String toString() {
		return "ProductsBean [id=" + id + ", name=" + name + ", sport=" + sport + ", brand=" + brand + ", price="
				+ price + ", removed=" + removed + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(brand, id, name, price, removed, sport);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductsBean other = (ProductsBean) obj;
		return Objects.equals(brand, other.brand) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& price == other.price && removed == other.removed && Objects.equals(sport, other.sport);
	}
	
}
