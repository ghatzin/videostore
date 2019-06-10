package com.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
	
	List<Message> findAllByUserId(Long id);

}
