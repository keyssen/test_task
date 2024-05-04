package com.task.mediasoft.order.service;


import com.task.mediasoft.order.exception.OrderFailedCreateException;
import com.task.mediasoft.order.exception.OrderForbiddenCustomerException;
import com.task.mediasoft.order.exception.OrderNotFoundExceptionById;
import com.task.mediasoft.order.exception.OrderStatusException;
import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.order.model.OrderStatus;
import com.task.mediasoft.order.model.dto.ChangeStatusDTO;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import com.task.mediasoft.order.repository.OrderRepository;
import com.task.mediasoft.orderProduct.model.OrderProduct;
import com.task.mediasoft.orderProduct.repository.OrderProductRepository;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.service.ProductService;
import com.task.mediasoft.session.CustomerIdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;
    private final CustomerIdProvider customerIdProvider;

    @Transactional(readOnly = true)
    public Order getOrderById(UUID id) {
        final Order currentOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundExceptionById(id));
        if (!currentOrder.getCustomerId().equals(customerIdProvider.getCustomerId()))
            throw new OrderForbiddenCustomerException(customerIdProvider.getCustomerId());
        return currentOrder;
    }

    @Transactional
    public Order createOrder(SaveOrderDTO createOrderDTO) {
        final Order currentOrder = new Order(createOrderDTO);
        currentOrder.setStatus(OrderStatus.CREATED);
        currentOrder.setCustomerId(customerIdProvider.getCustomerId());
        orderRepository.save(currentOrder);
        createOrderDTO.getProducts().stream().
                collect(Collectors.groupingBy(
                        SaveOrderProductDTO::getId,
                        Collectors.summingLong(SaveOrderProductDTO::getQuantity)
                )).forEach((productId, totalQuantity) ->
                        addOrderToProduct(currentOrder, productId, totalQuantity)
                );
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateOrder(List<SaveOrderProductDTO> products, UUID orderId) {
        final Order currentOrder = getOrderById(orderId);
        if (currentOrder.getStatus() != OrderStatus.CREATED) {
            throw new OrderStatusException(OrderStatus.CREATED);
        } else if (!currentOrder.getCustomerId().equals(customerIdProvider.getCustomerId())) {
            throw new OrderForbiddenCustomerException(customerIdProvider.getCustomerId());
        }
        products.stream().
                collect(Collectors.groupingBy(
                        SaveOrderProductDTO::getId,
                        Collectors.summingLong(SaveOrderProductDTO::getQuantity)
                )).forEach((productId, totalQuantity) -> {
                    final OrderProduct currentOrderProduct = orderProductRepository.findOrderProduct(currentOrder.getId(), productId);
                    if (currentOrderProduct == null) {
                        addOrderToProduct(currentOrder, productId, totalQuantity);
                    } else {
                        final Product currentProduct = productService.getProductById(productId);
                        if (currentProduct.getQuantity() >= totalQuantity && currentProduct.getIsAvailable()) {
                            currentOrderProduct.setQuantity(currentOrderProduct.getQuantity() + totalQuantity);
                            currentOrderProduct.setFrozenPrice(currentProduct.getPrice());
                            currentProduct.setQuantity(currentProduct.getQuantity() - totalQuantity);
                            orderProductRepository.save(currentOrderProduct);
                        } else {
                            throw new OrderFailedCreateException(productId, currentProduct.getIsAvailable(), currentProduct.getQuantity(), totalQuantity);
                        }
                    }
                });
        return orderRepository.save(currentOrder);


    }

    public void addOrderToProduct(Order order, UUID productId, Long totalQuantity) {
        final Product currentProduct = productService.getProductById(productId);
        if (currentProduct.getQuantity() >= totalQuantity && currentProduct.getIsAvailable()) {
            currentProduct.setQuantity(currentProduct.getQuantity() - totalQuantity);
        } else {
            throw new OrderFailedCreateException(productId, currentProduct.getIsAvailable(), currentProduct.getQuantity(), totalQuantity);
        }
        orderProductRepository.save(new OrderProduct(order, currentProduct, currentProduct.getPrice(), totalQuantity));
    }

    @Transactional
    public Order changeOrderStatus(UUID id, ChangeStatusDTO changeStatusDTO) {
        final Order currentOrder = getOrderById(id);
        OrderStatus currentOrderStatus = currentOrder.getStatus();
        if (currentOrderStatus == OrderStatus.CANCELLED || currentOrderStatus == OrderStatus.DONE || currentOrderStatus == OrderStatus.REJECTED || currentOrderStatus.compareTo(changeStatusDTO.getStatus()) > 0) {
            throw new OrderStatusException();
        } else if (!currentOrder.getCustomerId().equals(customerIdProvider.getCustomerId())) {
            throw new OrderForbiddenCustomerException(customerIdProvider.getCustomerId());
        }
        if (changeStatusDTO.getStatus() == OrderStatus.CANCELLED && currentOrderStatus != OrderStatus.CREATED) {
            throw new OrderStatusException(OrderStatus.CREATED);
        }
        currentOrder.setStatus(changeStatusDTO.getStatus());
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order deleteOrder(UUID id) {
        final Order currentOrder = getOrderById(id);
        if (currentOrder.getStatus() != OrderStatus.CREATED) {
            throw new OrderStatusException(OrderStatus.CREATED);
        } else if (!currentOrder.getCustomerId().equals(customerIdProvider.getCustomerId())) {
            throw new OrderForbiddenCustomerException(customerIdProvider.getCustomerId());
        }
        currentOrder.setStatus(OrderStatus.CANCELLED);
        currentOrder.getOrderProducts().forEach(orderProduct -> {
            final Product currentProduct = orderProduct.getProduct();
            currentProduct.setQuantity(orderProduct.getQuantity() + currentProduct.getQuantity());
        });
        return orderRepository.save(currentOrder);
    }
}
