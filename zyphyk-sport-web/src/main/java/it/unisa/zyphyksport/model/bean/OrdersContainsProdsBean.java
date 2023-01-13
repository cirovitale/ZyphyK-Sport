package it.unisa.zyphyksport.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class OrdersContainsProdsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int orderId;
	private String productId;
	private int quantity;
	private int size;
	private int price;
	
	
	
	public OrdersContainsProdsBean(int orderId, String productId, int quantity, int size, int price) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.size = size;
		this.price = price;
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
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	

	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	@Override
	public String toString() {
		return "OrdersContainsProdsBean [orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity
				+ ", size=" + size + ", price=" + price + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(orderId, price, productId, quantity, size);
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
		return orderId == other.orderId && price == other.price && Objects.equals(productId, other.productId)
				&& quantity == other.quantity && size == other.size;
	}

	

	
	

	

}
