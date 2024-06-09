package com.task.mediasoft.order.service;


import com.task.mediasoft.customer.model.Customer;
import com.task.mediasoft.customer.service.CustomerService;
import com.task.mediasoft.order.exception.OrderFailedCreateException;
import com.task.mediasoft.order.exception.OrderForbiddenCustomerException;
import com.task.mediasoft.order.exception.OrderNotFoundExceptionById;
import com.task.mediasoft.order.exception.OrderStatusException;
import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.order.model.OrderStatus;
import com.task.mediasoft.order.model.dto.ChangeStatusDTO;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import com.task.mediasoft.order.model.dto.ViewOrderWithProductDTO;
import com.task.mediasoft.order.model.dto.ViewProductFromOrderDTO;
import com.task.mediasoft.order.repository.OrderRepository;
import com.task.mediasoft.orderProduct.model.OrderProduct;
import com.task.mediasoft.orderProduct.repository.OrderProductRepository;
import com.task.mediasoft.product.controller.model.CustomerInfo;
import com.task.mediasoft.product.controller.model.OrderInfo;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.service.ProductService;
import com.task.mediasoft.restService.accountService.AccountService;
import com.task.mediasoft.restService.crmService.CrmService;
import com.task.mediasoft.session.CustomerIdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Сервисный класс, отвечающий за обработку операций, связанных с заказами.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final AccountService accountService;
    private final CrmService crmService;
    private final CustomerIdProvider customerIdProvider;

    /**
     * Получает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа.
     * @return Заказ, соответствующий указанному идентификатору.
     * @throws OrderNotFoundExceptionById      Если заказ с указанным идентификатором не найден.
     * @throws OrderForbiddenCustomerException Если текущий клиент не имеет доступа к заказу.
     */
    private Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundExceptionById(id));
    }

    /**
     * Получает заказ по его идентификатору.
     *
     * @param id Идентификатор заказа.
     * @return Заказ, соответствующий указанному идентификатору.
     * @throws OrderNotFoundExceptionById      Если заказ с указанным идентификатором не найден.
     * @throws OrderForbiddenCustomerException Если текущий клиент не имеет доступа к заказу.
     */
    @Transactional(readOnly = true)
    public ViewOrderWithProductDTO getViewOrderWithProductDTO(UUID id) {
        if (!orderRepository.existsByIdAndCustomer_Id(id, customerIdProvider.getCustomerId()))
            throw new OrderForbiddenCustomerException(customerIdProvider.getCustomerId());
        final List<ViewProductFromOrderDTO> products = orderProductRepository.findAllViewProductsByOrderId(id);
        final BigDecimal totalPrice = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ViewOrderWithProductDTO(id, products, totalPrice);
    }

    /**
     * Получает информацию о продуктах из базы данных.
     *
     * @return Карта, где ключом является UUID продукта, а значением - список информации о заказах, содержащих этот продукт.
     */
    @Transactional(readOnly = true)
    public Map<UUID, List<OrderInfo>> getProductsInfo() {
        List<String> logins = customerService.getAllCustomer().stream()
                .map(Customer::getLogin)
                .toList();

        CompletableFuture<Map<String, String>> accountNumbersFuture = accountService.getAccounts(logins);
        CompletableFuture<Map<String, String>> innsFuture = crmService.getInns(logins);

        return orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == OrderStatus.CREATED || order.getStatus() == OrderStatus.CONFIRMED)
                .flatMap(order -> order.getOrderProducts().stream())
                .collect(Collectors.groupingBy(OrderProduct::getProduct))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getId(),
                        entry -> entry.getValue().stream()
                                .map(orderProduct -> {
                                    Order order = orderProduct.getOrder();
                                    Customer customer = order.getCustomer();
                                    CustomerInfo customerInfo = new CustomerInfo(
                                            customer.getId(),
                                            accountNumbersFuture.join().get(customer.getLogin()),
                                            customer.getEmail(),
                                            innsFuture.join().get(customer.getLogin())
                                    );
                                    return new OrderInfo(
                                            order.getId(),
                                            customerInfo,
                                            order.getStatus(),
                                            order.getDeliveryAddress(),
                                            orderProduct.getQuantity()
                                    );
                                })
                                .toList()
                ));
    }

    /**
     * Создает новый заказ на основе переданных данных.
     *
     * @param createOrderDTO Данные о заказе.
     * @return Созданный заказ.
     */
    @Transactional
    public Order createOrder(SaveOrderDTO createOrderDTO) {
        final Order currentOrder = new Order();
        currentOrder.setDeliveryAddress(createOrderDTO.getDeliveryAddress());
        currentOrder.setStatus(OrderStatus.CREATED);
        currentOrder.setCustomer(customerService.getCustomerById(customerIdProvider.getCustomerId()));
        final List<OrderProduct> currentOrderProductList = new ArrayList<>();
        Map<UUID, Long> idToQuantityProductMap = createOrderDTO.getProducts().stream()
                .collect(Collectors.groupingBy(
                        SaveOrderProductDTO::getId,
                        Collectors.summingLong(SaveOrderProductDTO::getQuantity)));

        Map<UUID, Product> idToProductMap = productService.getProductsByIdIn(idToQuantityProductMap.keySet()).stream().collect(Collectors.toMap(Product::getId, product -> product));
        idToQuantityProductMap.forEach((productId, totalQuantity) ->
                currentOrderProductList.add((createOrderProduct(currentOrder, productId, totalQuantity, idToProductMap.get(productId))))
        );
        currentOrder.setOrderProducts(currentOrderProductList);
        return orderRepository.save(currentOrder);
    }

    /**
     * Обновляет существующий заказ на основе переданных данных о продуктах.
     *
     * @param products Список данных о продуктах для обновления заказа.
     * @param orderId  Идентификатор заказа, который нужно обновить.
     * @return Обновленный заказ.
     * @throws OrderStatusException            Если статус заказа не позволяет его обновление.
     * @throws OrderForbiddenCustomerException Если текущий клиент не имеет доступа к заказу.
     */
    @Transactional
    public Order updateOrder(List<SaveOrderProductDTO> products, UUID orderId) {
        final Order currentOrder = getOrderById(orderId);
        if (currentOrder.getStatus() != OrderStatus.CREATED) {
            throw new OrderStatusException(OrderStatus.CREATED);
        } else if (!currentOrder.getCustomer().getId().equals(customerIdProvider.getCustomerId())) {
            throw new OrderForbiddenCustomerException(customerIdProvider.getCustomerId());
        }
        final List<OrderProduct> currentOrderProductList = currentOrder.getOrderProducts();

        Map<UUID, OrderProduct> productIdToOrderProductMap = currentOrderProductList.stream().collect(Collectors.toMap(orderProduct -> orderProduct.getProduct().getId(), orderProduct -> orderProduct));

        Map<UUID, Long> idToQuantityProOductMap = products.stream()
                .collect(Collectors.groupingBy(
                        SaveOrderProductDTO::getId,
                        Collectors.summingLong(SaveOrderProductDTO::getQuantity)));

        Map<UUID, Product> idToProductMap = productService.getProductsByIdIn(idToQuantityProOductMap.keySet()).stream().collect(Collectors.toMap(Product::getId, product -> product));

        idToQuantityProOductMap.forEach((productId, totalQuantity) -> {
            final OrderProduct currentOrderProduct = productIdToOrderProductMap.get(productId);
            if (currentOrderProduct == null) {
                currentOrderProductList.add(createOrderProduct(currentOrder, productId, totalQuantity, idToProductMap.get(productId)));
            } else {
                final Product currentProduct = currentOrderProduct.getProduct();
                if (currentProduct.getQuantity() >= totalQuantity && currentProduct.getIsAvailable()) {
                    currentOrderProduct.setQuantity(currentOrderProduct.getQuantity() + totalQuantity);
                    currentOrderProduct.setFrozenPrice(currentProduct.getPrice());
                    currentProduct.setQuantity(currentProduct.getQuantity() - totalQuantity);
                } else {
                    throw new OrderFailedCreateException(productId, currentProduct.getIsAvailable(), currentProduct.getQuantity(), totalQuantity);
                }
            }
        });
        return orderRepository.save(currentOrder);
    }

    /**
     * Создает объект заказанного продукта на основе переданных данных.
     *
     * @param order         Заказ, к которому относится продукт.
     * @param productId     Идентификатор продукта.
     * @param totalQuantity Общее количество продукта в заказе.
     * @return Созданный объект заказанного продукта.
     * @throws OrderFailedCreateException Если создание заказанного продукта не удалось.
     */
    private OrderProduct createOrderProduct(Order order, UUID productId, Long totalQuantity, Product currentProduct) {
        if (currentProduct.getQuantity() >= totalQuantity && currentProduct.getIsAvailable()) {
            currentProduct.setQuantity(currentProduct.getQuantity() - totalQuantity);
        } else {
            throw new OrderFailedCreateException(productId, currentProduct.getIsAvailable(), currentProduct.getQuantity(), totalQuantity);
        }
        return new OrderProduct(order, currentProduct, currentProduct.getPrice(), totalQuantity);
    }

    /**
     * Изменяет статус существующего заказа.
     *
     * @param id              Идентификатор заказа, статус которого нужно изменить.
     * @param changeStatusDTO Данные для изменения статуса заказа.
     * @return Обновленный заказ.
     * @throws OrderForbiddenCustomerException Если текущий клиент не имеет доступа к заказу.
     * @throws OrderStatusException            Если текущий статус заказа не позволяет его изменение на заданный.
     */
    @Transactional
    public Order changeOrderStatus(UUID id, ChangeStatusDTO changeStatusDTO) {
        final Order currentOrder = getOrderById(id);
        OrderStatus currentOrderStatus = currentOrder.getStatus();
        if (currentOrderStatus == OrderStatus.CANCELLED || currentOrderStatus == OrderStatus.DONE || currentOrderStatus == OrderStatus.REJECTED || currentOrderStatus.compareTo(changeStatusDTO.getStatus()) > 0) {
            throw new OrderStatusException();
        }
        if (changeStatusDTO.getStatus() == OrderStatus.CANCELLED && currentOrderStatus != OrderStatus.CREATED) {
            throw new OrderStatusException(OrderStatus.CREATED);
        }
        currentOrder.setStatus(changeStatusDTO.getStatus());
        return orderRepository.save(currentOrder);
    }

    /**
     * Удаляет существующий заказ.
     *
     * @param id Идентификатор заказа, который нужно удалить.
     * @return Удаленный заказ.
     * @throws OrderStatusException            Если текущий статус заказа не позволяет его удаление.
     * @throws OrderForbiddenCustomerException Если текущий клиент не имеет доступа к заказу.
     */
    @Transactional
    public Order deleteOrder(UUID id) {
        final Order currentOrder = getOrderById(id);
        if (currentOrder.getStatus() != OrderStatus.CREATED) {
            throw new OrderStatusException(OrderStatus.CREATED);
        }
        if (!currentOrder.getCustomer().getId().equals(customerIdProvider.getCustomerId())) {
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
