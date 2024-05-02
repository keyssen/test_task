package com.task.mediasoft.product.service.searchCriteria.Criterial;


import com.task.mediasoft.product.service.searchCriteria.Predicate.PredicateStrategy;
import com.task.mediasoft.product.service.searchCriteria.Predicate.UUIDPredicateStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UUIDSearchCriterial implements SearchCriterial<UUID> {
    
    private String field;

    private UUID value;

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
}