package com.task.mediasoft.order.model;

import com.task.mediasoft.customer.model.Customer;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.orderProduct.model.OrderProduct;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Сущность для хранения информации о заказе.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    /**
     * Идентификатор заказа.
     */
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private UUID id;

    /**
     * Клиент, сделавший заказ.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    /**
     * Статус заказа.
     */
    @NonNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /**
     * Адрес доставки заказа.
     */
    @NonNull
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    /**
     * Продукты, входящие в заказ.
     */
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    /**
     * Создает новый объект заказа на основе данных из DTO.
     *
     * @param saveOrderDTO DTO с данными о заказе.
     */
    public Order(SaveOrderDTO saveOrderDTO) {
        this.deliveryAddress = saveOrderDTO.getDeliveryAddress();
    }

    /**
     * Добавляет продукт к заказу.
     *
     * @param orderProduct Продукт для добавления к заказу.
     */
    public void addToOrderProducts(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getCustomer().getId(), order.getCustomer().getId()) && getStatus() == order.getStatus() && Objects.equals(getDeliveryAddress(), order.getDeliveryAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomer().getId(), getStatus(), getDeliveryAddress());
    }
}