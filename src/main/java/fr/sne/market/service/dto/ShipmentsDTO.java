package fr.sne.market.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Shipments entity.
 */
public class ShipmentsDTO implements Serializable {

    private Long id;

    private String shipperType;

    private String companyName;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipperType() {
        return shipperType;
    }

    public void setShipperType(String shipperType) {
        this.shipperType = shipperType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipmentsDTO shipmentsDTO = (ShipmentsDTO) o;
        if(shipmentsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentsDTO{" +
            "id=" + getId() +
            ", shipperType='" + getShipperType() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
