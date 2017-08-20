package fr.sne.market.service.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import com.amazon.api.ECS.client.jax.DecimalWithUnits;
import com.amazon.api.ECS.client.jax.Image;
import com.amazon.api.ECS.client.jax.ItemAttributes;

import java.util.Objects;

/**
 * A DTO for the MarketItem entity.
 */
public class MarketItemDetailsDTO extends MarketItemDTO implements Serializable {

	
	public String desc;
	
	public String smallImage;
    public String largeImage;

	public List<String> feature;
	
	public ItemAttributes.ItemDimensions itemDimensions;
	public ItemAttributes.PackageDimensions packageDimensions;
	public BigInteger packageQuantity;
	
	public List<String> author;
	public String binding;
	public String color;

	public String genre;
	
	public String productGroup;
	public String productTypeName;	
    public String productTypeSubcategory;
    public String publicationDate;
    public String publisher;
    public String releaseDate;
    public String size;
    public String sku;
    public String studio;
    
	
    
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}
    
	public List<String> getAuthor() {
		return author;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
    
    public ItemAttributes.ItemDimensions getItemDimensions() {
		return itemDimensions;
	}

	public void setItemDimensions(ItemAttributes.ItemDimensions itemDimensions) {
		this.itemDimensions = itemDimensions;
	}

	public ItemAttributes.PackageDimensions getPackageDimensions() {
		return packageDimensions;
	}

	public void setPackageDimensions(ItemAttributes.PackageDimensions packageDimensions) {
		this.packageDimensions = packageDimensions;
	}

	public BigInteger getPackageQuantity() {
		return packageQuantity;
	}

	public void setPackageQuantity(BigInteger packageQuantity) {
		this.packageQuantity = packageQuantity;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductTypeSubcategory() {
		return productTypeSubcategory;
	}

	public void setProductTypeSubcategory(String productTypeSubcategory) {
		this.productTypeSubcategory = productTypeSubcategory;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public BigInteger getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(BigInteger numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String warranty;
	
//	private String desc;
	private String ean; // to delete
	private String label; // to delete
	public String isbn;
	public BigInteger numberOfItems;
	public String operatingSystem;
	
	public List<String> getFeature() {
        if (feature == null) {
            feature = new ArrayList<String>();
        }
        return this.feature;
    }

	public void setFeature(List<String> feature) {
		this.feature = feature;
	}
	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

}
