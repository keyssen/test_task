package com.task.mediasoft.product.service.searchCriteria.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.util.UUID;


/**
 * Стратегия предиката для типа UUID.
 */
public class UUIDPredicateStrategy implements PredicateStrategy<UUID> {
    @Override
    public Predicate equal(Expression<UUID> expression, UUID value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression.as(String.class), value);
    }

    @Override
    public Predicate greaterThanOrEqualTo(Expression<UUID> expression, UUID value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(expression.as(String.class), value + "%");
    }

    @Override
    public Predicate lessThanOrEqualTo(Expression<UUID> expression, UUID value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(expression.as(String.class), "%" + value);
    }

    @Override
    public Predicate like(Expression<UUID> expression, UUID value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(expression.as(String.class), "%" + value + "%");
    }
}
