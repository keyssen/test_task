package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.task.mediasoft.product.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
     * Создает предикат для операции равенства значения типа BigDecimal.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции равенства
     */
    @Override
    public Predicate equal(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(field), value);
    }

    /**
     * Создает предикат для операции "больше или равно" значения типа BigDecimal.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции "больше или равно"
     */
    @Override
    public Predicate greaterThanOrEqualTo(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get(field), value);
    }

    /**
     * Создает предикат для операции "меньше или равно" значения типа BigDecimal.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции "меньше или равно"
     */
    @Override
    public Predicate lessThanOrEqualTo(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(root.get(field), value);
    }

    /**
     * Создает предикат для операции "подобно" (LIKE) значения типа BigDecimal.
     * В данном случае, ограничивает значение в диапазоне от 90% до 110% от заданного значения.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции "подобно" (LIKE)
     */
    @Override
    public Predicate like(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get(field), value.multiply(BigDecimal.valueOf(0.9))),
                criteriaBuilder.lessThanOrEqualTo(root.get(field), value.multiply(BigDecimal.valueOf(1.1)))
        );
    }
}
