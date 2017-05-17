
package com.cdiscount.api.dto;

import java.util.HashMap;
import java.util.List;
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
    "ErrorMessage",
    "ItemCount",
    "PageCount",
    "PageNumber",
    "Products"
})
public class SearchResponseDTO {

    @JsonProperty("ErrorMessage")
    private Object errorMessage;
    @JsonProperty("ItemCount")
    private String itemCount;
    @JsonProperty("PageCount")
    private String pageCount;
    @JsonProperty("PageNumber")
    private String pageNumber;
    @JsonProperty("Products")
    private List<Product> products = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public SearchResponseDTO() {
    }

    /**
     * 
     * @param errorMessage
     * @param pageCount
     * @param pageNumber
     * @param itemCount
     * @param products
     */
    public SearchResponseDTO(Object errorMessage, String itemCount, String pageCount, String pageNumber, List<Product> products) {
        super();
        this.errorMessage = errorMessage;
        this.itemCount = itemCount;
        this.pageCount = pageCount;
        this.pageNumber = pageNumber;
        this.products = products;
    }

    @JsonProperty("ErrorMessage")
    public Object getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("ErrorMessage")
    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("ItemCount")
    public String getItemCount() {
        return itemCount;
    }

    @JsonProperty("ItemCount")
    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    @JsonProperty("PageCount")
    public String getPageCount() {
        return pageCount;
    }

    @JsonProperty("PageCount")
    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    @JsonProperty("PageNumber")
    public String getPageNumber() {
        return pageNumber;
    }

    @JsonProperty("PageNumber")
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @JsonProperty("Products")
    public List<Product> getProducts() {
        return products;
    }

    @JsonProperty("Products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
	// TODO - adapt to fiedls
    /* 
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name: ")
                .append(this.name).append("\n")
                .append("lastName: ")
                .append(this.lastName).append("\n")
                .append("age: ")
                .append(this.age).append("\n");

        for (Address address: this.addressList) {
            stringBuilder.append(address.toString());
        }

        return stringBuilder.toString();
    }
    */

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
