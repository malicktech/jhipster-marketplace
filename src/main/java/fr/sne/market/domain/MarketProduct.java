package fr.sne.market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * hold market ordered product information
 */
@ApiModel(description = "hold market ordered product information")
@Entity
@Table(name = "market_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marketproduct")
public class MarketProduct implements Serializable {

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

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @ManyToOne
    private MarketOrderline marketOrderline;

    @OneToMany(mappedBy = "marketProduct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MarketProductAttributes> attributes = new HashSet<>();

    @ManyToOne
    private MarketProductCategory category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public MarketProduct title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public MarketProduct description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MarketProduct price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public MarketProduct category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImg() {
        return img;
    }

    public MarketProduct img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public MarketProduct imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public MarketOrderline getMarketOrderline() {
        return marketOrderline;
    }

    public MarketProduct marketOrderline(MarketOrderline marketOrderline) {
        this.marketOrderline = marketOrderline;
        return this;
    }

    public void setMarketOrderline(MarketOrderline marketOrderline) {
        this.marketOrderline = marketOrderline;
    }

    public Set<MarketProductAttributes> getAttributes() {
        return attributes;
    }

    public MarketProduct attributes(Set<MarketProductAttributes> marketProductAttributes) {
        this.attributes = marketProductAttributes;
        return this;
    }

    public MarketProduct addAttributes(MarketProductAttributes marketProductAttributes) {
        this.attributes.add(marketProductAttributes);
        marketProductAttributes.setMarketProduct(this);
        return this;
    }

    public MarketProduct removeAttributes(MarketProductAttributes marketProductAttributes) {
        this.attributes.remove(marketProductAttributes);
        marketProductAttributes.setMarketProduct(null);
        return this;
    }

    public void setAttributes(Set<MarketProductAttributes> marketProductAttributes) {
        this.attributes = marketProductAttributes;
    }

    public MarketProductCategory getCategory() {
        return category;
    }

    public MarketProduct category(MarketProductCategory marketProductCategory) {
        this.category = marketProductCategory;
        return this;
    }

    public void setCategory(MarketProductCategory marketProductCategory) {
        this.category = marketProductCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketProduct marketProduct = (MarketProduct) o;
        if (marketProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketProduct{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", price='" + getPrice() + "'" +
            ", category='" + getCategory() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + imgContentType + "'" +
            "}";
    }
}
