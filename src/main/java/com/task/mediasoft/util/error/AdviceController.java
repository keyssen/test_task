package com.task.mediasoft.util.error;


import com.task.mediasoft.order.exception.OrderFailedCreateException;
import com.task.mediasoft.order.exception.OrderForbiddenCustomerException;
import com.task.mediasoft.product.exception.ErrorDetails;
import com.task.mediasoft.product.exception.ProductNotFoundExceptionByArticle;
import com.task.mediasoft.product.exception.ProductNotFoundExceptionById;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, являющийся глобальным обработчиком исключений
 */
@ControllerAdvice(annotations = RestController.class)
public class AdviceController {

    /**
     * Обработчик исключений ProductNotFoundExceptionById и ProductNotFoundExceptionByArticle.
     * Возвращает ответ с сообщением об ошибке и статусом NOT_FOUND.
     *
     * @param e Исключение, которое необходимо обработать.
     * @return ResponseEntity с сообщением об ошибке и статусом NOT_FOUND.
     */
    @ExceptionHandler({ProductNotFoundExceptionById.class, ProductNotFoundExceptionByArticle.class})
    public ResponseEntity<Object> handleProductNotFoundExceptionById(RuntimeException e) {
        final ErrorDetails errorDetails = new ErrorDetails(e.getStackTrace()[0].getClassName(), e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler({OrderFailedCreateException.class})
    public ResponseEntity<Object> handleOrderSaveException(RuntimeException e) {
        final ErrorDetails errorDetails = new ErrorDetails(e.getStackTrace()[0].getClassName(), e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler({OrderForbiddenCustomerException.class})
    public ResponseEntity<Object> handleOrderForbiddenException(RuntimeException e) {
        final ErrorDetails errorDetails = new ErrorDetails(e.getStackTrace()[0].getClassName(), e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
    }

    /**
     * Обработчик исключения MethodArgumentNotValidException при автоматической валидации данных.
     * Возвращает ответ с сообщением об ошибке валидации и статусом BAD_REQUEST.
     *
     * @param e Исключение MethodArgumentNotValidException, которое необходимо обработать.
     * @return ResponseEntity с сообщением об ошибке валидации и статусом BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleBindException(MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final String errorMessages = fieldErrors.stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).collect(Collectors.joining("; "));

        final ErrorDetails errorDetails = new ErrorDetails(e.getObjectName(), e.getClass().getSimpleName(), errorMessages, LocalDateTime.now());
        e.printStackTrace();
        return ResponseEntity.badRequest().body(errorDetails);
    }


    /**
     * Обработчик неизвестных исключений.
     * Возвращает ответ с сообщением об ошибке и статусом INTERNAL_SERVER_ERROR.
     * Также выводит стек трейс исключения в консоль.
     *
     * @param e Исключение, которое необходимо обработать.
     * @return ResponseEntity с сообщением об ошибке и статусом INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Throwable e) {
        final ErrorDetails errorDetails = new ErrorDetails(e.getStackTrace()[0].getClassName(), e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(errorDetails);
    }
}