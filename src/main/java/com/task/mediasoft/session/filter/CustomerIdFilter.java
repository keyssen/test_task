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

@Component
@AllArgsConstructor
public class CustomerIdFilter extends OncePerRequestFilter {
    private final CustomerIdProvider customerIdProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String customerIdHeader = request.getHeader("customerId");
        if (customerIdHeader != null) {
            Long customerId = Long.valueOf(customerIdHeader);
            customerIdProvider.setCustomerId(customerId);
        }
        filterChain.doFilter(request, response);
    }
}
