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

@Component
@AllArgsConstructor
public class CurrencyFilter extends OncePerRequestFilter {

    private final CurrencyProvider currencyProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currency = request.getHeader("currency");
        if (currency != null && CurrencyEnum.equalsEnum(currency)) {
            currencyProvider.setCurrency(currency);
        }
        filterChain.doFilter(request, response);
    }
}
