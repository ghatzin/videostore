package com.admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.admin.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
	
	List<Message> findAllByUserId(Long id);

}
