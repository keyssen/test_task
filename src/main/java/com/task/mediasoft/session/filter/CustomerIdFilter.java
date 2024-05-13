package com.task.mediasoft.session.filter;

import com.task.mediasoft.session.CustomerIdProvider;
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
 * Фильтр для установки идентификатора клиента в запросе.
 */
@Component
@AllArgsConstructor
public class CustomerIdFilter extends OncePerRequestFilter {
    private final CustomerIdProvider customerIdProvider;

    /**
     * Выполняет фильтрацию запроса и устанавливает идентификатор клиента в соответствии с заголовком запроса.
     *
     * @param request     HTTP-запрос
     * @param response    HTTP-ответ
     * @param filterChain цепочка фильтров
     * @throws ServletException если возникает ошибка в сервлете
     * @throws IOException      если возникает ошибка ввода-вывода
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader("customerId"))
                .map(Long::valueOf)
                .ifPresent(customerIdProvider::setCustomerId);
        filterChain.doFilter(request, response);
    }
}