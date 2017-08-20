package fr.sne.market.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MarketItem entity.
 */
public class MarketItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String asin;
	private String title;
	private String mainImageUrl;
	
	private String price;
	private String lowestNewPrice;

	private String brand;
	private String model;
	private String manufacturer;
	
		

	public String getLowestNewPrice() {
		return lowestNewPrice;
	}

	public void setLowestNewPrice(String lowestNewPrice) {
		this.lowestNewPrice = lowestNewPrice;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the mainImageUrl
	 */
	public String getMainImageUrl() {
		return mainImageUrl;
	}

	/**
	 * @param mainImageUrl
	 *            the mainImageUrl to set
	 */
	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
}
