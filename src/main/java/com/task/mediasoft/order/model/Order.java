package com.task.mediasoft.order.model;

import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.orderProduct.model.OrderProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NonNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NonNull
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @NonNull
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderProducts;

    public Order(SaveOrderDTO saveOrderDTO) {
        this.customerId = saveOrderDTO.getCustomerId();
        this.deliveryAddress = saveOrderDTO.getDeliveryAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getCustomerId(), order.getCustomerId()) && getStatus() == order.getStatus() && Objects.equals(getDeliveryAddress(), order.getDeliveryAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getStatus(), getDeliveryAddress());
    }
}
