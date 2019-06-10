package com.project.service;

import java.util.Set;

import com.project.domain.User;
import com.project.domain.security.PasswordResetToken;
import com.project.domain.security.UserRole;



public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail (String email);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	User findById(Long id);
}
