
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
    "Discount",
    "ReferencePrice",
    "Saving"
})
public class PriceDetails {

    @JsonProperty("Discount")
    private Object discount;
    @JsonProperty("ReferencePrice")
    private String referencePrice;
    @JsonProperty("Saving")
    private Saving saving;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public PriceDetails() {
    }

    /**
     * 
     * @param referencePrice
     * @param saving
     * @param discount
     */
    public PriceDetails(Object discount, String referencePrice, Saving saving) {
        super();
        this.discount = discount;
        this.referencePrice = referencePrice;
        this.saving = saving;
    }

    @JsonProperty("Discount")
    public Object getDiscount() {
        return discount;
    }

    @JsonProperty("Discount")
    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    @JsonProperty("ReferencePrice")
    public String getReferencePrice() {
        return referencePrice;
    }

    @JsonProperty("ReferencePrice")
    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
    }

    @JsonProperty("Saving")
    public Saving getSaving() {
        return saving;
    }

    @JsonProperty("Saving")
    public void setSaving(Saving saving) {
        this.saving = saving;
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
