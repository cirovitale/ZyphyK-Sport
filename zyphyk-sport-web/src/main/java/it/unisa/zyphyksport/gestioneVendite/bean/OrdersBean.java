package it.unisa.zyphyksport.gestioneVendite.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrdersBean implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String clienteUsername;
	private String gestOrdUsername;
	private LocalDateTime dateTime;
	private String shippingAddress;
	private String paymentMethod;
	private int amount;
	private boolean sent;
	
	
	
	
	public OrdersBean(int id, String clienteUsername, String gestOrdUsername, LocalDateTime dateTime, String shippingAddress, String paymentMethod, int amount) {
		super();
		this.id = id;
		this.clienteUsername = clienteUsername;
		this.gestOrdUsername = gestOrdUsername;
		this.dateTime = dateTime;
		this.shippingAddress = shippingAddress;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getClienteUsername() {
		return clienteUsername;
	}
	
	public void setClienteUsername(String clienteUsername) {
		this.clienteUsername = clienteUsername;
	}
	
	public String getGestOrdUsername() {
		return gestOrdUsername;
	}
	
	public void setGestOrdUsername(String gestOrdUsername) {
		this.gestOrdUsername = gestOrdUsername;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public boolean isSent() {
		return sent;
	}
	
	public void setSent(boolean sent) {
		this.sent = sent;
	}
	
	@Override
	public String toString() {
		return "OrdersBean [id=" + id + ", clienteUsername=" + clienteUsername + ", gestOrdUsername=" + gestOrdUsername
				+ ", dateTime=" + dateTime + ", paymentMethod=" + paymentMethod + ", amount=" + amount + ", sent="
				+ sent + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(amount, clienteUsername, dateTime, gestOrdUsername, id, paymentMethod, sent);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdersBean other = (OrdersBean) obj;
		return amount == other.amount && Objects.equals(clienteUsername, other.clienteUsername)
				&& Objects.equals(dateTime, other.dateTime) && gestOrdUsername == other.gestOrdUsername
				&& id == other.id && Objects.equals(paymentMethod, other.paymentMethod) && sent == other.sent;
	}
	

}
