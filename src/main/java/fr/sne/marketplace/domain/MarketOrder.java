package fr.sne.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.sne.marketplace.domain.enumeration.Status;

/**
 * Order entity
 */
@ApiModel(description = "Order entity")
@Entity
@Table(name = "market_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marketorder")
public class MarketOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "remote_virtual_card_id")
    private String remoteVirtualCardId;

    @Column(name = "market_order_id")
    private String marketOrderId;

    @OneToOne
    @JoinColumn(unique = true)
    private Payment payment;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MarketOrderItems> items = new HashSet<>();

    @OneToMany(mappedBy = "marketOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MarketOrderItems> quantities = new HashSet<>();

    @ManyToOne
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public MarketOrder orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public MarketOrder date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public MarketOrder status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemoteVirtualCardId() {
        return remoteVirtualCardId;
    }

    public MarketOrder remoteVirtualCardId(String remoteVirtualCardId) {
        this.remoteVirtualCardId = remoteVirtualCardId;
        return this;
    }

    public void setRemoteVirtualCardId(String remoteVirtualCardId) {
        this.remoteVirtualCardId = remoteVirtualCardId;
    }

    public String getMarketOrderId() {
        return marketOrderId;
    }

    public MarketOrder marketOrderId(String marketOrderId) {
        this.marketOrderId = marketOrderId;
        return this;
    }

    public void setMarketOrderId(String marketOrderId) {
        this.marketOrderId = marketOrderId;
    }

    public Payment getPayment() {
        return payment;
    }

    public MarketOrder payment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<MarketOrderItems> getItems() {
        return items;
    }

    public MarketOrder items(Set<MarketOrderItems> marketOrderItems) {
        this.items = marketOrderItems;
        return this;
    }

    public MarketOrder addItems(MarketOrderItems marketOrderItems) {
        this.items.add(marketOrderItems);
        marketOrderItems.setOrder(this);
        return this;
    }

    public MarketOrder removeItems(MarketOrderItems marketOrderItems) {
        this.items.remove(marketOrderItems);
        marketOrderItems.setOrder(null);
        return this;
    }

    public void setItems(Set<MarketOrderItems> marketOrderItems) {
        this.items = marketOrderItems;
    }

    public Set<MarketOrderItems> getQuantities() {
        return quantities;
    }

    public MarketOrder quantities(Set<MarketOrderItems> marketOrderItems) {
        this.quantities = marketOrderItems;
        return this;
    }

    public MarketOrder addQuantity(MarketOrderItems marketOrderItems) {
        this.quantities.add(marketOrderItems);
        marketOrderItems.setMarketOrder(this);
        return this;
    }

    public MarketOrder removeQuantity(MarketOrderItems marketOrderItems) {
        this.quantities.remove(marketOrderItems);
        marketOrderItems.setMarketOrder(null);
        return this;
    }

    public void setQuantities(Set<MarketOrderItems> marketOrderItems) {
        this.quantities = marketOrderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MarketOrder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketOrder marketOrder = (MarketOrder) o;
        if (marketOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrder{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", remoteVirtualCardId='" + getRemoteVirtualCardId() + "'" +
            ", marketOrderId='" + getMarketOrderId() + "'" +
            "}";
    }
}
