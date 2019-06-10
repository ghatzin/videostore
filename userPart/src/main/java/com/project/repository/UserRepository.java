package com.project.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);

	User findByEmail(String email);
}
