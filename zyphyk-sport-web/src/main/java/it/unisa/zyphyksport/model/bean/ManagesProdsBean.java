package it.unisa.zyphyksport.model.bean;

import java.util.Objects;

public class ManagesProdsBean {
	
	private static final long serialVersionUID = 1L;
	private String gestCatUsername;
	private int productId;
	private int tipologia;
	
	public ManagesProdsBean(String gestCatUsername, int productId, int tipologia) {
		super();
		this.gestCatUsername = gestCatUsername;
		this.productId = productId;
		this.tipologia = tipologia;
	}

	public String getGestCatUsername() {
		return gestCatUsername;
	}

	public void setGestCatUsername(String gestCatUsername) {
		this.gestCatUsername = gestCatUsername;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	@Override
	public String toString() {
		return "MangesProdsBean [gestCatUsername=" + gestCatUsername + ", productId=" + productId + ", tipologia="
				+ tipologia + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(gestCatUsername, productId, tipologia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManagesProdsBean other = (ManagesProdsBean) obj;
		return Objects.equals(gestCatUsername, other.gestCatUsername) && productId == other.productId
				&& tipologia == other.tipologia;
	}
	
}
