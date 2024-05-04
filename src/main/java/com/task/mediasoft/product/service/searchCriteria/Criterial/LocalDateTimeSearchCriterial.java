package com.task.mediasoft.product.service.searchCriteria.Criterial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.mediasoft.product.service.searchCriteria.Predicate.LocalDateTimePredicateStrategy;
import com.task.mediasoft.product.service.searchCriteria.Predicate.PredicateStrategy;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LocalDateTimeSearchCriterial implements SearchCriterial<LocalDateTime> {

    private String field;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime value;

    private String operation;

    /**
     * Класс, реализующий интерфейс PredicateStrategy для работы с LocalDateTime.
     */
    private static PredicateStrategy<LocalDateTime> predicateStrategy = new LocalDateTimePredicateStrategy();

    /**
     * Возвращает используемую стратегию предиката.
     *
     * @return Стратегия предиката для LocalDateTime.
     */
    @Override
    public PredicateStrategy<LocalDateTime> getPredicateStrategy() {
        return predicateStrategy;
    }
}
