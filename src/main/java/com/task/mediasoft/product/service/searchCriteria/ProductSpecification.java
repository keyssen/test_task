package com.task.mediasoft.product.service.searchCriteria;

import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.service.searchCriteria.Criterial.SearchCriterial;
import com.task.mediasoft.product.service.searchCriteria.Enums.OperationEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {
    private final List<SearchCriterial> criteriaList;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = criteriaList.stream().map(searchCriterial -> {
            switch (OperationEnum.fromCode(searchCriterial.getOperation())) {
                case EQUAL -> {
                    return searchCriterial.getPredicateStrategy().equal(root.get(searchCriterial.getField()), searchCriterial.getValue(), criteriaBuilder);
                }
                case GREATER_THAN_OR_EQ -> {
                    return searchCriterial.getPredicateStrategy().greaterThanOrEqualTo(root.get(searchCriterial.getField()), searchCriterial.getValue(), criteriaBuilder);
                }
                case LESS_THAN_OR_EQ -> {
                    return searchCriterial.getPredicateStrategy().lessThanOrEqualTo(root.get(searchCriterial.getField()), searchCriterial.getValue(), criteriaBuilder);
                }
                case LIKE -> {
                    return searchCriterial.getPredicateStrategy().like(root.get(searchCriterial.getField()), searchCriterial.getValue(), criteriaBuilder);
                }
                default -> throw new IllegalStateException("Unexpected value: " + searchCriterial.getOperation());
            }
        }).toList();
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
