package com.task.mediasoft.product.service.searchCriteria.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimePredicateStrategy implements PredicateStrategy<LocalDateTime> {

    @Override
    public Predicate equal(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression.as(LocalDate.class), value.toLocalDate());
    }

    @Override
    public Predicate greaterThanOrEqualTo(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate lessThanOrEqualTo(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate like(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(expression, value.minusDays(3), value.plusDays(3));
    }

}
