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
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.sne.market.domain.enumeration.OrderStatus;

/**
 * Order entity
 * 
 * An order can contain many items, and an MarketProduct can appear in many different orders
 * Each Order is associated with one or more OrderLines.
 * Each OrderLine is associated with one and only one Order.
 * Each OrderLine is associated with one and only one Product.
 * Each Product is associated with zero or more OrderLines.
 */
@ApiModel(description = "Order entity An order can contain many items, and an MarketProduct can appear in many different orders Each Order is associated with one or more OrderLines. Each OrderLine is associated with one and only one Order. Each OrderLine is associated with one and only one Product. Each Product is associated with zero or more OrderLines.")
@Entity
@Table(name = "market_orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marketorders")
public class MarketOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private ZonedDateTime orderDate;

    @NotNull
    @Column(name = "shipdate", nullable = false)
    private LocalDate shipdate;

    @Column(name = "total_price", precision=10, scale=2)
    private BigDecimal totalPrice;

    @Column(name = "discount")
    private String discount;

    @Column(name = "weight", precision=10, scale=2)
    private BigDecimal weight;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ordertatus", nullable = false)
    private OrderStatus ordertatus;

    @Column(name = "remote_virtual_card_id")
    private String remoteVirtualCardId;

    @Column(name = "market_order_id")
    private String marketOrderId;

    @OneToOne
    @JoinColumn(unique = true)
    private Payment payment;

    @OneToOne
    @JoinColumn(unique = true)
    private Shipments shipper;

    @OneToMany(mappedBy = "marketOrders")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MarketOrderline> quantities = new HashSet<>();

    @ManyToOne
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public MarketOrders orderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getShipdate() {
        return shipdate;
    }

    public MarketOrders shipdate(LocalDate shipdate) {
        this.shipdate = shipdate;
        return this;
    }

    public void setShipdate(LocalDate shipdate) {
        this.shipdate = shipdate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public MarketOrders totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public MarketOrders discount(String discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public MarketOrders weight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public MarketOrders trackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public OrderStatus getOrdertatus() {
        return ordertatus;
    }

    public MarketOrders ordertatus(OrderStatus ordertatus) {
        this.ordertatus = ordertatus;
        return this;
    }

    public void setOrdertatus(OrderStatus ordertatus) {
        this.ordertatus = ordertatus;
    }

    public String getRemoteVirtualCardId() {
        return remoteVirtualCardId;
    }

    public MarketOrders remoteVirtualCardId(String remoteVirtualCardId) {
        this.remoteVirtualCardId = remoteVirtualCardId;
        return this;
    }

    public void setRemoteVirtualCardId(String remoteVirtualCardId) {
        this.remoteVirtualCardId = remoteVirtualCardId;
    }

    public String getMarketOrderId() {
        return marketOrderId;
    }

    public MarketOrders marketOrderId(String marketOrderId) {
        this.marketOrderId = marketOrderId;
        return this;
    }

    public void setMarketOrderId(String marketOrderId) {
        this.marketOrderId = marketOrderId;
    }

    public Payment getPayment() {
        return payment;
    }

    public MarketOrders payment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Shipments getShipper() {
        return shipper;
    }

    public MarketOrders shipper(Shipments shipments) {
        this.shipper = shipments;
        return this;
    }

    public void setShipper(Shipments shipments) {
        this.shipper = shipments;
    }

    public Set<MarketOrderline> getQuantities() {
        return quantities;
    }

    public MarketOrders quantities(Set<MarketOrderline> marketOrderlines) {
        this.quantities = marketOrderlines;
        return this;
    }

    public MarketOrders addQuantity(MarketOrderline marketOrderline) {
        this.quantities.add(marketOrderline);
        marketOrderline.setMarketOrders(this);
        return this;
    }

    public MarketOrders removeQuantity(MarketOrderline marketOrderline) {
        this.quantities.remove(marketOrderline);
        marketOrderline.setMarketOrders(null);
        return this;
    }

    public void setQuantities(Set<MarketOrderline> marketOrderlines) {
        this.quantities = marketOrderlines;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MarketOrders customer(Customer customer) {
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
        MarketOrders marketOrders = (MarketOrders) o;
        if (marketOrders.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrders{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", shipdate='" + getShipdate() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            ", discount='" + getDiscount() + "'" +
            ", weight='" + getWeight() + "'" +
            ", trackingNumber='" + getTrackingNumber() + "'" +
            ", ordertatus='" + getOrdertatus() + "'" +
            ", remoteVirtualCardId='" + getRemoteVirtualCardId() + "'" +
            ", marketOrderId='" + getMarketOrderId() + "'" +
            "}";
    }
}
