package com.task.mediasoft.util.error;


import com.task.mediasoft.product.exception.ProductNotFoundExceptionByArticle;
import com.task.mediasoft.product.exception.ProductNotFoundExceptionById;
import com.task.mediasoft.util.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> handleException(Throwable e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
        final ValidationException validationException = new ValidationException(
                e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toSet()));

        return new ResponseEntity<>(validationException.getMessage(), HttpStatus.BAD_REQUEST);
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
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}