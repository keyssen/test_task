package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.task.mediasoft.product.service.searchCriteria.Predicate.BigDecimalPredicateStrategy;
import com.task.mediasoft.product.service.searchCriteria.Predicate.PredicateStrategy;
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

    private String field;

    private BigDecimal value;

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
}
