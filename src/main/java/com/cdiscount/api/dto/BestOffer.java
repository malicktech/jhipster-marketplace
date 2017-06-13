
package com.cdiscount.api.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Condition",
    "Id",
    "IsAvailable",
    "PriceDetails",
    "ProductURL",
    "SalePrice",
    "Seller",
    "Shippings",
    "Sizes"
})
public class BestOffer {

    @JsonProperty("Condition")
    private String condition;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("IsAvailable")
    private Boolean isAvailable;
    @JsonProperty("PriceDetails")
    private PriceDetails priceDetails;
    @JsonProperty("ProductURL")
    private String productURL;
    @JsonProperty("SalePrice")
    private String salePrice;
    @JsonProperty("Seller")
    private Seller seller;
    @JsonProperty("Shippings")
    private Object shippings;
    @JsonProperty("Sizes")
    private List<Size> sizes = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BestOffer() {
    }

    /**
     * 
     * @param sizes
     * @param id
     * @param priceDetails
     * @param condition
     * @param shippings
     * @param salePrice
     * @param seller
     * @param isAvailable
     * @param productURL
     */
    public BestOffer(String condition, String id, Boolean isAvailable, PriceDetails priceDetails, String productURL, String salePrice, Seller seller, Object shippings, List<Size> sizes) {
        super();
        this.condition = condition;
        this.id = id;
        this.isAvailable = isAvailable;
        this.priceDetails = priceDetails;
        this.productURL = productURL;
        this.salePrice = salePrice;
        this.seller = seller;
        this.shippings = shippings;
        this.sizes = sizes;
    }

    @JsonProperty("Condition")
    public String getCondition() {
        return condition;
    }

    @JsonProperty("Condition")
    public void setCondition(String condition) {
        this.condition = condition;
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("IsAvailable")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    @JsonProperty("IsAvailable")
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @JsonProperty("PriceDetails")
    public PriceDetails getPriceDetails() {
        return priceDetails;
    }

    @JsonProperty("PriceDetails")
    public void setPriceDetails(PriceDetails priceDetails) {
        this.priceDetails = priceDetails;
    }

    @JsonProperty("ProductURL")
    public String getProductURL() {
        return productURL;
    }

    @JsonProperty("ProductURL")
    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    @JsonProperty("SalePrice")
    public String getSalePrice() {
        return salePrice;
    }

    @JsonProperty("SalePrice")
    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    @JsonProperty("Seller")
    public Seller getSeller() {
        return seller;
    }

    @JsonProperty("Seller")
    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @JsonProperty("Shippings")
    public Object getShippings() {
        return shippings;
    }

    @JsonProperty("Shippings")
    public void setShippings(Object shippings) {
        this.shippings = shippings;
    }

    @JsonProperty("Sizes")
    public List<Size> getSizes() {
        return sizes;
    }

    @JsonProperty("Sizes")
    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
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
