package com.admin.service;

import java.util.Set;

import com.admin.domain.User;
import com.admin.domain.security.UserRole;



public interface UserService {
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);

	User findByUsername(String username);

	User findById(Long id);
}
