package it.unisa.zyphyksport.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class OrdersContainsProdsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int orderId;
	private String productId;
	
	
	
	public OrdersContainsProdsBean(int orderId, String productId) {
		super();
		this.orderId = orderId;
		this.productId = productId;
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

	@Override
	public String toString() {
		return "OrdersContainsProds [orderId=" + orderId + ", productId=" + productId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
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
		return orderId == other.orderId && Objects.equals(productId, other.productId);
	}

	
	
	
}
