package com.app.edhen.pos.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.edhen.pos.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
    
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
