package com.task.mediasoft.order.controller;

import com.task.mediasoft.order.model.dto.ChangeStatusDTO;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import com.task.mediasoft.order.model.dto.ViewOrderWithProductDTO;
import com.task.mediasoft.order.service.OrderServiceImpl;
import com.task.mediasoft.session.CustomerIdProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с заказами.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    private final CustomerIdProvider customerIdProvider;

    /**
     * Получить информацию о заказе по его идентификатору.
     *
     * @param id Идентификатор заказа.
     * @return Объект ViewOrderWithProductDTO с информацией о заказе и его продуктах.
     */
    @GetMapping("/{id}")
    public ViewOrderWithProductDTO getOrderById(@PathVariable UUID id) {
        return orderService.getViewOrderWithProductDTO(id, customerIdProvider.getCustomerId());
    }


    /**
     * Создать новый заказ.
     *
     * @param orderDTO Объект SaveOrderDTO с данными нового заказа.
     * @return Объект ViewOrderWithProductDTO с информацией о созданном заказе и его продуктах.
     */
    @PostMapping
    public UUID createOrder(@Valid @RequestBody SaveOrderDTO orderDTO) {
        return orderService.createOrder(orderDTO, customerIdProvider.getCustomerId()).getId();
    }

    /**
     * Подтвердить заказ.
     *
     * @param orderId Идентификатор заказа, который нужно подтвердить.
     * @return Объект ViewOrderWithProductDTO с информацией о заказе и его продуктах после подтверждения.
     */
    @PostMapping("/{orderId}/confirm")
    public void confirmOrder(@PathVariable UUID orderId) {
        // todo
    }

    /**
     * Обновить информацию о заказе.
     *
     * @param products Список объектов SaveOrderProductDTO с данными о продуктах заказа.
     * @param orderId  Идентификатор заказа, который нужно обновить.
     * @return Объект ViewOrderWithProductDTO с обновленной информацией о заказе и его продуктах.
     */
    @PatchMapping("/{orderId}")
    public UUID updateOrder(@Valid @RequestBody List<SaveOrderProductDTO> products, @PathVariable UUID orderId) {
        return orderService.updateOrder(products, orderId, customerIdProvider.getCustomerId()).getId();
    }

    /**
     * Изменить статус заказа.
     *
     * @param changeStatusDTO Объект ChangeStatusDTO с новым статусом заказа.
     * @param orderId         Идентификатор заказа, статус которого нужно изменить.
     * @return Объект ViewOrderWithProductDTO с обновленной информацией о заказе и его продуктах.
     */
    @PatchMapping("/{orderId}/status")
    public void changeOrderStatus(@Valid @RequestBody ChangeStatusDTO changeStatusDTO, @PathVariable UUID orderId) {
        orderService.changeOrderStatus(orderId, changeStatusDTO);
    }

    /**
     * Удалить заказ по его идентификатору.
     *
     * @param id Идентификатор заказа, который нужно удалить.
     */
    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable UUID id) {
        orderService.deleteOrder(id, customerIdProvider.getCustomerId());
    }
}
