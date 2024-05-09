package com.task.mediasoft.order.controller;

import com.task.mediasoft.order.model.dto.ChangeStatusDTO;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import com.task.mediasoft.order.model.dto.ViewOrderWithProductDTO;
import com.task.mediasoft.order.service.OrderService;
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

    private final OrderService orderService;

    /**
     * Получить информацию о заказе по его идентификатору.
     *
     * @param id Идентификатор заказа.
     * @return Объект ViewOrderWithProductDTO с информацией о заказе и его продуктах.
     */
    @GetMapping("/{id}")
    public ViewOrderWithProductDTO getOrderById(@PathVariable UUID id) {
        return new ViewOrderWithProductDTO(id, orderService.getAllViewProductsByOrderId(id));
    }


    /**
     * Создать новый заказ.
     *
     * @param orderDTO Объект SaveOrderDTO с данными нового заказа.
     * @return Объект ViewOrderWithProductDTO с информацией о созданном заказе и его продуктах.
     */
    @PostMapping
    public ViewOrderWithProductDTO createOrder(@Valid @RequestBody SaveOrderDTO orderDTO) {
        return new ViewOrderWithProductDTO(orderService.createOrder(orderDTO));
    }

    /**
     * Подтвердить заказ.
     *
     * @param orderId Идентификатор заказа, который нужно подтвердить.
     * @return Объект ViewOrderWithProductDTO с информацией о заказе и его продуктах после подтверждения.
     */
    @PostMapping("/{orderId}/confirm")
    public ViewOrderWithProductDTO confirmOrder(@PathVariable UUID orderId) {
        // todo
        return null;
    }

    /**
     * Обновить информацию о заказе.
     *
     * @param products Список объектов SaveOrderProductDTO с данными о продуктах заказа.
     * @param orderId  Идентификатор заказа, который нужно обновить.
     * @return Объект ViewOrderWithProductDTO с обновленной информацией о заказе и его продуктах.
     */
    @PatchMapping("/{orderId}")
    public ViewOrderWithProductDTO updateOrder(@Valid @RequestBody List<SaveOrderProductDTO> products, @PathVariable UUID orderId) {
        return new ViewOrderWithProductDTO(orderService.updateOrder(products, orderId));
    }

    /**
     * Изменить статус заказа.
     *
     * @param changeStatusDTO Объект ChangeStatusDTO с новым статусом заказа.
     * @param orderId         Идентификатор заказа, статус которого нужно изменить.
     * @return Объект ViewOrderWithProductDTO с обновленной информацией о заказе и его продуктах.
     */
    @PatchMapping("/{orderId}/status")
    public ViewOrderWithProductDTO changeOrderStatus(@Valid @RequestBody ChangeStatusDTO changeStatusDTO, @PathVariable UUID orderId) {
        return new ViewOrderWithProductDTO(orderService.changeOrderStatus(orderId, changeStatusDTO));
    }

    /**
     * Удалить заказ по его идентификатору.
     *
     * @param id Идентификатор заказа, который нужно удалить.
     * @return Объект ViewOrderWithProductDTO с информацией о удаленном заказе и его продуктах.
     */
    @DeleteMapping("/{id}")
    public ViewOrderWithProductDTO deleteOrderById(@PathVariable UUID id) {
        return new ViewOrderWithProductDTO(orderService.deleteOrder(id));
    }
}
