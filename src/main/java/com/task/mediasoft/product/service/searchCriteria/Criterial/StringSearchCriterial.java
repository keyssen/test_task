package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.task.mediasoft.product.service.searchCriteria.Predicate.PredicateStrategy;
import com.task.mediasoft.product.service.searchCriteria.Predicate.StringPredicateStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StringSearchCriterial implements SearchCriterial<String> {
    /**
     * Поле, к которому применяется критерий.
     */
    private String field;

    /**
     * Значение критерия.
     */
    private String value;

    /**
     * Операция, которая будет применена к критерию.
     */
    private String operation;

    /**
     * Класс, реализующий интерфейс PredicateStrategy для работы со строками.
     */
    private static PredicateStrategy<String> predicateStrategy = new StringPredicateStrategy();

    /**
     * Возвращает используемую стратегию предиката.
     *
     * @return Стратегия предиката для строк.
     */
    @Override
    public PredicateStrategy<String> getPredicateStrategy() {
        return predicateStrategy;
    }

    /**
     * Создает предикат для проверки равенства строке.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий равенство.
     */
    @Override
    public Predicate equal(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().equal(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки больше или равно строке.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий больше или равно.
     */
    @Override
    public Predicate greaterThanOrEqualTo(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().greaterThanOrEqualTo(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки меньше или равно строке.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий меньше или равно.
     */
    @Override
    public Predicate lessThanOrEqualTo(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().lessThanOrEqualTo(expression, value, criteriaBuilder);
    }

    /**
     * Создает предикат для проверки сходства с использованием оператора LIKE для строк.
     *
     * @param expression      Выражение для сравнения.
     * @param criteriaBuilder Строитель критериев.
     * @return Предикат, проверяющий сходство с использованием оператора LIKE.
     */
    @Override
    public Predicate like(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
        return getPredicateStrategy().like(expression, value, criteriaBuilder);
    }
}
