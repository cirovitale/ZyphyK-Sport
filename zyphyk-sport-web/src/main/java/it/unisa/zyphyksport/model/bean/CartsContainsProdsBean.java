package it.unisa.zyphyksport.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class CartsContainsProdsBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cartId;
	private String productId;
	
	public CartsContainsProdsBean(int cartId, String productId) {
		super();
		this.cartId = cartId;
		this.productId = productId;
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
	
	@Override
	public String toString() {
		return "CartsContainsProds [cartId=" + cartId + ", productId=" + productId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, productId);
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
		return cartId == other.cartId && Objects.equals(productId, other.productId);
	}
	
}
