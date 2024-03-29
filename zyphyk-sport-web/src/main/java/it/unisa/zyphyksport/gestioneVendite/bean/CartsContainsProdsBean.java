package it.unisa.zyphyksport.gestioneVendite.bean;

import java.io.Serializable;
import java.util.Objects;

public class CartsContainsProdsBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cartId;
	private String productId;
	private int quantity;
	private int size;
	
	public CartsContainsProdsBean(int cartId, String productId, int quantity, int size) {
		super();
		this.cartId = cartId;
		this.productId = productId;
		this.quantity = quantity;
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}



	public int getCartId() {
		return cartId;
	}
	
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartsContainsProdsBean [cartId=" + cartId + ", productId=" + productId + ", quantity=" + quantity
				+ ", size=" + size + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, productId, quantity, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartsContainsProdsBean other = (CartsContainsProdsBean) obj;
		return cartId == other.cartId && Objects.equals(productId, other.productId) && quantity == other.quantity
				&& size == other.size;
	}
}
