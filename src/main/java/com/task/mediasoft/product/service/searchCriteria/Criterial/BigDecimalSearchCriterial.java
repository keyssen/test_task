package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.task.mediasoft.product.service.searchCriteria.Predicate.BigDecimalPredicateStrategy;
import com.task.mediasoft.product.service.searchCriteria.Predicate.PredicateStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Класс, представляющий критерий поиска для значений типа BigDecimal.
 * Реализует интерфейс SearchCriterial для создания предикатов (условий) для запросов JPA Criteria API.
 */
@Setter
@Getter
public class BigDecimalSearchCriterial implements SearchCriterial<BigDecimal> {
    /**
     * Поле, к которому применяется критерий.
     */
    private String field;

    /**
     * Значение критерия.
     */
    private BigDecimal value;

    /**
     * Операция, которая будет применена к критерию.
     */
    private String operation;

    /**
     * Класс, реализующий интерфейс PredicateStrategy для работы с BigDecimal.
     */
    private static PredicateStrategy<BigDecimal> predicateStrategy = new BigDecimalPredicateStrategy();

    /**
     * Возвращает используемую стратегию предиката.
     *
     * @return Стратегия предиката для BigDecimal.
     */
    @Override
    public PredicateStrategy<BigDecimal> getPredicateStrategy() {
        return predicateStrategy;
    }

    /**
     * Создает предикат для проверки равенства значению BigDecimal.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий равенство.
     */
    @Override
    public Predicate equal(Expression<BigDecimal> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().equal(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки больше или равно значению BigDecimal.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий больше или равно.
     */
    @Override
    public Predicate greaterThanOrEqualTo(Expression<BigDecimal> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().greaterThanOrEqualTo(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки меньше или равно значению BigDecimal.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий меньше или равно.
     */
    @Override
    public Predicate lessThanOrEqualTo(Expression<BigDecimal> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().lessThanOrEqualTo(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки сходства с использованием оператора LIKE для BigDecimal.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий сходство с использованием оператора LIKE.
     */
    @Override
    public Predicate like(Expression<BigDecimal> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().like(expression, value, criteriaBuilder);
    }
}
