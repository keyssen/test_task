package com.task.mediasoft.session.filter;

import com.task.mediasoft.session.CurrencyEnum;
import com.task.mediasoft.session.CurrencyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Фильтр, который извлекает информацию о валюте из заголовка запроса и устанавливает её провайдеру валют.
 * Реализует интерфейс OncePerRequestFilter, чтобы гарантировать, что метод doFilterInternal будет вызываться только один раз на каждый запрос.
 */
@Component
@AllArgsConstructor
public class CurrencyFilter extends OncePerRequestFilter {

    private final CurrencyProvider currencyProvider;

    /**
     * Извлекает информацию о валюте из заголовка запроса и устанавливает её провайдеру валют.
     *
     * @param request     Запрос, который необходимо обработать.
     * @param response    Ответ, который будет отправлен клиенту.
     * @param filterChain Цепочка фильтров для продолжения обработки запроса.
     * @throws ServletException Если возникает ошибка во время обработки запроса.
     * @throws IOException      Если возникает ошибка ввода-вывода во время обработки запроса.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader("currency"))
                .map(CurrencyEnum::fromCode)
                .ifPresent(currencyProvider::setCurrency);
        filterChain.doFilter(request, response);
    }
}
