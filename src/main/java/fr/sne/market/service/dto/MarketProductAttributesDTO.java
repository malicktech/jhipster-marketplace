package fr.sne.market.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MarketProductAttributes entity.
 */
public class MarketProductAttributesDTO implements Serializable {

    private Long id;

    private String key;

    private String value;

    private Long marketProductId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getMarketProductId() {
        return marketProductId;
    }

    public void setMarketProductId(Long marketProductId) {
        this.marketProductId = marketProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketProductAttributesDTO marketProductAttributesDTO = (MarketProductAttributesDTO) o;
        if(marketProductAttributesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketProductAttributesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketProductAttributesDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
