package fr.sne.market.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MarketItemAttributes entity.
 */
public class MarketItemAttributesDTO implements Serializable {

    private Long id;

    private String title;

    private String label;

    private String isbn;

    private String ean;

    private String brand;

    private String sku;

    private String size;

    private String productGroup;

    private String model;

    private String manufacturer;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketItemAttributesDTO marketItemAttributesDTO = (MarketItemAttributesDTO) o;
        if(marketItemAttributesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketItemAttributesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketItemAttributesDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", label='" + getLabel() + "'" +
            ", isbn='" + getIsbn() + "'" +
            ", ean='" + getEan() + "'" +
            ", brand='" + getBrand() + "'" +
            ", sku='" + getSku() + "'" +
            ", size='" + getSize() + "'" +
            ", productGroup='" + getProductGroup() + "'" +
            ", model='" + getModel() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            "}";
    }
}
