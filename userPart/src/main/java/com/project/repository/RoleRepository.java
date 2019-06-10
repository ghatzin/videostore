package com.project.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByName(String name);
}
