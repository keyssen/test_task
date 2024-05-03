package com.task.mediasoft.order.service;


import com.task.mediasoft.order.exception.OrderFailedCreateException;
import com.task.mediasoft.order.exception.OrderNotFoundExceptionById;
import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.order.model.OrderStatus;
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
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundExceptionById(id));
    }


    public OrderProduct getOrderProductById(UUID productId, UUID orderId) {
        OrderProduct orderProduct = orderProductRepository.findOrderProduct(orderId, productId);
        return orderProduct;
    }


    @Transactional
    public Order createOrder(SaveOrderDTO createOrderDTO) {
        final Order order = new Order(createOrderDTO);
        order.setStatus(OrderStatus.CREATED);
        order.setCustomerId(customerIdProvider.getCustomerId());
        orderRepository.save(order);
        createOrderDTO.getProducts().stream().
                collect(Collectors.groupingBy(
                        SaveOrderProductDTO::getId,
                        Collectors.summingLong(SaveOrderProductDTO::getQuantity)
                )).forEach((productId, totalQuantity) -> {
                    addOrderToProduct(order, productId, totalQuantity);
                });
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(List<SaveOrderProductDTO> products, UUID orderId) {
        final Order order = getOrderById(orderId);
        if (order.getStatus() == OrderStatus.CREATED) {
            products.stream().
                    collect(Collectors.groupingBy(
                            SaveOrderProductDTO::getId,
                            Collectors.summingLong(SaveOrderProductDTO::getQuantity)
                    )).forEach((productId, totalQuantity) -> {
                        final OrderProduct orderProduct = orderProductRepository.findOrderProduct(order.getId(), productId);
                        if (orderProduct == null) {
                            addOrderToProduct(order, productId, totalQuantity);
                        } else {
                            final Product product = productService.getProductById(productId);
                            if (product.getQuantity() > totalQuantity && product.getIsAvailable()) {
                                orderProduct.setQuantity(orderProduct.getQuantity() + totalQuantity);
                                product.setQuantity(product.getQuantity() - totalQuantity);
                                orderProductRepository.save(orderProduct);
                            } else {
                                throw new OrderFailedCreateException(productId, product.getIsAvailable(), product.getQuantity(), totalQuantity);
                            }
                        }
                    });
            return orderRepository.save(order);
        }
        throw new OrderFailedCreateException();
    }

    private void addOrderToProduct(Order order, UUID productId, Long totalQuantity) {
        final Product product = productService.getProductById(productId);
        if (product.getQuantity() > totalQuantity && product.getIsAvailable()) {
            product.setQuantity(product.getQuantity() - totalQuantity);
        } else {
            throw new OrderFailedCreateException(productId, product.getIsAvailable(), product.getQuantity(), totalQuantity);
        }
        orderProductRepository.save(new OrderProduct(order, product, product.getPrice(), totalQuantity));
    }

}
