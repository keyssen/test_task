package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.task.mediasoft.product.service.searchCriteria.Predicate.PredicateStrategy;
import com.task.mediasoft.product.service.searchCriteria.Predicate.StringPredicateStrategy;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StringSearchCriterial implements SearchCriterial<String> {

    private String field;

    private String value;

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
}
