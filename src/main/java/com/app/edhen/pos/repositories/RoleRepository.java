package com.app.edhen.pos.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.edhen.pos.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
    
}
