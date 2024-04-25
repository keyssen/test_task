package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.mediasoft.product.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class LocalDateTimeSearchCriterial implements SearchCriterial<LocalDateTime> {
    /**
     * Поле, к которому применяется критерий.
     */
    private String field;

    /**
     * Значение критерия.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime value;

    /**
     * Операция, которая будет применена к критерию.
     */
    private String operation;

    /**
     * Создает предикат для операции равенства значения типа LocalDateTime.
     * Преобразует LocalDateTime к LocalDate для сравнения с корневым элементом запроса.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции равенства
     */
    @Override
    public Predicate equal(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(field).as(LocalDate.class), value.toLocalDate());
    }

    /**
     * Создает предикат для операции "больше или равно" значения типа LocalDateTime.
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
     * Создает предикат для операции "меньше или равно" значения типа LocalDateTime.
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
     * Создает предикат для операции "подобно" (LIKE) значения типа LocalDateTime.
     * В данном случае, ограничивает значение в диапазоне +/- 3 дня от заданного значения.
     *
     * @param root            корневой элемент запроса
     * @param criteriaBuilder объект CriteriaBuilder для создания предиката
     * @return предикат для операции "подобно" (LIKE)
     */
    @Override
    public Predicate like(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(root.get(field), value.minusDays(3), value.plusDays(3));
    }
}
