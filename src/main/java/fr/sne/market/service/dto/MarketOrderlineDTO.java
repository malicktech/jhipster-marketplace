package fr.sne.market.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MarketOrderline entity.
 */
public class MarketOrderlineDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Long marketOrdersId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getMarketOrdersId() {
        return marketOrdersId;
    }

    public void setMarketOrdersId(Long marketOrdersId) {
        this.marketOrdersId = marketOrdersId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long marketProductId) {
        this.productId = marketProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketOrderlineDTO marketOrderlineDTO = (MarketOrderlineDTO) o;
        if(marketOrderlineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrderlineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrderlineDTO{" +
            "id=" + getId() +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
