package com.alvez.user_service.domain;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
  
  private UserSpecification(){
    throw new UnsupportedOperationException("Utility Class");
  }

  public static Specification<User> create(final String name, final String email){
    return (root, query, criteriaBuilder) -> {
      var predicates = criteriaBuilder.conjunction();

      if (name != null && !name.isEmpty()) {
        predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
      }
      if (email != null && !email.isEmpty()) {
        predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("email"), "%" + email + "%"));
      }
      return predicates;
    };

  }
}
