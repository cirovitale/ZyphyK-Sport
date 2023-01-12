package it.unisa.zyphyksport.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class OrdersContainsProdsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int orderId;
	private String productId;
	private int quantity;
	
	
	
	public OrdersContainsProdsBean(int orderId, String productId, int quantity) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return orderId;
	}
	
	public void setQuantity(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrdersContainsProdsBean [orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdersContainsProdsBean other = (OrdersContainsProdsBean) obj;
		return orderId == other.orderId && Objects.equals(productId, other.productId) && quantity == other.quantity;
	}

}
