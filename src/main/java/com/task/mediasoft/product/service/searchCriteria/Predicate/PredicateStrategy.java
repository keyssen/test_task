package com.task.mediasoft.product.service.searchCriteria.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

/**
 * Интерфейс, определяющий стратегии создания предикатов в критериальном запросе на основе различных условий.
 *
 * @param <T> тип выражения и значения
 */
public interface PredicateStrategy<T> {
    /**
     * Создает предикат, представляющий условие равенства между заданным выражением и значением.
     *
     * @param expression      выражение для сравнения
     * @param value           значение для сравнения
     * @param criteriaBuilder построитель критериев для создания предиката
     * @return предикат, представляющий условие равенства
     */
    Predicate equal(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);

    /**
     * Создает предикат, представляющий условие "больше или равно" между заданным выражением и значением.
     *
     * @param expression      выражение для сравнения
     * @param value           значение для сравнения
     * @param criteriaBuilder построитель критериев для создания предиката
     * @return предикат, представляющий условие "больше или равно"
     */
    Predicate greaterThanOrEqualTo(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);

    /**
     * Создает предикат, представляющий условие "меньше или равно" между заданным выражением и значением.
     *
     * @param expression      выражение для сравнения
     * @param value           значение для сравнения
     * @param criteriaBuilder построитель критериев для создания предиката
     * @return предикат, представляющий условие "меньше или равно"
     */
    Predicate lessThanOrEqualTo(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);

    /**
     * Создает предикат, представляющий условие "подобно" между заданным выражением и значением.
     *
     * @param expression      выражение для сравнения
     * @param value           значение для сравнения
     * @param criteriaBuilder построитель критериев для создания предиката
     * @return предикат, представляющий условие "подобно"
     */
    Predicate like(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);
}