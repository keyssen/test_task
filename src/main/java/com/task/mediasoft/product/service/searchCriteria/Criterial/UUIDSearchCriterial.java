package com.task.mediasoft.product.service.searchCriteria.Criterial;


import com.task.mediasoft.product.service.searchCriteria.Predicate.PredicateStrategy;
import com.task.mediasoft.product.service.searchCriteria.Predicate.UUIDPredicateStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UUIDSearchCriterial implements SearchCriterial<UUID> {

    /**
     * Поле, к которому применяется критерий.
     */
    private String field;

    /**
     * Значение критерия.
     */
    private UUID value;

    /**
     * Операция, которая будет применена к критерию.
     */
    private String operation;

    /**
     * Класс, реализующий интерфейс PredicateStrategy для работы с UUID.
     */
    private static PredicateStrategy<UUID> predicateStrategy = new UUIDPredicateStrategy();

    /**
     * Возвращает используемую стратегию предиката.
     *
     * @return Стратегия предиката для UUID.
     */
    @Override
    public PredicateStrategy<UUID> getPredicateStrategy() {
        return predicateStrategy;
    }

    /**
     * Создает предикат для проверки равенства UUID.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий равенство.
     */
    @Override
    public Predicate equal(Expression<UUID> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().equal(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки больше или равно UUID.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий больше или равно.
     */
    @Override
    public Predicate greaterThanOrEqualTo(Expression<UUID> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().greaterThanOrEqualTo(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки меньше или равно UUID.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий меньше или равно.
     */
    @Override
    public Predicate lessThanOrEqualTo(Expression<UUID> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().lessThanOrEqualTo(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки сходства с использованием оператора LIKE для UUID.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий сходство с использованием оператора LIKE.
     */
    @Override
    public Predicate like(Expression<UUID> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().like(expression, value, criteriaBuilder);
    }
}