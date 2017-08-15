package fr.sne.market.service.dto;


import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import fr.sne.market.domain.enumeration.OrderStatus;

/**
 * A DTO for the MarketOrders entity.
 */
public class MarketOrdersDTO implements Serializable {

    private Long id;

    private ZonedDateTime orderDate;

    @NotNull
    private LocalDate shipdate;

    private BigDecimal totalPrice;

    private String discount;

    private BigDecimal weight;

    private String trackingNumber;

    @NotNull
    private OrderStatus ordertatus;

    private String remoteVirtualCardId;

    private String marketOrderId;

    private Long paymentId;

    private Long shipperId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getShipdate() {
        return shipdate;
    }

    public void setShipdate(LocalDate shipdate) {
        this.shipdate = shipdate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public OrderStatus getOrdertatus() {
        return ordertatus;
    }

    public void setOrdertatus(OrderStatus ordertatus) {
        this.ordertatus = ordertatus;
    }

    public String getRemoteVirtualCardId() {
        return remoteVirtualCardId;
    }

    public void setRemoteVirtualCardId(String remoteVirtualCardId) {
        this.remoteVirtualCardId = remoteVirtualCardId;
    }

    public String getMarketOrderId() {
        return marketOrderId;
    }

    public void setMarketOrderId(String marketOrderId) {
        this.marketOrderId = marketOrderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getShipperId() {
        return shipperId;
    }

    public void setShipperId(Long shipmentsId) {
        this.shipperId = shipmentsId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketOrdersDTO marketOrdersDTO = (MarketOrdersDTO) o;
        if(marketOrdersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrdersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrdersDTO{" +
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
