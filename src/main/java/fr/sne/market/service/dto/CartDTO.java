package fr.sne.market.service.dto;

import java.io.Serializable;
import java.util.List;


public class CartDTO implements Serializable {
	
    protected String cartId;
    protected String hmac;
    protected String purchaseURL;
    protected String subTotal;
    protected List<CartItemDTO> cartItem;
    
    
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public String getPurchaseURL() {
		return purchaseURL;
	}
	public void setPurchaseURL(String purchaseURL) {
		this.purchaseURL = purchaseURL;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public List<CartItemDTO> getCartItem() {
		return cartItem;
	}
	public void setCartItem(List<CartItemDTO> cartItem) {
		this.cartItem = cartItem;
	}

}
