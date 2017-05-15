package fr.sne.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Market entity
 */
@ApiModel(description = "Market entity")
@Entity
@Table(name = "market")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "market")
public class Market implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "market")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MarketInfo> infos = new HashSet<>();

    @OneToMany(mappedBy = "market")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductCategory> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Market name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public Market content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<MarketInfo> getInfos() {
        return infos;
    }

    public Market infos(Set<MarketInfo> marketInfos) {
        this.infos = marketInfos;
        return this;
    }

    public Market addInfo(MarketInfo marketInfo) {
        this.infos.add(marketInfo);
        marketInfo.setMarket(this);
        return this;
    }

    public Market removeInfo(MarketInfo marketInfo) {
        this.infos.remove(marketInfo);
        marketInfo.setMarket(null);
        return this;
    }

    public void setInfos(Set<MarketInfo> marketInfos) {
        this.infos = marketInfos;
    }

    public Set<ProductCategory> getCategories() {
        return categories;
    }

    public Market categories(Set<ProductCategory> productCategories) {
        this.categories = productCategories;
        return this;
    }

    public Market addCategory(ProductCategory productCategory) {
        this.categories.add(productCategory);
        productCategory.setMarket(this);
        return this;
    }

    public Market removeCategory(ProductCategory productCategory) {
        this.categories.remove(productCategory);
        productCategory.setMarket(null);
        return this;
    }

    public void setCategories(Set<ProductCategory> productCategories) {
        this.categories = productCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Market market = (Market) o;
        if (market.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), market.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Market{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
