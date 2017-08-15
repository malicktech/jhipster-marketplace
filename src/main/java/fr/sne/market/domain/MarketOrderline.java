package fr.sne.market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * MarketOrderItems = MarketOrderline
 * cross-reference table Order-Line,
 * holds information on each product ordered on one order with quantity fiedl
 * since customer can purchase multiple items on the same order
 */
@ApiModel(description = "MarketOrderItems = MarketOrderline cross-reference table Order-Line, holds information on each product ordered on one order with quantity fiedl since customer can purchase multiple items on the same order")
@Entity
@Table(name = "market_orderline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marketorderline")
public class MarketOrderline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    private MarketOrders marketOrders;

    @OneToMany(mappedBy = "marketOrderline")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MarketOrderItemsDetails> details = new HashSet<>();

    @OneToMany(mappedBy = "marketOrderline")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MarketProduct> details = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public MarketOrderline quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MarketOrders getMarketOrders() {
        return marketOrders;
    }

    public MarketOrderline marketOrders(MarketOrders marketOrders) {
        this.marketOrders = marketOrders;
        return this;
    }

    public void setMarketOrders(MarketOrders marketOrders) {
        this.marketOrders = marketOrders;
    }

    public Set<MarketOrderItemsDetails> getDetails() {
        return details;
    }

    public MarketOrderline details(Set<MarketOrderItemsDetails> marketOrderItemsDetails) {
        this.details = marketOrderItemsDetails;
        return this;
    }

    public MarketOrderline addDetails(MarketOrderItemsDetails marketOrderItemsDetails) {
        this.details.add(marketOrderItemsDetails);
        marketOrderItemsDetails.setMarketOrderline(this);
        return this;
    }

    public MarketOrderline removeDetails(MarketOrderItemsDetails marketOrderItemsDetails) {
        this.details.remove(marketOrderItemsDetails);
        marketOrderItemsDetails.setMarketOrderline(null);
        return this;
    }

    public void setDetails(Set<MarketOrderItemsDetails> marketOrderItemsDetails) {
        this.details = marketOrderItemsDetails;
    }

    public Set<MarketProduct> getDetails() {
        return details;
    }

    public MarketOrderline details(Set<MarketProduct> marketProducts) {
        this.details = marketProducts;
        return this;
    }

    public MarketOrderline addDetails(MarketProduct marketProduct) {
        this.details.add(marketProduct);
        marketProduct.setMarketOrderline(this);
        return this;
    }

    public MarketOrderline removeDetails(MarketProduct marketProduct) {
        this.details.remove(marketProduct);
        marketProduct.setMarketOrderline(null);
        return this;
    }

    public void setDetails(Set<MarketProduct> marketProducts) {
        this.details = marketProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketOrderline marketOrderline = (MarketOrderline) o;
        if (marketOrderline.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrderline.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrderline{" +
            "id=" + getId() +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
