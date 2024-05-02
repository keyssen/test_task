package com.task.mediasoft.product.service.searchCriteria.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

/**
 * Стратегия предиката для типа String.
 */
public class StringPredicateStrategy implements PredicateStrategy<String> {
    @Override
    public Predicate equal(Expression<String> expression, String value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate greaterThanOrEqualTo(Expression<String> expression, String value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(expression, value + "%");
    }

    @Override
    public Predicate lessThanOrEqualTo(Expression<String> expression, String value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(expression, "%" + value);
    }

    @Override
    public Predicate like(Expression<String> expression, String value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(expression, "%" + value + "%");
    }
}
