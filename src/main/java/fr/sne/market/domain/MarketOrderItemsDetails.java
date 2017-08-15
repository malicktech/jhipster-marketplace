package fr.sne.market.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MarketOrderItemsDetails.
 */
@Entity
@Table(name = "market_order_items_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marketorderitemsdetails")
public class MarketOrderItemsDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_key")
    private String key;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private MarketOrderline marketOrderline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public MarketOrderItemsDetails key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public MarketOrderItemsDetails value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MarketOrderline getMarketOrderline() {
        return marketOrderline;
    }

    public MarketOrderItemsDetails marketOrderline(MarketOrderline marketOrderline) {
        this.marketOrderline = marketOrderline;
        return this;
    }

    public void setMarketOrderline(MarketOrderline marketOrderline) {
        this.marketOrderline = marketOrderline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketOrderItemsDetails marketOrderItemsDetails = (MarketOrderItemsDetails) o;
        if (marketOrderItemsDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrderItemsDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrderItemsDetails{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
