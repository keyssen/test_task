package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.task.mediasoft.product.service.searchCriteria.Predicate.PredicateStrategy;


/**
 * Интерфейс, представляющий критерии поиска для использования в запросах.
 * Имеет ряд методов для создания предикатов (условий) для запросов JPA Criteria API.
 *
 * @param <T> тип значения, к которому применяется критерий
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "field")
@JsonSubTypes({@JsonSubTypes.Type(value = UUIDSearchCriterial.class, name = "id"),
        @JsonSubTypes.Type(value = StringSearchCriterial.class, name = "article"),
        @JsonSubTypes.Type(value = StringSearchCriterial.class, name = "name"),
        @JsonSubTypes.Type(value = StringSearchCriterial.class, name = "description"),
        @JsonSubTypes.Type(value = StringSearchCriterial.class, name = "category"),
        @JsonSubTypes.Type(value = BigDecimalSearchCriterial.class, name = "price"),
        @JsonSubTypes.Type(value = BigDecimalSearchCriterial.class, name = "quantity"),
        @JsonSubTypes.Type(value = LocalDateTimeSearchCriterial.class, name = "lastQuantityChangeDate"),
        @JsonSubTypes.Type(value = LocalDateTimeSearchCriterial.class, name = "creationDate")})
public interface SearchCriterial<T> {
    /**
     * Получает поле, к которому применяется критерий.
     *
     * @return строка с именем поля
     */
    String getField();

    /**
     * Устанавливает поле, к которому применяется критерий.
     *
     * @param field строка с именем поля
     */
    void setField(String field);

    /**
     * Получает значение критерия.
     *
     * @return значение критерия
     */
    T getValue();

    /**
     * Устанавливает значение критерия.
     *
     * @param value значение критерия
     */
    void setValue(T value);

    /**
     * Получает операцию, которая будет применена к критерию.
     *
     * @return строка с названием операции
     */
    String getOperation();

    /**
     * Устанавливает операцию, которая будет применена к критерию.
     *
     * @param operation строка с названием операции
     */
    void setOperation(String operation);

    /**
     * Retrieves the predicate strategy.
     *
     * @return The predicate strategy.
     */
    /**
     * Получает стратегию предиката.
     *
     * @return Стратегия предиката.
     */
    PredicateStrategy<T> getPredicateStrategy();
}