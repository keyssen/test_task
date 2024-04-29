package com.task.mediasoft.product.service.searchCriteria.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public interface PredicateStrategy<T> {

    Predicate equal(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);


    Predicate greaterThanOrEqualTo(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);


    Predicate lessThanOrEqualTo(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);

    Predicate like(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);

}