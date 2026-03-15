package com.alvez.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alvez.user_service.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
    
  boolean existsByEmail(String email);

}
