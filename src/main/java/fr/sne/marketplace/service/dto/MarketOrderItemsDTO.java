package fr.sne.marketplace.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the MarketOrderItems entity.
 */
public class MarketOrderItemsDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String description;

    private BigDecimal price;

    @Lob
    private byte[] img;
    private String imgContentType;

    private String name;

    private Long marketOrderId;

    private Long orderId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMarketOrderId() {
        return marketOrderId;
    }

    public void setMarketOrderId(Long marketOrderId) {
        this.marketOrderId = marketOrderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long marketOrderId) {
        this.orderId = marketOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketOrderItemsDTO marketOrderItemsDTO = (MarketOrderItemsDTO) o;
        if(marketOrderItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrderItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrderItemsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", price='" + getPrice() + "'" +
            ", img='" + getImg() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
