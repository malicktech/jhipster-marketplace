
package com.cdiscount.api.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "AssociatedProducts",
    "BestOffer",
    "Brand",
    "Description",
    "Ean",
    "Id",
    "Images",
    "MainImageUrl",
    "Name",
    "Offers",
    "OffersCount",
    "Rating"
})
public class Product {

    @JsonProperty("AssociatedProducts")
    private Object associatedProducts;
    @JsonProperty("BestOffer")
    private BestOffer bestOffer;
    @JsonProperty("Brand")
    private String brand;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Ean")
    private Object ean;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Images")
    private Object images;
    @JsonProperty("MainImageUrl")
    private String mainImageUrl;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Offers")
    private Object offers;
    @JsonProperty("OffersCount")
    private String offersCount;
    @JsonProperty("Rating")
    private String rating;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Product() {
    }

    /**
     * 
     * @param id
     * @param ean
     * @param bestOffer
     * @param description
     * @param offers
     * @param name
     * @param associatedProducts
     * @param images
     * @param brand
     * @param offersCount
     * @param rating
     * @param mainImageUrl
     */
    public Product(Object associatedProducts, BestOffer bestOffer, String brand, String description, Object ean, String id, Object images, String mainImageUrl, String name, Object offers, String offersCount, String rating) {
        super();
        this.associatedProducts = associatedProducts;
        this.bestOffer = bestOffer;
        this.brand = brand;
        this.description = description;
        this.ean = ean;
        this.id = id;
        this.images = images;
        this.mainImageUrl = mainImageUrl;
        this.name = name;
        this.offers = offers;
        this.offersCount = offersCount;
        this.rating = rating;
    }

    @JsonProperty("AssociatedProducts")
    public Object getAssociatedProducts() {
        return associatedProducts;
    }

    @JsonProperty("AssociatedProducts")
    public void setAssociatedProducts(Object associatedProducts) {
        this.associatedProducts = associatedProducts;
    }

    @JsonProperty("BestOffer")
    public BestOffer getBestOffer() {
        return bestOffer;
    }

    @JsonProperty("BestOffer")
    public void setBestOffer(BestOffer bestOffer) {
        this.bestOffer = bestOffer;
    }

    @JsonProperty("Brand")
    public String getBrand() {
        return brand;
    }

    @JsonProperty("Brand")
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("Ean")
    public Object getEan() {
        return ean;
    }

    @JsonProperty("Ean")
    public void setEan(Object ean) {
        this.ean = ean;
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("Images")
    public Object getImages() {
        return images;
    }

    @JsonProperty("Images")
    public void setImages(Object images) {
        this.images = images;
    }

    @JsonProperty("MainImageUrl")
    public String getMainImageUrl() {
        return mainImageUrl;
    }

    @JsonProperty("MainImageUrl")
    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Offers")
    public Object getOffers() {
        return offers;
    }

    @JsonProperty("Offers")
    public void setOffers(Object offers) {
        this.offers = offers;
    }

    @JsonProperty("OffersCount")
    public String getOffersCount() {
        return offersCount;
    }

    @JsonProperty("OffersCount")
    public void setOffersCount(String offersCount) {
        this.offersCount = offersCount;
    }

    @JsonProperty("Rating")
    public String getRating() {
        return rating;
    }

    @JsonProperty("Rating")
    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
