package fr.sne.market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Shipments.
 */
@Entity
@Table(name = "shipments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shipments")
public class Shipments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipper_type")
    private String shipperType;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "phone")
    private String phone;

    @OneToOne(mappedBy = "shipper")
    @JsonIgnore
    private MarketOrders order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipperType() {
        return shipperType;
    }

    public Shipments shipperType(String shipperType) {
        this.shipperType = shipperType;
        return this;
    }

    public void setShipperType(String shipperType) {
        this.shipperType = shipperType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Shipments companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public Shipments phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MarketOrders getOrder() {
        return order;
    }

    public Shipments order(MarketOrders marketOrders) {
        this.order = marketOrders;
        return this;
    }

    public void setOrder(MarketOrders marketOrders) {
        this.order = marketOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shipments shipments = (Shipments) o;
        if (shipments.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipments.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shipments{" +
            "id=" + getId() +
            ", shipperType='" + getShipperType() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
