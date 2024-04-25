package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.task.mediasoft.product.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
     * Создает предикат для операции равенства значения типа String.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции равенства
     */
    @Override
    public Predicate equal(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(field).as(String.class), value);
    }

    /**
     * Создает предикат для операции "больше или равно" значения типа String.
     * Использует операцию LIKE для поиска значений, начинающихся с заданного значения.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции "больше или равно"
     */
    @Override
    public Predicate greaterThanOrEqualTo(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(field).as(String.class), value + "%");
    }

    /**
     * Создает предикат для операции "меньше или равно" значения типа String.
     * Использует операцию LIKE для поиска значений, заканчивающихся заданным значением.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции "меньше или равно"
     */
    @Override
    public Predicate lessThanOrEqualTo(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(field).as(String.class), "%" + value);
    }

    /**
     * Создает предикат для операции подобно (LIKE) значения типа String.
     * Использует операцию LIKE для поиска значений, содержащих заданное значение в любом месте.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции "подобно" (LIKE)
     */
    @Override
    public Predicate like(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(field).as(String.class), "%" + value + "%");
    }
}
