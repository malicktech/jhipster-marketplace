package fr.sne.market.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MarketOrderItemsDetails entity.
 */
public class MarketOrderItemsDetailsDTO implements Serializable {

    private Long id;

    private String key;

    private String value;

    private Long marketOrderlineId;

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

    public Long getMarketOrderlineId() {
        return marketOrderlineId;
    }

    public void setMarketOrderlineId(Long marketOrderlineId) {
        this.marketOrderlineId = marketOrderlineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO = (MarketOrderItemsDetailsDTO) o;
        if(marketOrderItemsDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrderItemsDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrderItemsDetailsDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
