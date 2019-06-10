package com.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.admin.domain.security.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByName(String name);
}
