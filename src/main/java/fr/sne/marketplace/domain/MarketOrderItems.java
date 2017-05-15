package fr.sne.marketplace.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A MarketOrderItems.
 */
@Entity
@Table(name = "market_order_items")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marketorderitems")
public class MarketOrderItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private MarketOrder marketOrder;

    @ManyToOne
    private MarketOrder order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public MarketOrderItems title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public MarketOrderItems description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MarketOrderItems price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte[] getImg() {
        return img;
    }

    public MarketOrderItems img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public MarketOrderItems imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getName() {
        return name;
    }

    public MarketOrderItems name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarketOrder getMarketOrder() {
        return marketOrder;
    }

    public MarketOrderItems marketOrder(MarketOrder marketOrder) {
        this.marketOrder = marketOrder;
        return this;
    }

    public void setMarketOrder(MarketOrder marketOrder) {
        this.marketOrder = marketOrder;
    }

    public MarketOrder getOrder() {
        return order;
    }

    public MarketOrderItems order(MarketOrder marketOrder) {
        this.order = marketOrder;
        return this;
    }

    public void setOrder(MarketOrder marketOrder) {
        this.order = marketOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketOrderItems marketOrderItems = (MarketOrderItems) o;
        if (marketOrderItems.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrderItems.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrderItems{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", price='" + getPrice() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + imgContentType + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
