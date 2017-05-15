package fr.sne.marketplace.service.dto;


import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import fr.sne.marketplace.domain.enumeration.Status;

/**
 * A DTO for the MarketOrder entity.
 */
public class MarketOrderDTO implements Serializable {

    private Long id;

    private LocalDate orderDate;

    @NotNull
    private ZonedDateTime date;

    @NotNull
    private Status status;

    private String remoteVirtualCardId;

    private String marketOrderId;

    private Long paymentId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

        MarketOrderDTO marketOrderDTO = (MarketOrderDTO) o;
        if(marketOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOrderDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", remoteVirtualCardId='" + getRemoteVirtualCardId() + "'" +
            ", marketOrderId='" + getMarketOrderId() + "'" +
            "}";
    }
}
