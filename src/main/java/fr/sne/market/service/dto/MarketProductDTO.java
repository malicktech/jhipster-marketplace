package fr.sne.market.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the MarketProduct entity.
 */
public class MarketProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String description;

    private BigDecimal price;

    @NotNull
    private String category;

    @Lob
    private byte[] img;
    private String imgContentType;

    private Long marketOrderlineId;

    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Long getMarketOrderlineId() {
        return marketOrderlineId;
    }

    public void setMarketOrderlineId(Long marketOrderlineId) {
        this.marketOrderlineId = marketOrderlineId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long marketProductCategoryId) {
        this.categoryId = marketProductCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketProductDTO marketProductDTO = (MarketProductDTO) o;
        if(marketProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketProductDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", price='" + getPrice() + "'" +
            ", category='" + getCategory() + "'" +
            ", img='" + getImg() + "'" +
            "}";
    }
}
