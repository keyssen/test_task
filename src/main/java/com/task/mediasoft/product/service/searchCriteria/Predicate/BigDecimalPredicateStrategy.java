package com.task.mediasoft.product.service.searchCriteria.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;

public class BigDecimalPredicateStrategy implements PredicateStrategy<BigDecimal> {

    @Override
    public Predicate equal(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate greaterThanOrEqualTo(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate lessThanOrEqualTo(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate like(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(expression, value.multiply(BigDecimal.valueOf(0.9))),
                criteriaBuilder.lessThanOrEqualTo(expression, value.multiply(BigDecimal.valueOf(1.1)))
        );
    }
}
