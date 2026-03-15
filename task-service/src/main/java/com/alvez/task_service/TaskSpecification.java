package com.alvez.task_service;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    private TaskSpecification() {
        throw new IllegalStateException("Utility class");    // Private constructor to prevent instantiation
    }

    public static Specification<Task> create( final String status){
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            if(status != null && !status.isEmpty()){
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("status"), status.toUpperCase()));
            }
            return predicate;
        };
    
    }

    
}
