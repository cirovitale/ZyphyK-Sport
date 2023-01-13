package it.unisa.zyphyksport.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class ManagesProdsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String gestCatUsername;
	private String productId;
	// 0: aggiunta, 1: rimozione, 2: modifica
	private int tipologia;
	private int id;
	
	public ManagesProdsBean(int id, String gestCatUsername, String productId, int tipologia) {
		super();
		this.id = id;
		this.gestCatUsername = gestCatUsername;
		this.productId = productId;
		this.tipologia = tipologia;
		this.id = id;
	}

	public String getGestCatUsername() {
		return gestCatUsername;
	}

	public void setGestCatUsername(String gestCatUsername) {
		this.gestCatUsername = gestCatUsername;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ManagesProdsBean [gestCatUsername=" + gestCatUsername + ", productId=" + productId + ", tipologia="
				+ tipologia + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(gestCatUsername, id, productId, tipologia);
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
		return Objects.equals(gestCatUsername, other.gestCatUsername) && id == other.id
				&& Objects.equals(productId, other.productId) && tipologia == other.tipologia;
	}
	
	
	
}
