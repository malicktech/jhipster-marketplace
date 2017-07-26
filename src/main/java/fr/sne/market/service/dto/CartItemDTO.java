package fr.sne.market.service.dto;

import java.io.Serializable;

public class CartItemDTO implements Serializable {

    protected String cartItemId;
    protected String asin;
    protected String sellerNickname;
    protected String quantity;
    protected String title;
    protected String productGroup;
    protected String unitPrice;
    protected String totalCost;
    
    
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getSellerNickname() {
		return sellerNickname;
	}
	public void setSellerNickname(String sellerNickname) {
		this.sellerNickname = sellerNickname;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
    
    
    
}
