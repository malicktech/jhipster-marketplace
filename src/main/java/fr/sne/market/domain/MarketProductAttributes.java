package fr.sne.market.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Attributes / deatils of product: color, size, ..
 */
@ApiModel(description = "Attributes / deatils of product: color, size, ..")
@Entity
@Table(name = "market_product_attributes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marketproductattributes")
public class MarketProductAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_key")
    private String key;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private MarketProduct marketProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public MarketProductAttributes key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public MarketProductAttributes value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MarketProduct getMarketProduct() {
        return marketProduct;
    }

    public MarketProductAttributes marketProduct(MarketProduct marketProduct) {
        this.marketProduct = marketProduct;
        return this;
    }

    public void setMarketProduct(MarketProduct marketProduct) {
        this.marketProduct = marketProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketProductAttributes marketProductAttributes = (MarketProductAttributes) o;
        if (marketProductAttributes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketProductAttributes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketProductAttributes{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
