package it.unisa.zyphyksport.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class SizesBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value;
	private String productId;
	
	public SizesBean(int value, String productId) {
		super();
		this.value = value;
		this.productId = productId;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Override
	public String toString() {
		return "SizesBean [value=" + value + ", productId=" + productId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SizesBean other = (SizesBean) obj;
		return Objects.equals(productId, other.productId) && value == other.value;
	}
	
	
}
